/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;

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
        
        ee.getEnchantRegistry().getCustomEnchants().forEach(e -> {
            if(!e.equals(ee.getEnchantRegistry().RANDOM)) {
                Enchantment.registerEnchantment(e);
            }
        });
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
    
    public boolean canEnchantItem(ItemStack is, Enchantment e, int level) {
        if(!e.canEnchantItem(is)) {
            return false;
        }
        
        ItemMeta meta = is.getItemMeta();
        EeConfigEnchantEntry ec = ee.getEeConfig().getEnchantmentConfig(e);
        int enchantLevel = meta.getEnchantLevel(e);
        if(enchantLevel >= level) {
            return false;
        }
        
        return true;
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
    
    public void upgradeEnchantment(ItemStack is, Enchantment e, int plusLevels) {
        EeConfigEnchantEntry conf = ee.getEeConfig().getEnchantmentConfig(e);
        EeConfigActionUpgrade actionUpgrade = conf.getActionUpgrade();
        ItemMeta meta = is.getItemMeta();
        int level = meta.getEnchantLevel(e);
        level += plusLevels;
        level = Math.min(level, actionUpgrade.getMaxLevel());
        if(level <= 0) {
            meta.removeEnchant(e);
        } else {
            meta.addEnchant(e, level, true);
        }
        if(e instanceof  EeCustomEnchantment) {
            updateLore(meta, (EeCustomEnchantment) e, level);
        }
        is.setItemMeta(meta);
    }
    
    public Enchantment upgradeRandomEnchantment(ItemStack is) {
        List<Enchantment> availableEnchantmentsFor = ee.getEnchantRegistry().getAvailableEnchantmentsForRandom(is);
        Map<Pair<Long, Long>, Enchantment> weightsMap = new LinkedHashMap<>();
        long x = 0;
        for(Enchantment e : availableEnchantmentsFor) {
            EeConfigEnchantEntry conf = ee.getEeConfig().getEnchantmentConfig(e);
            EeConfigActionUpgrade actionUpgrade = conf.getActionUpgrade();
            int randomWeight = actionUpgrade.getRandomWeight();
            Pair<Long, Long> range = Pair.of(x, x + randomWeight - 1);
            weightsMap.put(range, e);
            x += randomWeight;
        }
        long random = (long) (Math.random() * x);
        Enchantment e = null;
        for(Map.Entry<Pair<Long, Long>, Enchantment> ent : weightsMap.entrySet()) {
            Pair<Long, Long> range = ent.getKey();
            if(random >= range.getLeft() && random <= range.getRight()) {
                e = ent.getValue();
                break;
            }
        }
        upgradeEnchantment(is, e, 1);
        return e;
    }
    
    public boolean isConflicting(ItemStack is, Enchantment e) {
        ItemMeta meta = is.getItemMeta();
        
        for(Map.Entry<Enchantment, Integer> ent : meta.getEnchants().entrySet()) {
            if(ent.getValue() > 0 && !ent.getKey().equals(e)) {
                if(e.conflictsWith(ent.getKey())) {
                    return true;
                }
            }
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
                int lvl = toMeta.getEnchantLevel(e);
                toMeta.addEnchant(e, Math.max(lvl, l), true);    
            }
        });
        to.setItemMeta(toMeta);
    }
    
}
