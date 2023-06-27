package org.itzsave.module;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;

public abstract class Module implements Listener {

    private final SaveCore plugin;
    private final ModuleType moduleType;

    public Module(SaveCore plugin, ModuleType moduleType) {

        this.plugin = plugin;
        this.moduleType = moduleType;
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
