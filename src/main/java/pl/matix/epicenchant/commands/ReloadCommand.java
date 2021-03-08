/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import pl.matix.epicenchant.EpicEnchant;
import pl.matix.epicenchant.locale.EeLocale;

/**
 *
 * @author Mati
 */
public class ReloadCommand extends EeCommand {

    public ReloadCommand(EpicEnchant ee) {
        super(ee, "reload");
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        if(ee.reloadEeConfig()) {
            ee.sendMessage(cs, EeLocale.CMD_RELOAD_SUCCESS);
        } else {
            ee.sendMessage(cs, EeLocale.CMD_RELOAD_FAILED);
        }
        return true;
    }
    
}
