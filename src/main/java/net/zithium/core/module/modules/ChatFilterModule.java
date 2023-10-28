package net.zithium.core.module.modules;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.zithium.core.ZithiumCore;
import net.zithium.core.config.ConfigType;
import net.zithium.core.module.Module;
import net.zithium.core.module.ModuleType;
import net.zithium.core.utils.TextUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;

import java.util.List;

public class ChatFilterModule extends Module {
    private FileConfiguration config;

    public ChatFilterModule(ZithiumCore plugin) {
        super(plugin, ModuleType.CHAT);
    }

    @Override
    public void onEnable() {
        config = getConfig(ConfigType.SETTINGS);
        getPlugin().getComponentLogger().info(TextUtils.color("[Module] Loaded chat filtering module"));
    }

    @Override
    public void onDisable() {
        // Nothing to handle here.
    }


    @EventHandler
    public void onPlayerChat(AsyncChatEvent event){
        Component messageComponent = event.message();
        String message = LegacyComponentSerializer.legacySection().serialize(messageComponent);
        String[] words = message.split(" ");

        for (String word : words) {
            if (isBlockedWord(word)) {
                event.setCancelled(true);
                return;
            }
        }
    }


    private  boolean isBlockedWord(String word){
        List<String> blockedWords = config.getStringList("Chat-Filter.blocked-words");
        return blockedWords.contains(word.toLowerCase());
    }
}
