/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.listeners;

import org.bukkit.event.Listener;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public abstract class EeListener implements Listener {
    
    protected final EpicEnchant ee;
    
    public EeListener(EpicEnchant ee) {
        this.ee = ee;
    }
    
}
