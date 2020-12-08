/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.permissions;

import java.util.Set;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.permissions.PermissionDefault;
import pl.matix.epicenchant.commands.EeCommand;

/**
 *
 * @author Mati
 */
public enum EpicEnchantPermission {
    
    SIGN_CREATE("create"),
    SIGN_USE("use", PermissionDefault.TRUE),
    
    UPGRADE_FAIL_DOWNGRADE_IMMUNITY("upgrade.fail_downgrade_immunity"),
    
    UPGRADE_COST_90(EePermissionsConstants.UPGRADE_COST_PREFIX + "90"),
    UPGRADE_COST_75(EePermissionsConstants.UPGRADE_COST_PREFIX + "75"),
    UPGRADE_COST_50(EePermissionsConstants.UPGRADE_COST_PREFIX + "50"),
    UPGRADE_COST_25(EePermissionsConstants.UPGRADE_COST_PREFIX + "25"),
    
    UPGRADE_CHANCE_110(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "110"),
    UPGRADE_CHANCE_125(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "125"),
    UPGRADE_CHANCE_150(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "150"),
    UPGRADE_CHANCE_200(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "200"),
    UPGRADE_CHANCE_300(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "300"),
    UPGRADE_CHANCE_500(EePermissionsConstants.UPGRADE_CHANCE_PREFIX + "500"),
    
    CMD_EXECUTE("command")
    ;

    private final String permission;
    private final PermissionDefault def;
    
    private EpicEnchantPermission(String perm, PermissionDefault def) {
        this.def = def;
        this.permission = EePermissionsConstants.PREFIX + perm;
    }
    
    private EpicEnchantPermission(String perm) {
        this(perm, PermissionDefault.FALSE);
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
    
    public PermissionDefault getDefault() {
        return def;
    }
    
    public Permission toPermission() {
        return new Permission(permission, def);
    }
    
    public static Integer getBestUpgradeCostPermission(Player player) {
        String prefix = EePermissionsConstants.PREFIX + EePermissionsConstants.UPGRADE_COST_PREFIX;
        Integer best = null;
        Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
        for (PermissionAttachmentInfo perm : perms) {
            String p = perm.getPermission();
            if(p.startsWith(prefix)) {
                try {
                    Integer value = Integer.parseInt(p.substring(prefix.length()));
                    if(value > 0) {
                        if(best == null || value < best) {
                            best = value;
                        }
                    }
                } catch(NumberFormatException ex) {}
            }
        }
        return best;
    }
    
    public static Integer getBestUpgradeChancePermission(Player player) {
        String prefix = EePermissionsConstants.PREFIX + EePermissionsConstants.UPGRADE_CHANCE_PREFIX;
        Integer best = null;
        Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
        for (PermissionAttachmentInfo perm : perms) {
            String p = perm.getPermission();
            if(p.startsWith(prefix)) {
                try {
                    Integer value = Integer.parseInt(p.substring(prefix.length()));
                    if(value > 0) {
                        if(best == null || value > best) {
                            best = value;
                        }
                    }
                } catch(NumberFormatException ex) {}
            }
        }
        return best;
    }
}
