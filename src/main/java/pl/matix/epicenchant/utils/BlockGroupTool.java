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
import org.bukkit.block.Block;

/**
 *
 * @author Mati
 */
public enum BlockGroupTool {
    
    PICKAXES_1(ToolGroup.PICKAXE, 1,
        REDSTONE_BLOCK,
            
        ICE, PACKED_ICE, BLUE_ICE, FROSTED_ICE,
            
        ANDESITE, BASALT, BLACKSTONE, COAL_BLOCK, QUARTZ_BLOCK, BRICKS, COAL_ORE,
        COBBLESTONE, 
            
        WHITE_CONCRETE, ORANGE_CONCRETE, MAGENTA_CONCRETE, LIGHT_BLUE_CONCRETE, 
        YELLOW_CONCRETE, LIME_CONCRETE, PINK_CONCRETE, GRAY_CONCRETE, 
        LIGHT_GRAY_CONCRETE, CYAN_CONCRETE, PURPLE_CONCRETE, BLUE_CONCRETE, 
        BROWN_CONCRETE, GREEN_CONCRETE, RED_CONCRETE, BLACK_CONCRETE,
            
        DARK_PRISMARINE, DIORITE, END_STONE, 
            
        WHITE_GLAZED_TERRACOTTA, ORANGE_GLAZED_TERRACOTTA, MAGENTA_GLAZED_TERRACOTTA, LIGHT_BLUE_GLAZED_TERRACOTTA, 
        YELLOW_GLAZED_TERRACOTTA, LIME_GLAZED_TERRACOTTA, PINK_GLAZED_TERRACOTTA, GRAY_GLAZED_TERRACOTTA, 
        LIGHT_GRAY_GLAZED_TERRACOTTA, CYAN_GLAZED_TERRACOTTA, PURPLE_GLAZED_TERRACOTTA, BLUE_GLAZED_TERRACOTTA, 
        BROWN_GLAZED_TERRACOTTA, GREEN_GLAZED_TERRACOTTA, RED_GLAZED_TERRACOTTA, BLACK_GLAZED_TERRACOTTA,
            
        GRANITE, MOSSY_COBBLESTONE, NETHER_BRICKS, NETHER_GOLD_ORE, NETHER_QUARTZ_ORE,
        NETHERRACK, PRISMARINE, PRISMARINE_BRICKS,
            
        POLISHED_ANDESITE, POLISHED_BLACKSTONE, POLISHED_BLACKSTONE_BRICKS, 
        POLISHED_DIORITE, POLISHED_GRANITE,
            
        RED_SANDSTONE, SANDSTONE, 
            
        Material.TERRACOTTA,
        WHITE_TERRACOTTA, ORANGE_TERRACOTTA, MAGENTA_TERRACOTTA, LIGHT_BLUE_TERRACOTTA, 
        YELLOW_TERRACOTTA, LIME_TERRACOTTA, PINK_TERRACOTTA, GRAY_TERRACOTTA, 
        LIGHT_GRAY_TERRACOTTA, CYAN_TERRACOTTA, PURPLE_TERRACOTTA, BLUE_TERRACOTTA, 
        BROWN_TERRACOTTA, GREEN_TERRACOTTA, RED_TERRACOTTA, BLACK_TERRACOTTA,
            
        SMOOTH_STONE, SMOOTH_QUARTZ, SMOOTH_RED_SANDSTONE, SMOOTH_SANDSTONE,
            
        STONE, STONE_BRICKS, 
            
        CHISELED_NETHER_BRICKS, CHISELED_POLISHED_BLACKSTONE, CHISELED_QUARTZ_BLOCK,
        CHISELED_RED_SANDSTONE, CHISELED_SANDSTONE, CHISELED_STONE_BRICKS,
            
        QUARTZ_PILLAR, QUARTZ_BRICKS,
        
        CUT_RED_SANDSTONE, CUT_SANDSTONE,
            
        RED_NETHER_BRICKS, 
        
        CRACKED_NETHER_BRICKS, CRACKED_POLISHED_BLACKSTONE_BRICKS, CRACKED_STONE_BRICKS,
            
        MOSSY_STONE_BRICKS
    ),
    
    PICKAXES_2(ToolGroup.PICKAXE, 2, new Material[] { STONE_PICKAXE, IRON_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE},
        IRON_BLOCK, LAPIS_BLOCK,
        IRON_ORE, LAPIS_ORE
    ),
    
