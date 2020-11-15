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
import pl.matix.epicenchant.config.EeConfigActionDowngrade;
import pl.matix.epicenchant.enchants.EnchantmentsRegistry;
import pl.matix.epicenchant.locale.EeLocale;

/**
 *
 * @author Mati
 */
public class EeActionHandlerDowngrade extends EeActionHandler<EeConfigActionDowngrade> {

    public EeActionHandlerDowngrade() {
        super(EeConfigActionDowngrade.class);
    }

    @Override
    public boolean validateBeforeAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionDowngrade config) {
        if(currentEnchantLevel <= 0) {
            ee.sendChatMessage(player, EeLocale.ITEM_MISSING_ENCHANT);
            return false;
        }
        return true;
    }

    @Override
    public void performAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionDowngrade config) {
        final boolean removed = ee.getEnchantments().upgradeEnchantment(is, e, -1);
        if(removed) {
            String eName = ee.getEnchantRegistry().getPrettyName(e);
            final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel-1);
            Map<String, String> params = new HashMap<>();
            params.put("enchant", eName);
            params.put("level", lvl);
            ee.sendChatMessage(player, EeLocale.DOWNGRADED_SUCCESSFULLY, params);
        }
    }

    @Override
    public void showInfoAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionDowngrade config) {
        String eName = ee.getEnchantRegistry().getPrettyName(e);
        int cost = 0;
        int chance = 25;
        final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel-1);
        Map<String, String> params = new HashMap<>();
        params.put("enchant", eName);
        params.put("level", lvl);
        params.put("cost", String.valueOf(cost));
        params.put("chance", chance+"%");
        ee.sendChatMessage(player, EeLocale.DOWNGRADE_INFO, params);
    }
    
}
