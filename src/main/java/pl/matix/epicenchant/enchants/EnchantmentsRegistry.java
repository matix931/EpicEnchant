/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.enchants;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public class EnchantmentsRegistry {
    
    private final Set<EeCustomEnchantment> enchantsList = new HashSet<>();
    private final Map<String, Enchantment> otherEnchantsList = new HashMap<>();
    
    public final DestroyerEnchantment DESTROYER;
    
    private EpicEnchant ee;

    public EnchantmentsRegistry(EpicEnchant ee) throws Exception {
        this.ee = ee;
        this.DESTROYER = createCustomEnchantment(DestroyerEnchantment.class);
    }
    
    public void init() throws Exception {
        Field byKeyField = Enchantment.class.getDeclaredField("byKey");
        byKeyField.setAccessible(true);
        HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) byKeyField.get(null);
        byKey.forEach((k, e) -> {
            String key = k.getKey();
            ee.getLog().log(Level.INFO, "Found enchantment: {0}", key);
            otherEnchantsList.put(key.toLowerCase(), e);
        });
        byKeyField.setAccessible(false);
    }
    
    private <T extends EeCustomEnchantment> T createCustomEnchantment(Class<T> clz) throws Exception {
        T enchant = clz.getConstructor(EpicEnchant.class).newInstance(ee);
        enchantsList.add(enchant);
        return enchant;
    }
    
    public Enchantment getEnchantmentByKey(String s) {
        if(s == null || s.isEmpty()) {
            return null;
        }
        s = s.toLowerCase();
        Enchantment e = otherEnchantsList.get(s);
        if(e != null) {
            return e;
        }
        for(EeCustomEnchantment ce : enchantsList) {
            if(ce.getKey().getKey().toLowerCase().equals(s)) {
                return ce;
            }
        }
        return null;
    }

    public Set<EeCustomEnchantment> getCustomEnchants() {
        return enchantsList;
    }

    public Map<String, Enchantment> getOtherEnchants() {
        return otherEnchantsList;
    }
    
}
