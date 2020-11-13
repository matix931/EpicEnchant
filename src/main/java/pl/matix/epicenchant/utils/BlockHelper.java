/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.bukkit.Material;

/**
 *
 * @author Mati
 */
public class BlockHelper {
    
    public static final Map<Material, Integer[]> expTable = new HashMap<>();
    static {
        expTable.put(Material.COAL_ORE, new Integer[] { 0, 2 });
        expTable.put(Material.NETHER_GOLD_ORE, new Integer[] { 0, 1 });
        expTable.put(Material.DIAMOND_ORE, new Integer[] { 3, 7 });
        expTable.put(Material.EMERALD_ORE, new Integer[] { 3, 7 });
        expTable.put(Material.LAPIS_ORE, new Integer[] { 2, 5 });
        expTable.put(Material.NETHER_QUARTZ_ORE, new Integer[] { 2, 5 });
        expTable.put(Material.REDSTONE_ORE, new Integer[] { 1, 5 });
    }
    
    public static int rollExpFor(Material m) {
        Integer[] range = expTable.get(m);
        if(range == null) {
            return 0;
        }
        Random r = new Random();
        return range[0] + r.nextInt(range[1] - range[0] + 1);
    }
    
}
