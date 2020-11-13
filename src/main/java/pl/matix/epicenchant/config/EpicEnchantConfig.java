/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.config;

import java.util.List;
import pl.matix.epicenchant.locale.LocaleLanguage;

/**
 *
 * @author Mati
 */
public class EpicEnchantConfig {
    
    private LocaleLanguage language = LocaleLanguage.EN;
    private List<EpicEnchantEnchantmentConfig> enchantments;

    public EpicEnchantConfig() {
        
    }
    
    public EpicEnchantConfig createDefault() {
        EpicEnchantConfig c = new EpicEnchantConfig();
        
        return c;
    }

    public void setLanguage(LocaleLanguage language) {
        this.language = language;
    }

    public LocaleLanguage getLanguage() {
        return language;
    }

    public void setEnchantments(List<EpicEnchantEnchantmentConfig> enchantments) {
        this.enchantments = enchantments;
    }

    public List<EpicEnchantEnchantmentConfig> getEnchantments() {
        return enchantments;
    }
}
