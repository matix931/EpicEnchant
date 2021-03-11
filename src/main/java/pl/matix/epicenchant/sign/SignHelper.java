/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.sign;

import org.bukkit.Bukkit;
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
        return sign.getLine(0).startsWith(ee.getSignPrefixColored());
        //return sign.getMetadata(EpicEnchant.SIGN_METAKEY).size() > 0;
    }
    
    public EeActionType getSignType(String[] signLines) {
        if(signLines[2] == null || signLines[2].trim().isEmpty()) {
            return EeActionType.UPGRADE;
        }
        return EeActionType.fromPrefix(signLines[2]);
    }
    
    public Enchantment getEnchantmentFromSign(String[] signLines) {
        return ee.getEnchantRegistry().getEnchantmentByKey(signLines[1], true);
    }
    
    public void updateSignLines(Sign sign, Enchantment e, EeActionType signType) {
        Bukkit.getScheduler().runTask(ee, () -> {
            String enchantmentName = ee.getEnchantRegistry().getPrettyName(e);
            if(enchantmentName.length() > 15) {
                enchantmentName = enchantmentName.substring(0, 15);
            }

            sign.setLine(0, ee.getSignPrefixColored());
            sign.setLine(1, enchantmentName);
            sign.setLine(2, signType.name());
            sign.setLine(3, "");
            sign.update();    
        });
    }
    
}
