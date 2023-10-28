package net.zithium.core.module;

import net.zithium.core.ZithiumCore;
import net.zithium.core.module.modules.*;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import net.zithium.core.config.ConfigType;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;


public class ModuleManager {

    private ZithiumCore plugin;
    private final Map<ModuleType, Module> modules = new HashMap<>();

    public void loadModules(ZithiumCore plugin) {
        this.plugin = plugin;

        if (!modules.isEmpty()) unloadModules();

        registerModule(new PlayerListenerModule(plugin), "Modules.player-listener");
        registerModule(new AnnouncerModule(plugin), "Modules.auto-announcer");
        registerModule(new ChatFormatModule(plugin), "Modules.chat-format");
        registerModule(new CustomCommandsModule(plugin), "Modules.custom-commands");
        registerModule(new AutoTrashModule(plugin), "Modules.auto-trash");
        registerModule(new ChatFilterModule(plugin), "Modules.chat-filter");

        if (Bukkit.getPluginManager().isPluginEnabled("RoseStacker")) {
            plugin.getLogger().log(Level.INFO, "[Hook] Loaded RoseStacker hook");
            registerModule(new EntityClearModule(plugin), "Modules.entity-clear");
        } else {
            plugin.getLogger().log(Level.SEVERE, "[Hook] Entity clear module is enabled without rose stacker!");
            plugin.getConfig().set("Modules.entity-clear", false);
            plugin.getConfigManager().getFile(ConfigType.SETTINGS).save();
        }


        for (Module module : modules.values()) {
            try {
                module.onEnable();
            } catch (Exception ex) {
                plugin.getLogger().log(Level.SEVERE, "Plugin encountered a fatal error. Will now disable", ex);
                plugin.getServer().getPluginManager().disablePlugin(plugin);
                break;
            }
        }

        plugin.getLogger().info("Loaded " + modules.size() + " modules.");

    }

    public void registerModule(Module module, String isEnabledPath) {
        ZithiumCore plugin = module.getPlugin();
        if (isEnabledPath != null && !plugin.getConfigManager().getFile(ConfigType.SETTINGS).getConfig().getBoolean(isEnabledPath, false))
            return;


        plugin.getServer().getPluginManager().registerEvents(module, plugin);
        modules.put(module.getModuleType(), module);


    }


    public void unloadModules() {

        if (this == null) return;

        for (Module module : modules.values()) {
            try {
                HandlerList.unregisterAll(module);
                module.onDisable();
            } catch (Exception e) {
                plugin.getLogger().log(Level.SEVERE, "Plugin encountered a fatal error while trying to unload modules.", e);
            }
        }
        modules.clear();
    }

    @SuppressWarnings("unused")
    public Module getModule(ModuleType type) {
        return modules.get(type);
    }

    @SuppressWarnings("unused")
    public void registerModule(Module module) {
        registerModule(module, null);
    }

    @SuppressWarnings("unused")
    public boolean isEnabled(ModuleType type) {
        return modules.containsKey(type);
    }


    public int getModulesAmount() {
        return modules.size();
    }

}
