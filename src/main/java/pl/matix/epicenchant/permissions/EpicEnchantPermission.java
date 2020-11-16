/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.permissions;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

/**
 *
 * @author Mati
 */
public enum EpicEnchantPermission {
    
    SIGN_CREATE("epicenchant.create"),
    SIGN_USE("epicenchant.use", true),
    
    UPGRADE_FAIL_DOWNGRADE_IMMUNITY("epicenchant.upgrade.fail_downgrade_immunity"),
    
    UPGRADE_COST_90("epicenchant.upgrade.cost.percent90"),
    UPGRADE_COST_75("epicenchant.upgrade.cost.percent75"),
    UPGRADE_COST_50("epicenchant.upgrade.cost.percent50"),
    UPGRADE_COST_25("epicenchant.upgrade.cost.percent25"),
    
    UPGRADE_CHANCE_150("epicenchant.upgrade.chance.percent150"),
    UPGRADE_CHANCE_200("epicenchant.upgrade.chance.percent200"),
    UPGRADE_CHANCE_300("epicenchant.upgrade.chance.percent300"),
    UPGRADE_CHANCE_500("epicenchant.upgrade.chance.percent500"),
    ;

    private final String permission;
    private final boolean isDefault;
    
    private EpicEnchantPermission(String perm, boolean isDefault) {
        this.isDefault = isDefault;
        this.permission = perm;
    }
    
    private EpicEnchantPermission(String perm) {
        this(perm, false);
    }

    public String getPermission() {
        return permission;
    }
    
    public static boolean has(Player player, EpicEnchantPermission permission) {
        if(player.isOp()) {
            return true;
        }
        return player.hasPermission(permission.getPermission());
    }

    public boolean isDefault() {
        return isDefault;
    }
    
    public Permission toPermission() {
        return new Permission(permission, isDefault ? PermissionDefault.TRUE : PermissionDefault.FALSE);
    }
}
