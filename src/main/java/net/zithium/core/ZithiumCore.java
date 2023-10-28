package net.zithium.core;

import me.mattstudios.mf.base.CommandManager;
import net.luckperms.api.LuckPerms;
import net.zithium.core.commands.DonationCommand;
import net.zithium.core.commands.RulesCommand;
import net.zithium.core.commands.ZithiumCommand;
import net.zithium.core.config.ConfigManager;
import net.zithium.core.config.Messages;
import net.zithium.core.hooks.PlaceholderAPIHook;
import net.zithium.core.module.ModuleManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.stream.Stream;


@SuppressWarnings("unused")
public final class ZithiumCore extends JavaPlugin {

    private ConfigManager configManager;
    private ModuleManager moduleManager;

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().log(Level.INFO, "------ [ LOAD START ] ------]");

        saveDefaultConfig();

        // Checking if luck perms is installed before attempting to load functionality.
        if (Bukkit.getPluginManager().getPlugin("LuckPerms") != null) {
            luckPerms = getServer().getServicesManager().load(LuckPerms.class);
            getLogger().log(Level.INFO,"[Hook] Loaded LuckPerms hook.");
        } else {
            getLogger().log(Level.SEVERE,"[WARNING] LuckPerms is not installed you may encounter errors!");
        }

        // Loading and registering the commands
        CommandManager commandManager = initializeCommandManager();
        registerCommands(commandManager);

        // Load configuration files
        configManager = new ConfigManager();
        configManager.loadFiles(this);

        // Load and initialize modules.
        moduleManager = new ModuleManager();
        moduleManager.loadModules(this);


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPIHook().register();
            getLogger().log(Level.INFO,"[Hook] Loaded PlaceholderAPI hook");
        } else {
            getLogger().log(Level.SEVERE,"[Hook] PlaceholderAPI not installed.");
        }
        getLogger().log(Level.INFO,"<blue>------ [ LOAD COMPLETE ] ------]");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (moduleManager != null) {
            moduleManager.unloadModules();
        }
    }

    public void onReload() {

        HandlerList.unregisterAll(this);
        reloadConfig();
        configManager.reloadFiles();
        moduleManager.loadModules(this);

    }


    // Loading the command manager and setting the default no permission message to the config one.
    private CommandManager initializeCommandManager() {
        CommandManager commandManager = new CommandManager(this);
        commandManager.getMessageHandler().register("cmd.no.permission", Messages.NO_PERMISSION::send);
        return commandManager;
    }

    private void registerCommands(CommandManager commandManager) {
        Stream.of(
                new DonationCommand(this),
                new ZithiumCommand(this),
                new RulesCommand()
        ).forEach(commandManager::register);
    }


    public ConfigManager getConfigManager() {
        return configManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

}
