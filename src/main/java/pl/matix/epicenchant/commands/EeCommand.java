/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.commands;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.permissions.PermissionDefault;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;

/**
 *
 * @author Mati
 */
public abstract class EeCommand implements CommandExecutor, TabCompleter {

    protected final EpicEnchant ee;
    protected final String command;
    protected final String permission;

    public EeCommand(EpicEnchant ee, String command) {
        this.ee = ee;
        this.command = command;
        this.permission = EpicEnchantPermission.CMD_EXECUTE.getPermission()+"."+command;
    }

    public String getCommand() {
        return command;
    }
    
    public String getPermission() {
        return permission;
    }
    
    public boolean hasPermission(CommandSender cs) {
        return cs.hasPermission(permission);
    }
    
    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmnd, String string, String[] strings) {
        return new ArrayList<>();
    }
    
    public PermissionDefault getDefaultPermission() {
        return PermissionDefault.FALSE;
    }
}
