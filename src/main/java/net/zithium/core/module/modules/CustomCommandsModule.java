package net.zithium.core.module.modules;

import me.clip.placeholderapi.PlaceholderAPI;
import net.zithium.core.config.ConfigType;
import net.zithium.core.module.Module;
import net.zithium.core.utils.TextUtils;
import net.zithium.library.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.ModuleType;

import java.util.logging.Level;

public class CustomCommandsModule extends Module implements Listener {


    public CustomCommandsModule(ZithiumCore plugin) {
        super(plugin, ModuleType.CUSTOM_COMMANDS);
    }

    @Override
    public void onEnable() {
        // We don't have to handle anything.

        getPlugin().getLogger().log(Level.INFO, "[Module] Loaded custom commands module.");
    }

    @Override
    public void onDisable() {
        // We don't have to handle anything.
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCustomCommand(PlayerCommandPreprocessEvent event) {
        FileConfiguration config = getConfig(ConfigType.SETTINGS);
        Player player = event.getPlayer();
        try {
            config.getConfigurationSection("Custom-Commands.").getKeys(false).forEach(
                    command -> {
                        if (command.equalsIgnoreCase(event.getMessage().split(" ")[0].replace("/", ""))) {
                            event.setCancelled(true);
                            config.getStringList("Custom-Commands." + command + ".message").forEach(line -> player.sendMessage(Color.stringColor(PlaceholderAPI.setPlaceholders(player, (line)))));

                        }
                    }
            );
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

}
