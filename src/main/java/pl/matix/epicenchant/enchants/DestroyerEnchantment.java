/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.enchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public class DestroyerEnchantment extends EeCustomEnchantment {

    private static final String ID = "Destroyer";
    
    public DestroyerEnchantment(EpicEnchant ee) {
        super(ee, ID);
    }

    @Override
    public String getName() {
        return ID;
    }

    @Override
    public int getMaxLevel() {
        return 2;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.TOOL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(Enchantment e) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack is) {
        return true;
    }

    @Override
    protected String getLoreName() {
        return ID;
    }
    
}
