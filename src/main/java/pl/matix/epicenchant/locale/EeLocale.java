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
public enum EeLocale {
    
    CREATE_SIGN_NO_PERMISSION("You don't have permission"),
    UNRECOGNIZED_ENCHANTMENT("Unrecognized enchantment"),
    UNRECOGNIZED_SIGN_TYPE("Unrecognized sign type"),
    CANT_ENCHANT("You can not enchant this item with that enchantment"),
    MAX_ENCHANT_LEVEL_REACHED("Maximum enchantment level reached!"),
    ENCHANT_COLLISION("This enchantment conflicts with other enchantments"),
    UPGRADE_INFO("Click again to upgrade ${enchant} to ${level} for ${cost}$ with ${chance} chance"),
    UPGRADED_SUCCESSFULLY("Enchantment upgraded successfully to ${enchant} ${level}"),
    UPGRADE_FAILED("Enchantment upgrading failed"),
    UPGRADE_FAILED_WITH_DOWNGRADE("Enchantment upgrading failed, enchantment has been downgraded to ${enchant} ${level}"),
    DOWNGRADED_SUCCESSFULLY("Enchantment downgraded successfully to ${enchant} ${level}"),
    DOWNGRADE_INFO("Click again to downgrade ${enchant} to ${level} for ${cost}$"),
    ITEM_MISSING_ENCHANT("Item is missing this enchantment"),
    NOT_ENOUGH_MONEY("You do not have enough money")
    ;
    
    public static final Map<String, EeLocale> map = new HashMap<>();
    static {
        for(EeLocale l : values()) {
            map.put(l.name(), l);
        }
    }
    
    private String text;

    private EeLocale(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
    
    public String getText(Map<String, String> params) {
        String s = text;
        for(Map.Entry<String, String> ent : params.entrySet()) {
            s = s.replace(String.format("${%s}", ent.getKey()), ent.getValue());
        }
        return s;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
