/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.actions;

import pl.matix.epicenchant.actions.EeActionType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.config.EeConfigAction;

/**
 *
 * @author Mati
 * @param <T>
 */
public abstract class EeActionHandler<T extends EeConfigAction> {

    protected final Class<T> configClass;
    
    public EeActionHandler(Class<T> configClass) {
        this.configClass = configClass;
    }
    
    public abstract boolean validateBeforeAction(EpicEnchant ee,
            Player player, ItemStack is, Enchantment e, 
            int currentEnchantLevel, T config);
    
    public abstract void performAction(EpicEnchant ee,
            Player player, ItemStack is, Enchantment e, 
            int currentEnchantLevel, T config);
    
    public abstract void showInfoAction(EpicEnchant ee,
            Player player, ItemStack is, Enchantment e, 
            int currentEnchantLevel, T config);
    
    public Class<T> getConfigClass() {
        return configClass;
    }
    
}
