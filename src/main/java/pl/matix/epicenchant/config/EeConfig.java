/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;

/**
 *
 * @author Mati
 */
public class EeConfig {
    
    private String language;
    
    private EeConfigGlobalUpgradeCosts globalUpgradeCosts;
    private EeConfigGlobalUpgradeChances globalUpgradeChances;
    private double globalDowngradeCostPart;
    private Boolean globalUpgradeDowngradeOnFail;
    private EeConfigRandom random;
    
    private Map<String, EeConfigEnchantEntry> enchantments = new HashMap<>();

    public EeConfig() {
        
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setEnchantments(Map<String, EeConfigEnchantEntry> enchantments) {
        this.enchantments = enchantments;
    }

    public Map<String, EeConfigEnchantEntry> getEnchantments() {
        return enchantments;
    }
    
    public EeConfigEnchantEntry getEnchantmentConfig(Enchantment e) {
        return enchantments.get(e.getKey().getKey().toUpperCase());
    }

    public double getGlobalDowngradeCostPart() {
        return globalDowngradeCostPart;
    }

    public void setGlobalDowngradeCostPart(double globalDowngradeCostPart) {
        this.globalDowngradeCostPart = globalDowngradeCostPart;
    }

    public EeConfigGlobalUpgradeCosts getGlobalUpgradeCosts() {
        return globalUpgradeCosts;
    }

    public void setGlobalUpgradeCosts(EeConfigGlobalUpgradeCosts globalUpgradeCosts) {
        this.globalUpgradeCosts = globalUpgradeCosts;
    }

    public EeConfigGlobalUpgradeChances getGlobalUpgradeChances() {
        return globalUpgradeChances;
    }

    public void setGlobalUpgradeChances(EeConfigGlobalUpgradeChances globalUpgradeChances) {
        this.globalUpgradeChances = globalUpgradeChances;
    }

    public Boolean getGlobalUpgradeDowngradeOnFail() {
        return globalUpgradeDowngradeOnFail;
    }

    public void setGlobalUpgradeDowngradeOnFail(Boolean globalUpgradeDowngradeOnFail) {
        this.globalUpgradeDowngradeOnFail = globalUpgradeDowngradeOnFail;
    }

    public EeConfigRandom getRandom() {
        return random;
    }

    public void setRandom(EeConfigRandom random) {
        this.random = random;
    }
    
}
