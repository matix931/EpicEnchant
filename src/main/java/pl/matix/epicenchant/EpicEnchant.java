/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.matix.epicenchant;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.apache.commons.io.FileUtils;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import pl.matix.epicenchant.config.EeConfig;
import pl.matix.epicenchant.enchants.EnchantmentsRegistry;
import pl.matix.epicenchant.enchants.EeEnchantmentsManager;
import pl.matix.epicenchant.listeners.EnchantingListener;
import pl.matix.epicenchant.listeners.BlockBreakListener;
import pl.matix.epicenchant.listeners.EeListener;
import pl.matix.epicenchant.listeners.PlayerListener;
import pl.matix.epicenchant.listeners.SignCreateListener;
import pl.matix.epicenchant.locale.EeLocale;
import pl.matix.epicenchant.locale.EeLocaleFile;
import pl.matix.epicenchant.locale.LocaleLanguage;
import pl.matix.epicenchant.sign.SignHelper;

/**
 *
 * @author Mati
 */
public class EpicEnchant extends JavaPlugin {

    private final PluginManager pm;
    private final Server server;
    private final Logger log;
    
    private Economy economy;
    private Permission perms;
    
    private EeConfig config;
    
    private final String pluginName;
    private final String messagePrefix;
    private final String logPrefix;
    private final String signPrefix;
    private final String signPrefixColored;
    
    //paths
    private String pluginFolderPath;
    
    //utils
    private SignHelper signHelper;
    private EnchantmentsRegistry enchantRegistry;
    private EeEnchantmentsManager enchantments;
    
    public EpicEnchant() {
        this.server = getServer();
        this.pm = server.getPluginManager();
        this.log = server.getLogger();
        this.pluginName = getDescription().getName();
        this.messagePrefix = "§3[§c" + pluginName + "§3]§f";
        this.logPrefix = "\033[0;36m[\033[0;31m" + pluginName + "\033[0;36m]\033[0m";
        this.signPrefix = "[" + pluginName.toLowerCase() + "]";
        this.signPrefixColored = "§3[§c" + pluginName + "§3]§f";
        this.pluginFolderPath = "plugins/"+pluginName;
        
        this.signHelper = new SignHelper(this);
        this.enchantments = new EeEnchantmentsManager(this);
        
        try {
            this.enchantRegistry = new EnchantmentsRegistry(this);
        } catch (Exception ex) {
            Logger.getLogger(EpicEnchant.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void onEnable() {
        if(this.enchantRegistry == null) {
            showConsoleError("Disabled due to custom enchantments registration error!");
            pm.disablePlugin(this);
            return;
        }
        
        if (!setupEconomy() ) {
            showConsoleError("Disabled due to no Vault [Economy] dependency found!");
            pm.disablePlugin(this);
            return;
        }
        if(!setupPermissions()) {
            showConsoleError("Disabled due to no Vault [Permissions] dependency found!");
            pm.disablePlugin(this);
            return;
        }
        
        try {
            loadConfig();
        } catch (IOException ex) {
            showConsoleError("Disabled due to configuration file loading error");
            log.log(Level.SEVERE, null, ex);
            pm.disablePlugin(this);
            return;
        }
        
        try {
            loadLocale();
        } catch (IOException ex) {
            showConsoleError("Disabled due to localization file loading error");
            log.log(Level.SEVERE, null, ex);
            pm.disablePlugin(this);
            return;
        }
        
        try {
            setupListeners();
        } catch (Exception ex) {
            showConsoleError("Disabled due to registering listeners error");
            log.log(Level.SEVERE, null, ex);
            pm.disablePlugin(this);
            return;
        }
        
        try {
            setupCustomEnchantments();
        } catch (Exception ex) {
            showConsoleError("Disabled due to registering custom enchantments error");
            log.log(Level.SEVERE, null, ex);
            pm.disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        if(enchantments!=null) {
            try {
                enchantments.unregister();
            } catch (Exception ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private boolean setupEconomy() {
        if (pm.getPlugin("Vault") == null) {
            showConsoleError("No Vault plugin");
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            showConsoleError("No Economy provider");
            return false;
        }
        economy = rsp.getProvider();
        if(economy==null) {
            showConsoleError("Null Economy provider");
        }
        return economy != null;
    }
    
    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = server.getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        perms = rsp.getProvider();
        return perms != null;
    }
    
    private void setupListeners() throws Exception {
        registerListener(BlockBreakListener.class);
        registerListener(SignCreateListener.class);
        registerListener(PlayerListener.class);
        registerListener(EnchantingListener.class);
        showConsoleInfo("Listeners registered");
    }
    
    private void registerListener(Class<? extends EeListener> listener) throws Exception {
        EeListener ll = listener.getConstructor(EpicEnchant.class).newInstance(this);
        pm.registerEvents(ll, this);
    }

    public Permission getPerms() {
        return perms;
    }

    public Economy getEconomy() {
        return economy;
    }

    public EeEnchantmentsManager getEnchantments() {
        return enchantments;
    }
    
    public void showConsoleError(String error, Object... args) {
        log.log(Level.SEVERE, String.format("%s %s", logPrefix, error), args);
    }
    
    public void showConsoleInfo(String info, Object... args) {
        log.log(Level.INFO, String.format("%s %s", logPrefix, info), args);
    }
    
    public void sendChatMessage(Player player, String msg) {
        player.sendMessage(String.format("%s %s", messagePrefix, msg));
    }
    
    public void sendChatMessage(Player player, EeLocale locale) {
        player.sendMessage(String.format("%s %s", messagePrefix, locale.getText()));
    }
    
    public void sendChatMessage(Player player, EeLocale locale, Map<String, String> params) {
        player.sendMessage(String.format("%s %s", messagePrefix, locale.getText(params)));
    }

    public String getSignPrefix() {
        return signPrefix;
    }

    public String getSignPrefixColored() {
        return signPrefixColored;
    }
    
    private void loadConfig() throws IOException {
        File pluginFolder = new File(pluginFolderPath);
        if(!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }
        File configFile = new File(pluginFolderPath + "/config.json");
        ObjectMapper om = new ObjectMapper();
        if(!configFile.exists()) {
            showConsoleInfo("No configuration file found, creating default one");
            InputStream is = getClass().getResourceAsStream("/config.json");
            FileUtils.copyInputStreamToFile(is, configFile);
        }
        showConsoleInfo("Loading configuration file");
        config = om.readValue(configFile, EeConfig.class);    
        showConsoleInfo("Configuration file loaded");
    }

    private void loadLocale() throws IOException {
        LocaleLanguage language = config.getLanguage();
        File localeFile = new File(pluginFolderPath + "/locale_"+language.name().toLowerCase()+".json");
        ObjectMapper om = new ObjectMapper();
        if(!localeFile.exists() && language == LocaleLanguage.EN) {
            //create default locale file
            localeFile.createNewFile();
            EeLocaleFile locale = EeLocaleFile.createDefault();
            om.writerWithDefaultPrettyPrinter().writeValue(localeFile, locale);
        } else {
            EeLocaleFile locale = om.readValue(localeFile, EeLocaleFile.class);
            locale.forEach((l, txt) -> {
                l.setText(txt);
            });
        }
    }

    public SignHelper getSignHelper() {
        return signHelper;
    }

    private void setupCustomEnchantments() throws Exception {
        enchantments.register();
    }

    public String getPluginName() {
        return pluginName;
    }

    public EnchantmentsRegistry getEnchantRegistry() {
        return enchantRegistry;
    }

    public EeConfig getEeConfig() {
        return config;
    }
    
}
