package org.itzsave.modules;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;

@SuppressWarnings("unused")
public abstract class Module implements Listener {

    private SaveCore plugin;
    private ModuleType moduleType;

    public Module(SaveCore plugin, ModuleType type) {

        this.plugin = plugin;
        this.moduleType = type;
    }

    public SaveCore getPlugin() {
        return plugin;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public FileConfiguration getConfig(ConfigType type) {
        return getPlugin().getConfigManager().getFile(type).getConfig();
    }

    public abstract void onEnable();

    public abstract void onDisable();


}
