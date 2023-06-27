package org.itzsave.config;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.itzsave.utils.TextUtils;

import java.util.List;

@SuppressWarnings("unused")
public enum Messages {

    RELOADED("reloaded", "<green>SaveCore has been reloaded!"),
    NO_PERMISSION("no-permission", "<red>You do not have permission to use this command!"),
    RULES("rules", "<red>Rules message is missing!");


    private final String path;
    private final String def;

    private static FileConfiguration config;


    Messages(String path, String def) {
        this.path = path;
        this.def = def;
    }

    public static void setConfiguration(FileConfiguration c) {
        config = c;
    }

    public void send(CommandSender sender, Object... replacements) {
        Object value = config.getString("Messages." + this.path, this.def);

        String configMessage;
        if (value == null) {
            configMessage = "SaveCore: Message was not found! (" + this.path + ")";
        } else {
            configMessage = value instanceof List ? TextUtils.fromList((List<?>) value) : value.toString();
        }

        if (!configMessage.isEmpty()) {
            sender.sendMessage(TextUtils.color(replace(configMessage, replacements)));
        }
    }

    private String replace(String message, Object... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 >= replacements.length) break;
            message = message.replace(String.valueOf(replacements[i]), String.valueOf(replacements[i + 1]));
        }
        return message;
    }


}
