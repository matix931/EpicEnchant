/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfigAction;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;
import pl.matix.epicenchant.actions.EeActionHandler;
import pl.matix.epicenchant.actions.EeActionType;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;

/**
 *
 * @author Mati
 */
public class PlayerListener extends EeListener {

    private static final Map<UUID, Pair<Long, Location>> playersClicksMap = new HashMap<>();
    
    public PlayerListener(EpicEnchant ee) {
        super(ee);
    }

    @EventHandler
    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        playersClicksMap.remove(player.getUniqueId());
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        if(block == null) {
            return;
        }
        BlockState state = block.getState();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && state instanceof Sign) {
            Sign sign = (Sign) state;
            Player player = event.getPlayer();
            
            Pair<Long, Location> pair = playersClicksMap.get(player.getUniqueId());
            Long lastClickTime = null;
            if(pair != null && pair.getRight().equals(block.getLocation())) {
                lastClickTime = pair.getLeft();
            }
            playersClicksMap.remove(player.getUniqueId());
            
            if(!ee.getSignHelper().isSignEpicEnchant(sign)) {
                return;
            }
            
            if(!EpicEnchantPermission.has(player, EpicEnchantPermission.SIGN_USE)) {
                ee.sendChatMessage(player, EeLocale.NO_PERMISSION);
                return;
            }
            
            final String[] lines = sign.getLines();
            final ItemStack itemInHand = player.getInventory().getItemInMainHand();
            final ItemMeta itemMeta = itemInHand.getItemMeta();
            if(itemMeta == null) {
                return;
            }
            final Enchantment enchantment = ee.getSignHelper().getEnchantmentFromSign(lines);
            final int currentEnchantLevel = itemMeta.getEnchantLevel(enchantment);
            final EeActionType actionType = ee.getSignHelper().getSignType(lines);
            final EeConfigEnchantEntry config = ee.getEeConfig().getEnchantmentConfig(enchantment);
            final EeActionHandler actionHandler = actionType.getActionHandler();
            final EeConfigAction actionConfig = config == null ? null : config.getActions().get(actionType);
            
            if(!actionHandler.validateBeforeAction(ee, player, itemInHand, enchantment, currentEnchantLevel, actionConfig)) {
                return;
            }
            
            final long currentTime = System.currentTimeMillis();
            if(lastClickTime != null && (currentTime - lastClickTime) < 5000) {
                actionHandler.performAction(ee, player, itemInHand, enchantment, currentEnchantLevel, actionConfig);
            } else {
                playersClicksMap.put(player.getUniqueId(), Pair.of(currentTime, block.getLocation()));
                actionHandler.showInfoAction(ee, player, itemInHand, enchantment, currentEnchantLevel, actionConfig);
            }
        }
    }

}
