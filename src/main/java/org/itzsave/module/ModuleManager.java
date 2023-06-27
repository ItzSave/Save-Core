package org.itzsave.module;

import org.bukkit.event.HandlerList;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;
import org.itzsave.module.modules.AnnouncerModule;
import org.itzsave.module.modules.ChatFormatModule;
import org.itzsave.module.modules.CustomCommandsModule;
import org.itzsave.module.modules.PlayerListenerModule;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ModuleManager {

    private SaveCore plugin;
    private final Map<ModuleType, Module> modules = new HashMap<>();

    public void loadModules(SaveCore plugin) {
        this.plugin = plugin;

        if (!modules.isEmpty()) unloadModules();

        registerModule(new PlayerListenerModule(plugin), "Modules.player-listener");
        registerModule(new AnnouncerModule(plugin), "Modules.auto-announcer");
        registerModule(new ChatFormatModule(plugin), "Modules.chat-format");
        registerModule(new CustomCommandsModule(plugin), "Modules.custom-commands");

        for (Module module : modules.values()) {
            try {
                module.onEnable();
            } catch (Exception e) {
                e.printStackTrace();
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                break;
            }
        }

        plugin.getLogger().info("Loaded " + modules.size() + " modules.");

    }

    public void registerModule(Module module, String isEnabledPath) {
        SaveCore plugin = module.getPlugin();
        if (isEnabledPath != null && !plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getBoolean(isEnabledPath, false))
            return;


        plugin.getServer().getPluginManager().registerEvents(module, plugin);
        modules.put(module.getModuleType(), module);


    }


    public void unloadModules() {
        for (Module module : modules.values()) {
            try {
                HandlerList.unregisterAll(module);
                module.onDisable();
            } catch (Exception e) {
                e.printStackTrace();
                plugin.getLogger().severe("[ERROR] There was an error unloading the modules!");
            }
        }
        modules.clear();
    }

    public Module getModule(ModuleType type) {
        return modules.get(type);
    }

    public void registerModule(Module module) {
        registerModule(module, null);
    }

    public boolean isEnabled(ModuleType type) {
        return modules.containsKey(type);
    }


    public int getModulesAmount(){
        return modules.size();
    }

}
