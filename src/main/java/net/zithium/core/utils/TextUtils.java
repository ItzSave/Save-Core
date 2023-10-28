package net.zithium.core.utils;

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
     *
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
}
