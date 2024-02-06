package net.zithium.core.module.modules;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.zithium.core.module.Module;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.ModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ChatFormatModule extends Module implements Listener {

    public ChatFormatModule(ZithiumCore plugin) {
        super(plugin, ModuleType.CHAT);
    }

    @Override
    public void onEnable() {

        try {
            getPlugin().getServer().getPluginManager().getPlugin("LuckPerms");
            getPlugin().getLogger().log(Level.INFO, "[Module] Loaded chat formatting module.");
        } catch (Exception ex) {
            getPlugin().getLogger().log(Level.SEVERE, "[Module] Chat formatting module is enabled without LuckPerms installed!");
            getPlugin().getServer().getPluginManager().disablePlugin(getPlugin());
        }
    }

    @Override
    public void onDisable() {
        getPlugin().getLogger().log(Level.INFO, "<green>[Module] Unloaded chat formatting module.");
    }

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        event.renderer(new SaveCoreChatRenderer());
    }

    static class SaveCoreChatRenderer implements ChatRenderer {
        final ZithiumCore plugin = ZithiumCore.getPlugin(ZithiumCore.class);


        @Override
        public @NotNull Component render(@NotNull Player source, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience viewer) {

            FileConfiguration config = plugin.getConfig();

            final CachedMetaData metaData = plugin.getLuckPerms().getPlayerAdapter(Player.class).getMetaData(source);
            final String group = metaData.getPrimaryGroup();

            @Nullable String format = config.getString(config.getString("Chat-Formats.group-formats." + group) != null ? "Chat-Formats.group-formats." + group : "Chat-Formats.default");

            format = plugin.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI") ? PlaceholderAPI.setPlaceholders(source, format) : format;

            List<TagResolver> resolverList = new ArrayList<>(List.of(Placeholder.component("message", message)));

            return MiniMessage.miniMessage().deserialize(format + "<message>", TagResolver.resolver(resolverList));
        }
    }
}
