package net.zithium.core.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


@SuppressWarnings("deprecation")
public class TextUtils {
    private final static NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.US);

    public static String fromList(List<?> list) {
        if (list == null || list.isEmpty()) return null;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (ChatColor.stripColor(list.get(i).toString()).equals("")) builder.append("\n<reset>");
            else builder.append(list.get(i).toString()).append(i + 1 != list.size() ? "\n" : "");
        }

        return builder.toString();
    }

    public static String numberFormat(long amount) {
        return NUMBER_FORMAT.format(amount);
    }

    /**
     * @param seconds The time unit in seconds to be formatted.
     * @return The formatted duration
     */
    public static String formatTime(long seconds) {
        long sec = seconds % 60;
        long minutes = seconds % 3600 / 60;
        long hours = seconds % 86400 / 3600;
        long days = seconds / 86400;

        if (days > 0) return String.format("%02dd %02dh %02dm %02ds", days, hours, minutes, sec);
        else if (hours > 0) return String.format("%02dh %02dm %02ds", hours, minutes, sec);
        else if (minutes > 0) return String.format("%02dm %02ds", minutes, sec);
        return String.format("%02ds", sec);
    }

    /**
     * Colorizes a text message containing legacy color codes or MiniMessage formatting.
     *
     * <p>If MiniMessage is compatible, this method converts the provided message with MiniMessage
     * and then serializes it to a legacy string format. If MiniMessage is not available on the
     * server, it falls back to translating legacy color codes using ChatColor.</p>
     *
     * @param message The text message with legacy color codes or MiniMessage formatting.
     * @return The colorized message as a plain string.
     */
    public static Component color(String message) {
        return MiniMessage.miniMessage().deserialize(replaceLegacy(message));
    }

    /**
     * Replaces legacy color codes (e.g., "&1" for dark blue) with Adventure format codes
     * (e.g., "<dark_blue>").
     *
     * @param legacyText The text containing legacy color codes.
     * @return The text with Adventure format codes.
     */
    private static String replaceLegacy(String legacyText) {
        return legacyText
                .replaceAll("&1", "<dark_blue>")
                .replaceAll("&2", "<dark_green>")
                .replaceAll("&3", "<dark_aqua>")
                .replaceAll("&4", "<dark_red>")
                .replaceAll("&5", "<dark_purple>")
                .replaceAll("&6", "<gold>")
                .replaceAll("&7", "<gray>")
                .replaceAll("&8", "<dark_gray>")
                .replaceAll("&9", "<blue>")
                .replaceAll("&a", "<green>")
                .replaceAll("&b", "<aqua>")
                .replaceAll("&c", "<red>")
                .replaceAll("&d", "<light_purple>")
                .replaceAll("&e", "<yellow>")
                .replaceAll("&f", "<white>")
                .replaceAll("&l", "<bold>")
                .replaceAll("&k", "<obfuscated>")
                .replaceAll("&m", "<strikethrough>")
                .replaceAll("&n", "<underline>")
                .replaceAll("&r", "<reset>")
                .replaceAll("&o", "<italic>");
    }
}
