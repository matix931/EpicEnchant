/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.locale;

import java.util.EnumMap;

/**
 *
 * @author Mati
 */
public class EpicEnchantLocaleFile extends EnumMap<EpicEnchantLocale, String> {

    public EpicEnchantLocaleFile() {
        super(EpicEnchantLocale.class);
    }
    
    public static EpicEnchantLocaleFile createDefault() {
        EpicEnchantLocaleFile localeFile = new EpicEnchantLocaleFile();
        EpicEnchantLocale.map.values().forEach(l -> localeFile.put(l, l.getText()));
        return localeFile;
    }
    
}
