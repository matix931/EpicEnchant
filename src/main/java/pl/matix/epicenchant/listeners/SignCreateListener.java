/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.SignChangeEvent;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;
import pl.matix.epicenchant.actions.EeActionType;

/**
 *
 * @author Mati
 */
public class SignCreateListener extends EeListener {

    public SignCreateListener(EpicEnchant ee) {
        super(ee);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSignChange(SignChangeEvent event) {
        String[] lines = event.getLines();
        String line = lines[0];
        if(line == null || line.isEmpty()) {
            return;
        }
        
        if (!line.equalsIgnoreCase(ee.getSignPrefix()) 
         && !line.equalsIgnoreCase(ee.getSignPrefixColored())) {
            return;
        }
        
        final Player player = event.getPlayer();
        
        if (!EpicEnchantPermission.has(player, EpicEnchantPermission.SIGN_CREATE)) {
            ee.sendChatMessage(player, EeLocale.NO_PERMISSION);
            event.setCancelled(true);
            return;
        }
        
        Enchantment enchantment = ee.getSignHelper().getEnchantmentFromSign(lines);
        if(enchantment == null) {
            ee.sendChatMessage(player, EeLocale.UNRECOGNIZED_ENCHANTMENT);
            event.setCancelled(true);
            return;
        }
        
        EeActionType signType = ee.getSignHelper().getSignType(lines);
        if(signType == null) {
            ee.sendChatMessage(player, EeLocale.UNRECOGNIZED_SIGN_TYPE);
            event.setCancelled(true);
            return;
        }
        
        ee.getSignHelper().updateSignLines(lines, enchantment, signType);
        ee.sendChatMessage(player, EeLocale.CREATE_SIGN_SUCCESS);
    }
    
}
