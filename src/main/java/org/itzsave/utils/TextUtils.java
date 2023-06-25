package org.itzsave.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@SuppressWarnings({"unused", "deprecation"})
public class TextUtils {

    /**
     *
     * @param message The message to color.
     * @return Colored Message
     */
    public static @NotNull Component color(String message) {
        return MiniMessage.miniMessage().deserialize(message);
    }

    public static String fromList(List<?> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (ChatColor.stripColor(list.get(i).toString()).equals("")) builder.append("\n&r");
            else builder.append(list.get(i).toString()).append(i + 1 != list.size() ? "\n" : "");
        }

        return builder.toString();
    }
}
