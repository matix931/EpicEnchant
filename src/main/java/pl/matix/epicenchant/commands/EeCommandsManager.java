/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.permissions.EpicEnchantPermission;

/**
 *
 * @author Mati
 */
public class EeCommandsManager implements CommandExecutor, TabCompleter {

    private final EpicEnchant ee;
    private final Map<String, EeCommand> cmds = new HashMap<>();

    public EeCommandsManager(EpicEnchant ee) {
        this.ee = ee;
        
        registerCommand(new ReloadCommand(ee));
        registerCommand(new InfoCommand(ee));
    }
    
    private void registerCommand(EeCommand eecmd) {
        cmds.put(eecmd.getCommand(), eecmd);
    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if(args == null || args.length == 0) {
            return false;
        }
        
        String subcommand = args[0];
        EeCommand eecmd = cmds.get(subcommand.toLowerCase());
        if(eecmd == null) {
            return false;
        }
        
        if(!eecmd.hasPermission(cs)) {
            ee.sendMessage(cs, EeLocale.CMD_NO_PERMISSION);
            return true;
        }
        
        return eecmd.onCommand(cs, cmd, label, args);
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String label, String[] args) {
        String subcommand = args.length==0 ? "" : args[0];
        if(subcommand==null) {
            subcommand = "";
        } else {
            subcommand = subcommand.toLowerCase();
        }
        EeCommand eecmd = cmds.get(subcommand);
        if(eecmd==null) {
            if(args.length > 1) {
                return new ArrayList<>();
            } else {
                List<String> list = new ArrayList<>();
                for (Map.Entry<String, EeCommand> ent : cmds.entrySet()) {
                    if(ent.getKey().startsWith(subcommand) && ent.getValue().hasPermission(cs)) {
                        list.add(ent.getKey());
                    }
                }
                return list;
            }
        }
        
        if(!eecmd.hasPermission(cs)) {
            return new ArrayList<>();
        }
        
        return eecmd.onTabComplete(cs, cmd, label, args);
    }

    public Map<String, EeCommand> getCmds() {
        return cmds;
    }
    
}
