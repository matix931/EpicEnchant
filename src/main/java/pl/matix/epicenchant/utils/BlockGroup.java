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

/**
 *
 * @author Mati
 */
public enum BlockGroup {
    
    ORE(
        COAL_ORE, IRON_ORE, GOLD_ORE, LAPIS_ORE, REDSTONE_ORE, 
        EMERALD_ORE, DIAMOND_ORE, NETHER_QUARTZ_ORE, NETHER_GOLD_ORE, ANCIENT_DEBRIS
    ),
    
    OBSIDIAN(
        Material.OBSIDIAN, CRYING_OBSIDIAN
    ),
    
    ORE_BLOCK(
        COAL_BLOCK, IRON_BLOCK, GOLD_BLOCK, LAPIS_BLOCK, REDSTONE_BLOCK, 
        EMERALD_BLOCK, DIAMOND_BLOCK, QUARTZ_BLOCK, NETHERITE_BLOCK
    ),
    
    STONE_NATURAL(
        ANDESITE, BASALT, BLACKSTONE, COBBLESTONE, STONE, GRANITE, DIORITE, 
        END_STONE, MOSSY_COBBLESTONE, NETHERRACK, RED_SANDSTONE, SANDSTONE
    ),
    
    STONES_CRAFTED(
        BRICKS, NETHER_BRICKS, PRISMARINE, PRISMARINE_BRICKS, DARK_PRISMARINE,

        POLISHED_ANDESITE, POLISHED_BLACKSTONE, POLISHED_BLACKSTONE_BRICKS, 
        POLISHED_DIORITE, POLISHED_GRANITE,

        SMOOTH_STONE, STONE_BRICKS
    ),
    
    TERRACOTTA(
        Material.TERRACOTTA,
        WHITE_TERRACOTTA, ORANGE_TERRACOTTA, MAGENTA_TERRACOTTA, LIGHT_BLUE_TERRACOTTA, 
        YELLOW_TERRACOTTA, LIME_TERRACOTTA, PINK_TERRACOTTA, GRAY_TERRACOTTA, 
        LIGHT_GRAY_TERRACOTTA, CYAN_TERRACOTTA, PURPLE_TERRACOTTA, BLUE_TERRACOTTA, 
        BROWN_TERRACOTTA, GREEN_TERRACOTTA, RED_TERRACOTTA, BLACK_TERRACOTTA
    ),
    
    GLAZED_TERRACOTTA(
        WHITE_GLAZED_TERRACOTTA, ORANGE_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, LIGHT_BLUE_GLAZED_TERRACOTTA, 
        YELLOW_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA, PINK_GLAZED_TERRACOTTA, GRAY_GLAZED_TERRACOTTA, 
        LIGHT_GRAY_GLAZED_TERRACOTTA, CYAN_GLAZED_TERRACOTTA, PURPLE_GLAZED_TERRACOTTA, BLUE_GLAZED_TERRACOTTA, 
        BROWN_GLAZED_TERRACOTTA, GREEN_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA, BLACK_GLAZED_TERRACOTTA   
    ),
    
    CONCRETE(
        WHITE_CONCRETE, ORANGE_CONCRETE, MAGENTA_CONCRETE, LIGHT_BLUE_CONCRETE, 
        YELLOW_CONCRETE, LIME_CONCRETE, PINK_CONCRETE, GRAY_CONCRETE, 
        LIGHT_GRAY_CONCRETE, CYAN_CONCRETE, PURPLE_CONCRETE, BLUE_CONCRETE, 
        BROWN_CONCRETE, GREEN_CONCRETE, RED_CONCRETE, BLACK_CONCRETE
    )
    ;
    
    private final List<Material> blocks;
    
    private final Map<Material, BlockGroup> map = new HashMap<>();

    private BlockGroup(Material... blocks) {
        this.blocks = Arrays.asList(blocks);
        this.blocks.forEach(b -> map.put(b, this));
    }

    public List<Material> getBlocks() {
        return blocks;
    }
    
    public BlockGroup getGroup(Material m) {
        return map.get(m);
    }
    
}
