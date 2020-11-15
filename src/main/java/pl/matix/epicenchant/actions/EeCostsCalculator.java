/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfig;
import pl.matix.epicenchant.config.EeConfigActionDowngrade;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.config.EeConfigActionUpgradeCosts;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;
import pl.matix.epicenchant.config.EeConfigGlobalUpgradeCosts;

/**
 *
 * @author Mati
 */
public class EeCostsCalculator {
    
    public static long calculateDowngradeCost(EpicEnchant ee, Enchantment e, int currentEnchantLevel) {
        EeConfig c = ee.getEeConfig();
        EeConfigEnchantEntry eConfig = c.getEnchantmentConfig(e);
        EeConfigActionDowngrade aConfig = (EeConfigActionDowngrade) eConfig.getActions().get(EeActionType.DOWNGRADE);
        
        double downgradeCostPart = c.getGlobalDowngradeCostPart();
        double cost = downgradeCostPart * calculateUpgradeCost(ee, e, currentEnchantLevel);
        if(aConfig != null) {
            if(aConfig.getCostPartModifier() != null) {
                cost *= aConfig.getCostPartModifier();
            }
        }
        
        return (long) cost;
    }
    
    public static long calculateUpgradeCost(EpicEnchant ee, Enchantment e, int currentEnchantLevel) {
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
        
        return (long) (minValue + (Math.pow(currentEnchantLevel, levelPower) * levelMultiplier));
    }
    
}
