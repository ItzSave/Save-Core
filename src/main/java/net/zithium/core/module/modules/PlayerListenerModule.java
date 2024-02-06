package net.zithium.core.module.modules;

import net.kyori.adventure.text.Component;
import net.zithium.core.config.ConfigType;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.Module;
import net.zithium.core.utils.TextUtils;
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

    private Component joinMessage;
    private Component leaveMessage;

    public PlayerListenerModule(ZithiumCore plugin) {
        super(plugin, ModuleType.PLAYER_LISTENER);
    }

    public void onEnable() {
        FileConfiguration config = getConfig(ConfigType.SETTINGS);

        joinMessagesEnabled = config.getBoolean("Players.disable-join-messages");
        quitMessagesEnabled = config.getBoolean("Players.disable-quit-messages");
        useCustomMessages = config.getBoolean("Players.use-custom-join-quit-message");

        joinMessage = TextUtils.color(config.getString("Players.custom-messages.join-message"));
        leaveMessage = TextUtils.color(config.getString("Players.custom-messages.quit-message"));

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
                event.joinMessage(joinMessage);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (quitMessagesEnabled) {
            if (!useCustomMessages) {
                event.quitMessage(null);
            } else {
                event.quitMessage(leaveMessage);
            }
        }

    }
}
