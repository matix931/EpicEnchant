/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public class PlayerListener extends EeListener {

    public PlayerListener(EpicEnchant ee) {
        super(ee);
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
            if(ee.getSignHelper().isSignEpicEnchant(sign)) {
                Enchantment enchantment = ee.getSignHelper().getEnchantmentFromSign(sign.getLines());
                Player player = event.getPlayer();
                ItemStack itemInHand = player.getInventory().getItemInMainHand();
                if(itemInHand.getAmount() == 1) {
                    boolean added = ee.getEnchantments().upgradeEnchantment(itemInHand, enchantment, 1);
                    if(added) {
                        ee.sendChatMessage(player, "Item enchanted successfully");
                    }
                }
            }
        }
    }

}
