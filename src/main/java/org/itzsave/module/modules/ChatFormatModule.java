package org.itzsave.module.modules;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.itzsave.SaveCore;
import org.itzsave.module.Module;
import org.itzsave.module.ModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ChatFormatModule extends Module implements Listener {

    public ChatFormatModule(SaveCore plugin) {
        super(plugin, ModuleType.CHAT);
    }

    @Override
    public void onEnable() {

        if (getPlugin().getServer().getPluginManager().getPlugin("LuckPerms") != null) {
            getPlugin().luckPerms = getPlugin().getServer().getServicesManager().load(LuckPerms.class);
            getPlugin().getLogger().info("[Module] Loaded chat formatter module.");
        } else {
            getPlugin().getLogger().warning("[Module] Chat formatting module is enabled without LuckPerms installed!");
            getPlugin().setEnabled(false);
        }
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(new SaveCoreChatRenderer());
    }

    static class SaveCoreChatRenderer implements ChatRenderer {
        final SaveCore plugin = SaveCore.getPlugin(SaveCore.class);

        @Override
        public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

            FileConfiguration config = plugin.getConfig();

            final CachedMetaData metaData = plugin.luckPerms.getPlayerAdapter(Player.class).getMetaData(source);
            final String group = metaData.getPrimaryGroup();

            @Nullable String format = config.getString(config.getString("Chat-Formats.group-formats." + group) != null ? "Chat-Formats.group-formats." + group : "Chat-Formats.default");

            format = plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(source, format) : format;

            List<TagResolver> resolverList = new ArrayList<>(List.of(Placeholder.component("message", message)));

            return MiniMessage.miniMessage().deserialize(format + "<message>", TagResolver.resolver(resolverList));
        }
    }
}
