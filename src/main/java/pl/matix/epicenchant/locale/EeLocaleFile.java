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
public class EeLocaleFile extends EnumMap<EeLocale, String> {

    public EeLocaleFile() {
        super(EeLocale.class);
    }
    
    public static EeLocaleFile createDefault() {
        EeLocaleFile localeFile = new EeLocaleFile();
        EeLocale.map.values().forEach(l -> localeFile.put(l, l.getText()));
        return localeFile;
    }
    
}
