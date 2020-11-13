/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.managers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.enchants.DestroyerEnchantment;
import pl.matix.epicenchant.enchants.EeCustomEnchantment;

/**
 *
 * @author Mati
 */
public class EeEnchantmentsManager {
    
    private EpicEnchant ee;
    
    public EeEnchantmentsManager(EpicEnchant ee) {
        this.ee = ee;
    }

    public void register() throws Exception {
        Field f = Enchantment.class.getDeclaredField("acceptingNew");
        f.setAccessible(true);
        f.set(null, true);
        f.setAccessible(false);
        
        ee.getEnchantRegistry().getCustomEnchants().forEach(e -> Enchantment.registerEnchantment(e));
        Enchantment.stopAcceptingRegistrations();
    }
    
    public void unregister() throws Exception {
        Field byKeyField = Enchantment.class.getDeclaredField("byKey");
        byKeyField.setAccessible(true);
        HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) byKeyField.get(null);
        
        Field byNameField = Enchantment.class.getDeclaredField("byName");
	byNameField.setAccessible(true);
        HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) byNameField.get(null);
        
        ee.getEnchantRegistry().getCustomEnchants().forEach(e -> {
            byKey.remove(e.getKey());
            byName.remove(e.getName());
        });
        
        byKeyField.setAccessible(false);
        byNameField.setAccessible(false);
    }
    
    public boolean addCustomEnchantment(ItemStack is, EeCustomEnchantment e, int level) {
        ItemMeta meta = is.getItemMeta();
        if(meta!=null) {
            meta.addEnchant(e, level, true);
            updateLore(meta, e, level);
            is.setItemMeta(meta);
            return true;
        }
        return false;
    }
    
    public boolean upgradeEnchantment(ItemStack is, Enchantment e, int plusLevels) {
        ItemMeta meta = is.getItemMeta();
        if(meta!=null) {
            int level = meta.getEnchantLevel(e);
            level += plusLevels;
            level = Math.min(level, 127);
            if(level < 0) {
                meta.removeEnchant(e);
            } else {
                meta.addEnchant(e, level, true);
            }
            if(e instanceof  EeCustomEnchantment) {
                updateLore(meta, (EeCustomEnchantment) e, level);
            }
            is.setItemMeta(meta);
            return true;
        }
        return false;
    }
    
    public void updateLore(ItemMeta meta, EeCustomEnchantment e, int level) {
        List<String> lore = meta.getLore();
        if(lore == null) lore = new ArrayList<>();
        int idx = -1;
        int n = lore.size();
        for(int i=0; i<n; i++) {
            String s = lore.get(i);
            if(s.startsWith(e.buildLoreEntryPrefix())) {
                idx = i;
                break;
            }
        }
        final String loreEntry = e.buildLoreEntry(level); 
        if(level == 0) {
            if(idx >= 0) {
                lore.remove(idx);    
            }
        } else {
            if(idx>=0) {
                lore.set(idx, loreEntry);
            } else {
                lore.add(0, loreEntry);
            }    
        }
        meta.setLore(lore);
    }
    
    public boolean hasItemCustomEnchnts(ItemStack is) {
        ItemMeta meta = is.getItemMeta();
        if(meta == null) {
            return false;
        }
        
        return meta.getEnchants().entrySet()
                .stream()
                .anyMatch((ent) -> (ent.getKey() instanceof EeCustomEnchantment && ent.getValue() > 0));
    }
    
    public void copyEnchants(ItemStack from, ItemStack to, boolean onlyCustom) {
        ItemMeta fromMeta = from.getItemMeta();
        ItemMeta toMeta = to.getItemMeta();
        
        if(fromMeta == null || toMeta == null) {
            return;
        }
        fromMeta.getEnchants().forEach((e, l) -> {
            if(!onlyCustom || (e instanceof EeCustomEnchantment)) {
                toMeta.addEnchant(e, l, true);    
            }
        });
        to.setItemMeta(toMeta);
    }
    
}
