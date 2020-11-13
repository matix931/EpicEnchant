/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import static org.bukkit.Material.*;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Mati
 */
public enum ToolGroup {
    
    AXE(WOODEN_AXE, STONE_AXE, IRON_AXE, GOLDEN_AXE, DIAMOND_AXE, NETHERITE_AXE), 
    PICKAXE(WOODEN_PICKAXE, STONE_PICKAXE, IRON_PICKAXE, GOLDEN_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE),
    SHOVEL(WOODEN_SHOVEL, STONE_SHOVEL, IRON_SHOVEL, GOLDEN_SHOVEL, DIAMOND_SHOVEL, NETHERITE_SHOVEL),
    HOE(WOODEN_HOE, STONE_HOE, IRON_HOE, GOLDEN_HOE, DIAMOND_HOE, NETHERITE_HOE);
    
    private static final Map<Material, ToolGroup> map = new HashMap<>();
    static {
        for(ToolGroup g : values()) {
            g.tools.forEach(b -> map.put(b, g));
        }
    }
    
    private final List<Material> tools;
    
    private ToolGroup(Material... blocks) {
        this.tools = Arrays.asList(blocks);
    }

    public List<Material> getTools() {
        return tools;
    }
    
    public static ToolGroup getToolGroup(ItemStack is) {
        return map.get(is.getType());
    }
}
