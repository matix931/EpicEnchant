/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.events.DestroyerBlockBreakEvent;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;
import pl.matix.epicenchant.utils.BlockGroupTool;
import pl.matix.epicenchant.utils.BlockHelper;
import pl.matix.epicenchant.utils.ToolGroup;

/**
 *
 * @author Mati
 */
public class BlockBreakListener extends EeListener {
    
    public BlockBreakListener(EpicEnchant ee) {
        super(ee);
    }
    
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerInteract(BlockBreakEvent event) {
        if(event instanceof DestroyerBlockBreakEvent) {
            return;
        }
        
        if(event.isCancelled()) {
            return;
        }
        
        Player player = event.getPlayer();
        if(!player.isOnline()) {
            return;
        }

        final Block block = event.getBlock();        
        if(block instanceof Sign) {
            Sign sign = (Sign) block;
            if(ee.getSignHelper().isSignEpicEnchant(sign)) {
                if (!EpicEnchantPermission.has(player, EpicEnchantPermission.SIGN_CREATE)) {
                    ee.sendChatMessage(player, EeLocale.NO_PERMISSION);
                    event.setCancelled(true);
                    return;
                }
            }
        }
        
        ItemStack is = player.getInventory().getItemInMainHand();
        ItemMeta meta = is.getItemMeta();
        if(meta == null) {
            return;
        }
        
        final int destroyerLevel = meta.getEnchantLevel(ee.getEnchantRegistry().DESTROYER);
        if(destroyerLevel > 0) {
            if(player.isSneaking()) {
                return;
            }
            ToolGroup toolGroup = ToolGroup.getToolGroup(is);
            if(toolGroup == null) {
                return;
            }
            BlockGroupTool blockGroup = BlockGroupTool.getGroupByBlock(block);
            if(blockGroup == null) {
                return;
            }
            if(blockGroup.getToolGroup() != toolGroup) {
                return;
            
            }
            List<Block> blocks = getBlocksToBreakFor(toolGroup, destroyerLevel, player, block);
            
            int additionalExp = 0;
            for(Block b : blocks) {
                Material bm = b.getType();
                BlockGroupTool bGroup = BlockGroupTool.getGroupByBlock(b);
                if(bGroup == null) {
                    continue;
                }
                if(bGroup.getToolGroup() != blockGroup.getToolGroup()) {
                    continue;
                }
                if(bGroup.getTier() > blockGroup.getTier()) {
                    continue;
                }
                
                DestroyerBlockBreakEvent newEvent = new DestroyerBlockBreakEvent(b, player);
                ee.getServer().getPluginManager().callEvent(newEvent);
                
                if(!newEvent.isCancelled()) {
                    if(b.breakNaturally(is)) {
                        additionalExp += BlockHelper.rollExpFor(bm);
                    }
                }
            }
            event.setExpToDrop(event.getExpToDrop() + additionalExp);
        }
    }
    
    private List<Block> getBlocksToBreakFor(ToolGroup toolGroup, int level, Player player, Block targetBlock) {
        List<Block> blocks = new ArrayList<>();
        BlockFace targetBlockFace = getTargetBlockFace(player);
        BlockFace playerBlockFace = player.getFacing();
        if(targetBlockFace==null) {
            targetBlockFace = playerBlockFace.getOppositeFace();
        }
        switch(toolGroup) {
            case PICKAXE:
            case AXE: {
                if(level >= 1) {
                    BlockFace bf = getNextSideVertical(playerBlockFace, targetBlockFace);
                    blocks.add(targetBlock.getRelative(bf));
                    blocks.add(targetBlock.getRelative(bf.getOppositeFace()));
                }
                if(level >= 2) {
                    List<Block> toAdd = new ArrayList<>();
                    for(Block b : blocks) {
                        BlockFace bf = getNextSideHorizontal(playerBlockFace, targetBlockFace);
                        toAdd.add(b.getRelative(bf));
                        toAdd.add(b.getRelative(bf.getOppositeFace()));
                    }
                    blocks.addAll(toAdd);
                    BlockFace bf = getNextSideHorizontal(playerBlockFace, targetBlockFace);
                    blocks.add(targetBlock.getRelative(bf));
                    blocks.add(targetBlock.getRelative(bf.getOppositeFace()));
                }
                break;
            }
            case SHOVEL:
            case HOE: {
                if(level >= 1) {
                    BlockFace bf = getNextSideHorizontal(playerBlockFace, targetBlockFace);
                    blocks.add(targetBlock.getRelative(bf));
                    blocks.add(targetBlock.getRelative(bf.getOppositeFace()));
                }
                if(level >= 2) {
                    List<Block> toAdd = new ArrayList<>();
                    toAdd.add(targetBlock.getRelative(playerBlockFace, 1));
                    toAdd.add(targetBlock.getRelative(playerBlockFace, 2));
                    for(Block b : toAdd) {
                        BlockFace bf = getNextSideHorizontal(playerBlockFace, targetBlockFace);
                        blocks.add(b.getRelative(bf));
                        blocks.add(b.getRelative(bf.getOppositeFace()));
                    }
                    blocks.addAll(toAdd);
                }
                break;
            }
        }
        return blocks;
    }
    
    public BlockFace getTargetBlockFace(Player player) {
        List<Block> lastTwoTargetBlocks = player.getLastTwoTargetBlocks(null, 15);
        if (lastTwoTargetBlocks.size() != 2 || !lastTwoTargetBlocks.get(1).getType().isOccluding()) return null;
        Block targetBlock = lastTwoTargetBlocks.get(1);
        Block adjacentBlock = lastTwoTargetBlocks.get(0);
        return targetBlock.getFace(adjacentBlock);
    }
    
    public BlockFace getNextSide(BlockFace bf) {
        switch(bf) {
            case WEST:
            case EAST: {
                return BlockFace.NORTH;
            }
            case NORTH:
            case SOUTH: {
                return BlockFace.EAST;
            }
            default: return BlockFace.UP;
        }
    }
    
    public BlockFace getNextSideVertical(BlockFace playerFace, BlockFace blockFace) {
        switch(blockFace) {
            case DOWN: 
            case UP: {
                return playerFace;
            }
            default: return BlockFace.UP;
        }
    }
    
    public BlockFace getNextSideHorizontal(BlockFace playerFace, BlockFace blockFace) {
        switch(blockFace) {
            case DOWN: 
            case UP: {
                return getNextSide(playerFace);
            }
            default: return getNextSide(blockFace);
        }
    }
    
}