    PICKAXES_3(ToolGroup.PICKAXE, 3, new Material[] { IRON_PICKAXE, DIAMOND_PICKAXE, NETHERITE_PICKAXE},
        DIAMOND_ORE, EMERALD_ORE, GOLD_ORE, REDSTONE_ORE
    ),
    
    PICKAXES_4(ToolGroup.PICKAXE, 4, new Material[] { DIAMOND_PICKAXE, NETHERITE_PICKAXE},
        ANCIENT_DEBRIS, CRYING_OBSIDIAN, OBSIDIAN
    ),
    
    AXES(ToolGroup.AXE, 1,
        BOOKSHELF,
            
        OAK_PLANKS, SPRUCE_PLANKS, BIRCH_PLANKS, JUNGLE_PLANKS,
        ACACIA_PLANKS, DARK_OAK_PLANKS, CRIMSON_PLANKS, WARPED_PLANKS,
            
        OAK_LOG, SPRUCE_LOG, BIRCH_LOG, JUNGLE_LOG, 
        ACACIA_LOG, DARK_OAK_LOG, CRIMSON_STEM, WARPED_STEM,
            
        OAK_WOOD, SPRUCE_WOOD, BIRCH_WOOD, JUNGLE_WOOD, 
        ACACIA_WOOD, DARK_OAK_WOOD, CRIMSON_HYPHAE, WARPED_HYPHAE,
            
        STRIPPED_OAK_LOG, STRIPPED_SPRUCE_LOG, STRIPPED_BIRCH_LOG, STRIPPED_JUNGLE_LOG, 
        STRIPPED_ACACIA_LOG, STRIPPED_DARK_OAK_LOG, STRIPPED_CRIMSON_STEM, STRIPPED_WARPED_STEM
    ),
    
    SHOVELS(ToolGroup.SHOVEL, 1,
        CLAY, COARSE_DIRT, 
         
        WHITE_CONCRETE_POWDER, ORANGE_CONCRETE_POWDER, MAGENTA_CONCRETE_POWDER, LIGHT_BLUE_CONCRETE_POWDER, 
        YELLOW_CONCRETE_POWDER, LIME_CONCRETE_POWDER, PINK_CONCRETE_POWDER, GRAY_CONCRETE_POWDER, 
        LIGHT_GRAY_CONCRETE_POWDER, CYAN_CONCRETE_POWDER, PURPLE_CONCRETE_POWDER, BLUE_CONCRETE_POWDER, 
        BROWN_CONCRETE_POWDER, GREEN_CONCRETE_POWDER, RED_CONCRETE_POWDER, BLACK_CONCRETE_POWDER,
            
        DIRT, FARMLAND, GRASS_BLOCK, GRAVEL, MYCELIUM, PODZOL, RED_SAND, SAND,
        SOUL_SAND, SOUL_SOIL, SNOW_BLOCK, SNOW
    ),
    
    HOE(ToolGroup.HOE, 1,
        OAK_LEAVES, SPRUCE_LEAVES, BIRCH_LEAVES, JUNGLE_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES,
        NETHER_WART_BLOCK, SHROOMLIGHT, WARPED_WART_BLOCK
    )
    ;
    
    private static final Map<Material, BlockGroupTool> blocksMap = new HashMap<>();
    static {
        for(BlockGroupTool g : values()) {
            g.blocks.forEach(b -> blocksMap.put(b, g));
        }
    }
    
    private final ToolGroup toolGroup;
    private final int tier;
    private final List<Material> tools;
    private final List<Material> blocks;

    private BlockGroupTool(ToolGroup toolGroup, int tier, Material... blocks) {
        this(toolGroup, tier, toolGroup.getTools(), blocks);
    }
    
    private BlockGroupTool(ToolGroup toolGroup, int tier, Material[] tools, Material... blocks) {
        this(toolGroup, tier, Arrays.asList(tools), blocks);
    }
    
    private BlockGroupTool(ToolGroup toolGroup, int tier, List<Material> tools, Material... blocks) {
        this.toolGroup = toolGroup;
        this.tier = tier;
        this.blocks = Arrays.asList(blocks);        
        this.tools = tools;
    }

    public List<Material> getBlocks() {
        return blocks;
    }

    public List<Material> getTools() {
        return tools;
    }
    
    public static BlockGroupTool getGroupByBlock(final Block block) {
        Material m = block.getType();
        return blocksMap.get(m);
    }

    public int getTier() {
        return tier;
    }

    public ToolGroup getToolGroup() {
        return toolGroup;
    }
    
}
