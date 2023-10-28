package net.zithium.core.module;

import net.zithium.core.ZithiumCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import net.zithium.core.config.ConfigType;

public abstract class Module implements Listener {

    private final ZithiumCore plugin;
    private final ModuleType moduleType;

    public Module(ZithiumCore plugin, ModuleType moduleType) {

        this.plugin = plugin;
        this.moduleType = moduleType;
    }

    public ZithiumCore getPlugin() {
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
