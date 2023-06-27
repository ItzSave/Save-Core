package org.itzsave.module.modules;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;
import org.itzsave.module.Module;
import org.itzsave.module.ModuleType;
import org.itzsave.utils.TextUtils;

public class CustomCommandsModule extends Module implements Listener {


    public CustomCommandsModule(SaveCore plugin) {
        super(plugin, ModuleType.CUSTOM_COMMANDS);
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
        // We don't have to handle anything.
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCustomCommand(PlayerCommandPreprocessEvent event) {
        try {
            getConfig(ConfigType.SETTINGS).getConfigurationSection("Custom-Commands.").getKeys(false).forEach(
                    command -> {
                        if (command.equalsIgnoreCase(event.getMessage().split(" ")[0].replace("/", ""))) {
                            event.setCancelled(true);
                            getConfig(ConfigType.SETTINGS).getStringList("Custom-Commands." + command + ".message").forEach(line -> event.getPlayer().sendMessage(TextUtils.color(PlaceholderAPI.setPlaceholders(event.getPlayer(), (line)))));

                        }
                    }
            );
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

}
