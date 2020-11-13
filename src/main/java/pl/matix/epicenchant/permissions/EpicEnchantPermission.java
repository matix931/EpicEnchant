/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.permissions;

import org.bukkit.entity.Player;

/**
 *
 * @author Mati
 */
public enum EpicEnchantPermission {
    
    SIGN_CREATE("epicenchant.create")
    ;

    private final String permission;
    
    private EpicEnchantPermission(String perm) {
        this.permission = perm;
    }

    public String getPermission() {
        return permission;
    }
    
    public static boolean has(Player player, EpicEnchantPermission permission) {
        return player.hasPermission(permission.getPermission());
    }
}
