/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.commands;

import java.util.stream.Collectors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginDescriptionFile;
import pl.matix.epicenchant.EpicEnchant;

/**
 *
 * @author Mati
 */
public class InfoCommand extends EeCommand {

    public InfoCommand(EpicEnchant ee) {
        super(ee, "info");
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        PluginDescriptionFile desc = ee.getDescription();
        String authors = desc.getAuthors().stream().collect(Collectors.joining(", "));
        ee.sendMessage(cs, desc.getName()+" "+desc.getVersion()+" by "+authors);
        return true;
    }

    @Override
    public PermissionDefault getDefaultPermission() {
        return PermissionDefault.TRUE;
    }
    
}
