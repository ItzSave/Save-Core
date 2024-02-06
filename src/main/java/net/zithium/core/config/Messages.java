package net.zithium.core.config;

import net.zithium.core.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@SuppressWarnings("unused")
public enum Messages {

    RELOADED("reloaded"),
    NO_PERMISSION("no-permission"),
    RULES("rules"),
    HELP("help"),
    INVALID_PLAYER("invalid-player"),
    INVALID_ARGUMENT("invalid-arguments"),
    AUTO_TRASH_HELP("auto_trash.help"),
    AUTO_TRASH_NULL_ITEM("auto_trash.null_item"),
    AUTO_TRASH_ITEM_ADDED("auto_trash.item_added"),
    ENTITY_CLEAR_WARNING("entity-clear.warning"),
    ENTITY_CLEAR_FINISHED("entity-clear.finished"),
    ENTITY_CLEAR_TIMER_COMMAND("entity-clear.timer");


    private final String path;

    private static FileConfiguration config;


    Messages(String path) {
        this.path = path;
    }

    public static void setConfiguration(FileConfiguration c) {
        config = c;
    }

    public void send(CommandSender sender, Object... replacements) {
        Object value = config.get("messages." + this.path);

        String configMessage;
        if (value == null) {
            configMessage = "<red>Zithium Message was not found! (" + this.path + ")";
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
