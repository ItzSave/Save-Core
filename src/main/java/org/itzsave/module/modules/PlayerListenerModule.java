package org.itzsave.module.modules;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;
import org.itzsave.module.Module;
import org.itzsave.module.ModuleType;
import org.itzsave.utils.TextUtils;

public class PlayerListenerModule extends Module implements Listener {

    private boolean joinMessagesEnabled;
    private boolean quitMessagesEnabled;
    private boolean useCustomMessages;

    private String joinMessage;
    private String leaveMessage;

    final SaveCore plugin = SaveCore.getPlugin(SaveCore.class);

    public PlayerListenerModule(SaveCore plugin) {
        super(plugin, ModuleType.PLAYER_LISTENER);
    }

    public void onEnable() {
        FileConfiguration config = getConfig(ConfigType.SETTINGS);

        joinMessagesEnabled = config.getBoolean("Players.disable-join-messages");
        quitMessagesEnabled = config.getBoolean("Players.disable-quit-messages");
        useCustomMessages = config.getBoolean("Players.use-custom-join-quit-message");

        joinMessage = config.getString("Players.custom-join-message");
        leaveMessage = config.getString("Players.custom-quit-message");

        plugin.getLogger().info("[Module] Loaded player listener");
    }

    @Override
    public void onDisable() {

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoin(PlayerJoinEvent event) {
        if (joinMessagesEnabled) {
            if (!useCustomMessages) {
                event.joinMessage(null);
            } else {
                event.joinMessage(TextUtils.color(joinMessage.replace("%player%", event.getPlayer().getName())));
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (quitMessagesEnabled) {
            if (!useCustomMessages) {
                event.quitMessage(null);
            } else {
                event.quitMessage(TextUtils.color(leaveMessage.replace("%player%", event.getPlayer().getName())));
            }
        }

    }
}
