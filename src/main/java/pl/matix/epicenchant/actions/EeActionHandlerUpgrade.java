/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.enchants.EnchantmentsRegistry;
import pl.matix.epicenchant.locale.EeLocale;

/**
 *
 * @author Mati
 */
public class EeActionHandlerUpgrade extends EeActionHandler<EeConfigActionUpgrade> {

    public EeActionHandlerUpgrade() {
        super(EeConfigActionUpgrade.class);
    }

    @Override
    public boolean validateBeforeAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        if(!e.canEnchantItem(is)) {
            ee.sendChatMessage(player, EeLocale.CANT_ENCHANT);
            return false;
        }
        
        if(currentEnchantLevel >= config.getMaxLevel()) {
            ee.sendChatMessage(player, EeLocale.MAX_ENCHANT_LEVEL_REACHED);
            return false;
        }
        
        if(currentEnchantLevel<=0) {
            //check collisions
            if(ee.getEnchantments().isConflicting(is, e)) {
                ee.sendChatMessage(player, EeLocale.ENCHANT_COLLISION);
                return false;
            }
        }
        
        return true;
    }

    @Override
    public void performAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        final boolean added = ee.getEnchantments().upgradeEnchantment(is, e, 1);
        if(added) {
            String eName = ee.getEnchantRegistry().getPrettyName(e);
            final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel+1);
            Map<String, String> params = new HashMap<>();
            params.put("enchant", eName);
            params.put("level", lvl);
            ee.sendChatMessage(player, EeLocale.UPGRADED_SUCCESSFULLY, params);
        }
    }

    @Override
    public void showInfoAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        String eName = ee.getEnchantRegistry().getPrettyName(e);
        int cost = 0;
        int chance = 25;
        final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel+1);
        Map<String, String> params = new HashMap<>();
        params.put("enchant", eName);
        params.put("level", lvl);
        params.put("cost", String.valueOf(cost));
        params.put("chance", chance+"%");
        ee.sendChatMessage(player, EeLocale.UPGRADE_INFO, params);
    }
    
}
