/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.locale;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Mati
 */
public enum EpicEnchantLocale {
    
    CREATE_SIGN_NO_PERMISSION("You don't have permission."),
    UNRECOGNIZED_ENCHANTMENT("Unrecognized enchantment.")
    ;
    
    public static final Map<String, EpicEnchantLocale> map = new HashMap<>();
    static {
        for(EpicEnchantLocale l : values()) {
            map.put(l.name(), l);
        }
    }
    
    private String text;

    private EpicEnchantLocale(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
