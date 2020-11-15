/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.locale.LocaleLanguage;

/**
 *
 * @author Mati
 */
public class EeConfig {
    
    private LocaleLanguage language = LocaleLanguage.EN;
    private Map<String, EeConfigEnchantEntry> enchantments = new HashMap<>();

    public EeConfig() {
        
    }
    
    public void setLanguage(LocaleLanguage language) {
        this.language = language;
    }

    public LocaleLanguage getLanguage() {
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
}
