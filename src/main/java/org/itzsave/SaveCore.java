package org.itzsave;

import me.mattstudios.mf.base.CommandManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.itzsave.commands.SaveCoreCommand;
import org.itzsave.config.ConfigManager;
import org.itzsave.module.ModuleManager;

@SuppressWarnings("unused")
public final class SaveCore extends JavaPlugin {

    private ConfigManager configManager;
    private ModuleManager moduleManager;

    public LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();


        CommandManager cm = new CommandManager(this);
        cm.register(new SaveCoreCommand(this));


        configManager = new ConfigManager();
        configManager.loadFiles(this);

        moduleManager = new ModuleManager();
        moduleManager.loadModules(this);

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            //new PlaceholderHandler().register();
            getLogger().info("[Hook] Loading PlaceholderAPI functions.");
        } else {
            getLogger().severe("WARNING: PlaceholderAPI is not installed.");
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        moduleManager.unloadModules();
    }

    public void onReload() {

        HandlerList.unregisterAll(this);
        reloadConfig();
        configManager.reloadFiles();
        moduleManager.loadModules(this);
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }


}
