package net.zithium.core.module.modules;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.zithium.core.config.ConfigType;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.Module;
import net.zithium.library.utils.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import net.zithium.core.module.ModuleType;

import java.util.logging.Level;

public class PlayerListenerModule extends Module implements Listener {

    private boolean joinMessagesEnabled;
    private boolean quitMessagesEnabled;
    private boolean useCustomMessages;

    private String joinMessage;
    private String leaveMessage;

    public PlayerListenerModule(ZithiumCore plugin) {
        super(plugin, ModuleType.PLAYER_LISTENER);
    }

    public void onEnable() {
        FileConfiguration config = getConfig(ConfigType.SETTINGS);

        joinMessagesEnabled = config.getBoolean("Players.disable-join-messages");
        quitMessagesEnabled = config.getBoolean("Players.disable-quit-messages");
        useCustomMessages = config.getBoolean("Players.use-custom-join-quit-message");

        joinMessage = Color.stringColor(config.getString("Players.custom-messages.join-message"));
        leaveMessage = Color.stringColor(config.getString("Players.custom-messages.quit-message"));

        getPlugin().getLogger().log(Level.INFO,"[Module] Loaded player listener module");
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
                Component componentJoinMessage = MiniMessage.miniMessage().deserialize(joinMessage);
                event.joinMessage(componentJoinMessage);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (quitMessagesEnabled) {
            if (!useCustomMessages) {
                event.quitMessage(null);
            } else {
                Component componentQuitMessage = MiniMessage.miniMessage().deserialize(leaveMessage);
                event.quitMessage(componentQuitMessage);
            }
        }

    }
}
