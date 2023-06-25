package org.itzsave.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.itzsave.SaveCore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class ConfigManager {

    private final Map<ConfigType, ConfigHandler> configurations;

    public ConfigManager() {
        configurations = new HashMap<>();
    }

    public void loadFiles(SaveCore plugin) {

        registerFile(ConfigType.MESSAGES, new ConfigHandler(plugin, "lang"));
        registerFile(ConfigType.SETTINGS, new ConfigHandler(plugin, "config"));

        configurations.values().forEach(ConfigHandler::saveDefaultConfig);

        Messages.setConfiguration(getFile(ConfigType.MESSAGES).getConfig());

    }

    public ConfigHandler getFile(ConfigType type) {
        return configurations.get(type);
    }

    public void reloadFiles() {
        configurations.values().forEach(ConfigHandler::reload);
        Messages.setConfiguration(getFile(ConfigType.MESSAGES).getConfig());
    }

    public void registerFile(ConfigType type, ConfigHandler config) {
        configurations.put(type, config);
    }

    public FileConfiguration getFileConfiguration(File file) {
        return YamlConfiguration.loadConfiguration(file);
    }
}
