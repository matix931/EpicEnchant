/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public class EnchantingListener extends EeListener {
    
    public EnchantingListener(EpicEnchant ee) {
        super(ee);
    }
    
    @EventHandler
    public void onPrepareAnvil(PrepareAnvilEvent evt) {
        ItemStack result = evt.getResult();
        if(result == null) {
            return;
        }
        ItemMeta resultMeta = result.getItemMeta();
        if(resultMeta == null) {
            return;
        }
        
        ItemStack sourceItem = evt.getInventory().getItem(0);
        ItemStack secondaryItem = evt.getInventory().getItem(1);
        
        if(sourceItem != null) {
            ee.getEnchantments().copyEnchants(sourceItem, result, false);
//            boolean doubleCost = false;
            if(secondaryItem != null) {
                ee.getEnchantments().copyEnchants(secondaryItem, result, false);
//                if(ee.getEnchantments().hasItemCustomEnchnts(secondaryItem)) {
//                    doubleCost = true;
//                }
            }
//            if(ee.getEnchantments().hasItemCustomEnchnts(sourceItem)) {
//                doubleCost = true;
//            }
        }
    }
    
}
