/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.enchants;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang.WordUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfigActionUpgrade;
import pl.matix.epicenchant.config.EeConfigEnchantEntry;
import pl.matix.epicenchant.config.EeConfigRandom;

/**
 *
 * @author Mati
 */
public class EnchantmentsRegistry {
    
    public static final String[] LEVEL_FORMATS = new String[] {
        "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"
    };
    
    public static String formatLevel(int level) {
        if(level<=0 || level > 10) {
            return String.valueOf(level);
        }
        return LEVEL_FORMATS[level-1];
    }
    
    private final Set<EeCustomEnchantment> customEnchants = new HashSet<>();
    private final Map<String, Enchantment> otherEnchants = new HashMap<>();
    private final Map<Enchantment, String> enchantsPrettyNames = new HashMap<>();
    
    public final RandomEnchantment RANDOM;
    public final DestroyerEnchantment DESTROYER;
    
    private EpicEnchant ee;

    public EnchantmentsRegistry(EpicEnchant ee) throws Exception {
        this.ee = ee;
        this.RANDOM = createCustomEnchantment(RandomEnchantment.class);
        this.DESTROYER = createCustomEnchantment(DestroyerEnchantment.class);
        
        init();
    }
    
    private void init() throws Exception {
        Field byKeyField = Enchantment.class.getDeclaredField("byKey");
        byKeyField.setAccessible(true);
        HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) byKeyField.get(null);
        byKey.forEach((k, e) -> {
            String key = k.getKey();
            ee.showConsoleInfo("Found enchantment: {0}", key);
            otherEnchants.put(key.toLowerCase(), e);
            registerPrettyName(e);
        });
        byKeyField.setAccessible(false);
    }
    
    public void initAfterConfigLoad() {
        
    }
    
    private <T extends EeCustomEnchantment> T createCustomEnchantment(Class<T> clz) throws Exception {
        T enchant = clz.getConstructor(EpicEnchant.class).newInstance(ee);
        customEnchants.add(enchant);
        registerPrettyName(enchant);
        return enchant;
    }
    
    public Enchantment getEnchantmentByKey(String s, boolean startsWith) {
        if(s == null || s.isEmpty()) {
            return null;
        }
        s = s.trim().toLowerCase().replace(" ", "_");
        for(Enchantment e : otherEnchants.values()) {
            if(startsWith) {
                if(e.getKey().getKey().toLowerCase().startsWith(s)) {
                    return e;
                }
            } else {
                if(e.getKey().getKey().toLowerCase().equals(s)) {
                    return e;
                }    
            }
        }
        for(EeCustomEnchantment ce : customEnchants) {
            if(startsWith) {
                if(ce.getKey().getKey().toLowerCase().startsWith(s)) {
                    return ce;
                }
            } else {
                if(ce.getKey().getKey().toLowerCase().equals(s)) {
                    return ce;
                }    
            }
        }
        return null;
    }
    
    public List<Enchantment> getAvailableEnchantmentsForRandom(ItemStack is) {
        List<Enchantment> list = new ArrayList<>();
        ItemMeta meta = is.getItemMeta();
        if(meta != null) {
            List<Enchantment> maxedEnchants = new ArrayList<>();
            List<Enchantment> allEnchants = new ArrayList<>();
            for(Map.Entry<Enchantment, Integer> ent : meta.getEnchants().entrySet()) {
                EeConfigEnchantEntry conf = ee.getEeConfig().getEnchantmentConfig(ent.getKey());
                EeConfigActionUpgrade actionUpgrade = conf.getActionUpgrade();
                if(ent.getValue() <= 0) {
                    continue;
                }
                if(ent.getValue() >= actionUpgrade.getMaxLevel()) {
                    maxedEnchants.add(ent.getKey());
                }
                allEnchants.add(ent.getKey());
            }
            
            List<Enchantment> allAvailableEnchantments = getAllAvailableEnchantments();
            for(Enchantment e : allAvailableEnchantments) {
                EeConfigEnchantEntry conf = ee.getEeConfig().getEnchantmentConfig(e);
                EeConfigActionUpgrade actionUpgrade = conf.getActionUpgrade();
                if(actionUpgrade.getRandomWeight() <= 0) {
                    continue;
                }
                if(maxedEnchants.contains(e)) {
                    continue;
                }
                if(!e.canEnchantItem(is)) {
                    continue;
                }
                if(ee.getEnchantments().isConflicting(is, e)) {
                    continue;
                }
                list.add(e);
            }
        }
        return list;
    }
    
    private void registerPrettyName(Enchantment e) {
        enchantsPrettyNames.put(e, WordUtils.capitalizeFully(e.getKey().getKey().replace("_", " ")));
    }
    
    public String getPrettyName(Enchantment e) {
        return enchantsPrettyNames.get(e);
    }

    public Set<EeCustomEnchantment> getCustomEnchants() {
        return customEnchants;
    }

    public Map<String, Enchantment> getOtherEnchants() {
        return otherEnchants;
    }
    
    public List<Enchantment> getAllAvailableEnchantments() {
        List<Enchantment> list = new ArrayList<>();
        list.addAll(otherEnchants.values());
        list.addAll(customEnchants.stream()
                .filter(ce -> !ce.equals(RANDOM))
                .collect(Collectors.toList()));
        return list;
    }
    
}
