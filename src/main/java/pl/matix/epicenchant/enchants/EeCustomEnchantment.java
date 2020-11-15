/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.enchants;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public abstract class EeCustomEnchantment extends Enchantment {
    
    public EeCustomEnchantment(EpicEnchant ee, String name) {
        super(new NamespacedKey(ee, name));
    }
    
    protected abstract String getLoreName();
    
    public String buildLoreEntryPrefix() {
        return "§3" + getLoreName();
    }
    
    public String buildLoreEntry(int level) {
        return buildLoreEntryPrefix() + " " + EnchantmentsRegistry.formatLevel(level);
    }
    
}
