/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.sign;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import pl.matix.epicenchant.actions.EeActionType;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.EpicEnchant;

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
    
    public EeActionType getSignType(String[] signLines) {
        return EeActionType.fromString(signLines[2]);
    }
    
    public Enchantment getEnchantmentFromSign(String[] signLines) {
        return ee.getEnchantRegistry().getEnchantmentByKey(signLines[1], true);
    }
    
    public void updateSignLines(String[] signLines, Enchantment e, EeActionType signType) {
        signLines[0] = ee.getSignPrefixColored();
        signLines[1] = ee.getEnchantRegistry().getPrettyName(e).substring(0,15);
        signLines[2] = signType.name();
        signLines[3] = "";
    }
    
}
