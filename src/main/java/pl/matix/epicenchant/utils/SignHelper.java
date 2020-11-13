/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.utils;

import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.sign.SignType;

/**
 *
 * @author Mati
 */
public class SignHelper {
    
    private final EpicEnchant ee;

    public SignHelper(EpicEnchant ee) {
        this.ee = ee;
    }
    
    public boolean isSignEpicEnchant(Sign sign) {
        return sign.getLine(0).equalsIgnoreCase(ee.getSignPrefixColored());
    }
    
    public SignType getSignType(Sign sign) {
        return SignType.UPGRADE;
    }
    
    public Enchantment getEnchantmentFromSign(String[] signLines) {
        return ee.getEnchantRegistry().getEnchantmentByKey(signLines[1]);
    }
    
    public void updateSignLines(String[] signLines, Enchantment e) {
        signLines[0] = ee.getSignPrefixColored();
        signLines[1] = e.getKey().getKey().toUpperCase();
        signLines[2] = "";
        signLines[3] = "";
    }
    
}
