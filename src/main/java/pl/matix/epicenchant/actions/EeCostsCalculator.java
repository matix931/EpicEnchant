/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import java.util.List;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfig;
import pl.matix.epicenchant.config.EeConfigActionDowngrade;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.config.EeConfigActionUpgradeCosts;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;
import pl.matix.epicenchant.config.EeConfigGlobalUpgradeCosts;
import pl.matix.epicenchant.config.EeConfigRandom;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;

/**
 *
 * @author Mati
 */
public class EeCostsCalculator {
    
    public static long calculateRandomCost(EpicEnchant ee, Player player, ItemStack is) {
        EeConfig c = ee.getEeConfig();
        EeConfigRandom randomConfig = c.getRandom();
        
        ItemMeta meta = is.getItemMeta();
        long costSum = 0;
        List<Enchantment> availableEnchantmentsFor = ee.getEnchantRegistry().getAvailableEnchantmentsForRandom(is);
        for(Enchantment e : availableEnchantmentsFor) {
            int level = meta.getEnchantLevel(e);
            long cost = calculateUpgradeCost(ee, player, e, level);
            costSum += cost;
        }
        long minCost = randomConfig.getMinCost()==null ? 0 : randomConfig.getMinCost();
        long maxCost = randomConfig.getMaxCost()==null ? Long.MAX_VALUE : randomConfig.getMaxCost();
        double costMultiplier = randomConfig.getCostMultiplier()==null ? 1 : randomConfig.getCostMultiplier();
        
        int amount = availableEnchantmentsFor.size();
        return (long) Math.min(Math.max((costSum / amount) * costMultiplier, minCost), maxCost);
    }
    
    public static long calculateDowngradeCost(EpicEnchant ee, Player player, Enchantment e, int currentEnchantLevel) {
        EeConfig c = ee.getEeConfig();
        EeConfigEnchantEntry eConfig = c.getEnchantmentConfig(e);
        EeConfigActionDowngrade aConfig = (EeConfigActionDowngrade) eConfig.getActions().get(EeActionType.DOWNGRADE);
        
        double downgradeCostPart = c.getGlobalDowngradeCostPart();
        double cost = downgradeCostPart * calculateUpgradeCost(ee, player, e, currentEnchantLevel);
        if(aConfig != null) {
            if(aConfig.getCostPartModifier() != null) {
                cost *= aConfig.getCostPartModifier();
            }
        }
        
        return (long) cost;
    }
    
    public static long calculateUpgradeCost(EpicEnchant ee, Player player, Enchantment e, int currentEnchantLevel) {
        EeConfig c = ee.getEeConfig();
        EeConfigEnchantEntry eConfig = c.getEnchantmentConfig(e);
        EeConfigActionUpgrade aConfig = (EeConfigActionUpgrade) eConfig.getActions().get(EeActionType.UPGRADE);
        
        EeConfigGlobalUpgradeCosts globalCosts = c.getGlobalUpgradeCosts();
        EeConfigActionUpgradeCosts costs = aConfig.getCosts();
        
        Long[] exactValues = costs == null ? null : costs.getExactValues();
        if(exactValues !=null && exactValues.length > currentEnchantLevel && exactValues[currentEnchantLevel] != null) {
            return exactValues[currentEnchantLevel];
        }
        exactValues = globalCosts.getExactValues();
        if(exactValues !=null && exactValues.length > currentEnchantLevel && exactValues[currentEnchantLevel] != null) {
            return exactValues[currentEnchantLevel];
        }
        
        double minValue = globalCosts.getMinValue();
        double levelPower = globalCosts.getLevelPower();
        double levelMultiplier = globalCosts.getLevelMultiplier();
        
        if(costs != null) {
            if(costs.getMinValueModifier() != null) {
                minValue *= costs.getMinValueModifier();
            }
            if(costs.getLevelPowerModifier()!= null) {
                levelPower *= costs.getLevelPowerModifier();
            }
            if(costs.getLevelMultiplierModifier()!= null) {
                levelMultiplier *= costs.getLevelMultiplierModifier();
            }
        }
        
        long cost = (long) (minValue + (Math.pow(currentEnchantLevel, levelPower) * levelMultiplier));
        
        
        Integer value = EpicEnchantPermission.getBestUpgradeCostPermission(player);
        if(value != null) {
            cost *= 0.01 * value;
        }
        
        return cost;
    }
    
}
