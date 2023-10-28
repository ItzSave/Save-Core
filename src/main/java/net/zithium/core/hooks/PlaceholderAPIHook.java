package net.zithium.core.hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.ModuleType;
import net.zithium.core.module.modules.EntityClearModule;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import net.zithium.core.utils.TextUtils;
import org.jetbrains.annotations.NotNull;

public class PlaceholderAPIHook extends PlaceholderExpansion {

    final ZithiumCore plugin = ZithiumCore.getPlugin(ZithiumCore.class);

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "zcore";
    }

    @Override
    public @NotNull String getAuthor() {
        return "Zithium Studios";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }


    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        Player p = player.getPlayer();

        // Placeholder: %zcore_total_exp%
        if (params.equalsIgnoreCase("total_exp")) {
            return TextUtils.numberFormat(p.getTotalExperience());
        }

        // Placeholder: %zcore_entity_clear_time%
        if (params.equalsIgnoreCase("entity_clear_time")) {
            if (plugin.getModuleManager().isEnabled(ModuleType.ENTITY_CLEAR)) {
                return EntityClearModule.getTimeLeft();
            } else {
                return "null";
            }
        }

        return null;
    }
}
