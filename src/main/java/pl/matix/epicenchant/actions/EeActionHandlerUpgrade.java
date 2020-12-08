/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfig;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.config.EeConfigActionUpgradeChances;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;
import pl.matix.epicenchant.config.EeConfigGlobalUpgradeChances;
import pl.matix.epicenchant.enchants.EnchantmentsRegistry;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;

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
        
        if(e.equals(ee.getEnchantRegistry().RANDOM)) {
            List<Enchantment> availableEnchantmentsFor = ee.getEnchantRegistry().getAvailableEnchantmentsForRandom(is);
            int n = availableEnchantmentsFor.size();
            Integer minEnchantmentPoolSize = ee.getEeConfig().getRandom().getMinEnchantmentPoolSize();
            if(minEnchantmentPoolSize == null) minEnchantmentPoolSize = 1;
            if(n < minEnchantmentPoolSize) {
                ee.sendChatMessage(player, EeLocale.RANDOM_TOO_FEW_ENCHANTMENTS_TO_ROLL);
                return false;
            }
            return true;
        }
        
        if(currentEnchantLevel >= config.getMaxLevel()) {
            ee.sendChatMessage(player, EeLocale.MAX_ENCHANT_LEVEL_REACHED);
            return false;
        }
        
        if(ee.getEnchantments().isConflicting(is, e)) {
            ee.sendChatMessage(player, EeLocale.ENCHANT_COLLISION);
            return false;
        }
        
        return true;
    }

    @Override
    public void performAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        boolean randomEnchantment = e.equals(ee.getEnchantRegistry().RANDOM);
        
        long cost = randomEnchantment ? EeCostsCalculator.calculateRandomCost(ee, player, is) 
                : EeCostsCalculator.calculateUpgradeCost(ee, player, e, currentEnchantLevel);
        double playerMoney = ee.getEconomy().getBalance(player);
        if(playerMoney < cost) {
            ee.sendChatMessage(player, EeLocale.NOT_ENOUGH_MONEY);
            return;
        }
        
        if(randomEnchantment) {
            Enchantment randomEnchant = ee.getEnchantments().upgradeRandomEnchantment(is);
            ee.getEconomy().withdrawPlayer(player, cost);
            String eName = ee.getEnchantRegistry().getPrettyName(randomEnchant);
            int level = is.getItemMeta().getEnchantLevel(randomEnchant);
            final String lvl = EnchantmentsRegistry.formatLevel(level);
            Map<String, String> params = new HashMap<>();
            params.put("enchant", eName);
            params.put("level", lvl);
            ee.sendChatMessage(player, EeLocale.RANDOM_SUCCESSFULLY, params);
            return;
        }
        
        int chance = calcChance(ee, player, e, currentEnchantLevel, config);
        Random r = new Random();
        int randomNum = 1 + r.nextInt(100);
        if(randomNum <= chance) {
            ee.getEnchantments().upgradeEnchantment(is, e, 1);
            ee.getEconomy().withdrawPlayer(player, cost);
            String eName = ee.getEnchantRegistry().getPrettyName(e);
            final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel+1);
            Map<String, String> params = new HashMap<>();
            params.put("enchant", eName);
            params.put("level", lvl);
            ee.sendChatMessage(player, EeLocale.UPGRADED_SUCCESSFULLY, params);
        } else {
            ee.getEconomy().withdrawPlayer(player, cost);
            boolean downgradeOnFail;
            if(config.getDowngradeOnFail() != null) {
                downgradeOnFail = config.getDowngradeOnFail();
            } else {
                downgradeOnFail = ee.getEeConfig().getGlobalUpgradeDowngradeOnFail();
            }
            if(downgradeOnFail && !EpicEnchantPermission.has(player, EpicEnchantPermission.UPGRADE_FAIL_DOWNGRADE_IMMUNITY)) {
                ee.getEnchantments().upgradeEnchantment(is, e, -1);
                String eName = ee.getEnchantRegistry().getPrettyName(e);
                final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel-1);
                Map<String, String> params = new HashMap<>();
                params.put("enchant", eName);
                params.put("level", lvl);
                ee.sendChatMessage(player, EeLocale.UPGRADE_FAILED_WITH_DOWNGRADE, params);
            } else {
                ee.sendChatMessage(player, EeLocale.UPGRADE_FAILED);    
            }
        }
    }

    @Override
    public void showInfoAction(EpicEnchant ee, Player player, ItemStack is, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        if(e.equals(ee.getEnchantRegistry().RANDOM)) {
            long cost = EeCostsCalculator.calculateRandomCost(ee, player, is);
            Map<String, String> params = new HashMap<>();
            params.put("cost", String.valueOf(cost));
            ee.sendChatMessage(player, EeLocale.RANDOM_INFO, params);
        } else {
            String eName = ee.getEnchantRegistry().getPrettyName(e);
            long cost = EeCostsCalculator.calculateUpgradeCost(ee, player, e, currentEnchantLevel);
            int chance = calcChance(ee, player, e, currentEnchantLevel, config);
            final String lvl = EnchantmentsRegistry.formatLevel(currentEnchantLevel+1);
            Map<String, String> params = new HashMap<>();
            params.put("enchant", eName);
            params.put("level", lvl);
            params.put("cost", String.valueOf(cost));
            params.put("chance", chance+"%");
            ee.sendChatMessage(player, EeLocale.UPGRADE_INFO, params);
        }
    }
    
    private int calcChance(EpicEnchant ee, Player player, Enchantment e, int currentEnchantLevel, EeConfigActionUpgrade config) {
        EeConfig c = ee.getEeConfig();
        EeConfigEnchantEntry eConfig = c.getEnchantmentConfig(e);
        EeConfigActionUpgrade aConfig = eConfig.getActionUpgrade();
        
        EeConfigGlobalUpgradeChances globalChances = c.getGlobalUpgradeChances();
        EeConfigActionUpgradeChances chances = aConfig.getChances();
        
        Integer[] exactValues = chances == null ? null : chances.getExactValues();
        if(exactValues !=null && exactValues.length > currentEnchantLevel && exactValues[currentEnchantLevel] != null) {
            return exactValues[currentEnchantLevel];
        }
        exactValues = globalChances.getExactValues();
        if(exactValues !=null && exactValues.length > currentEnchantLevel && exactValues[currentEnchantLevel] != null) {
            return exactValues[currentEnchantLevel];
        }
        
        double minValue = globalChances.getMinValue();
        double levelPower = globalChances.getLevelPower();
        double levelMultiplier = globalChances.getLevelMultiplier();
        
        if(chances != null) {
            if(chances.getMinValueModifier() != null) {
                minValue *= chances.getMinValueModifier();
            }
            if(chances.getLevelPowerModifier()!= null) {
                levelPower *= chances.getLevelPowerModifier();
            }
            if(chances.getLevelMultiplierModifier()!= null) {
                levelMultiplier *= chances.getLevelMultiplierModifier();
            }
        }
        if(minValue < 1) {
            minValue = 1;
        }
        
        int chance = (int) (100 - (Math.pow(currentEnchantLevel, levelPower) * levelMultiplier));
        
        Integer value = EpicEnchantPermission.getBestUpgradeChancePermission(player);
        if(value != null) {
            chance *= 0.01 * value;
        }        
        
        return Math.min(Math.max(chance, (int) minValue), 100);
    }
    
}
