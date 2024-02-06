package net.zithium.core.commands;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.zithium.core.config.Messages;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.ModuleType;
import net.zithium.core.module.modules.EntityClearModule;
import net.zithium.core.utils.TextUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SuppressWarnings({"unused", "deprecation"})
@Command("zithium")
@Alias("zcore")
public class ZithiumCommand extends CommandBase {

    private final ZithiumCore plugin;

    public ZithiumCommand(ZithiumCore plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(Player player) {
        Messages.HELP.send(player);
    }


    @SubCommand("reload")
    @Permission({"zcore.admin", "zcore.command.reload"})
    public void reloadCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            // If the sender is not a player, perform a reload and send a message
            sender.sendMessage("ZithiumCore has been reloaded.");
        }

        long startTime = System.currentTimeMillis(); // Record the start time
        plugin.onReload(); // Perform the plugin reload

        if (sender instanceof Player) {
            // If the sender is a player, send a message with the time it took to reload
            long elapsedTime = System.currentTimeMillis() - startTime;
            Messages.RELOADED.send(sender, "%ms%", String.valueOf(elapsedTime));
        }
    }

    @SubCommand("info")
    @Permission({"zcore.admin", "zcore.command.info"})
    public void infoCommand(Player player) {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedTime = localDateTime.format(formatter);

        player.sendMessage(TextUtils.color("<reset>"));
        player.sendMessage(TextUtils.color("<green>Server Version: <reset>" + plugin.getServer().getVersion()));
        player.sendMessage(TextUtils.color(""));
        player.sendMessage(TextUtils.color("<blue>Modules Loaded: <reset>" + plugin.getModuleManager().getModulesAmount()));
        player.sendMessage(TextUtils.color("<blue>Plugin Version: <reset>" + plugin.getDescription().getVersion()));
        player.sendMessage(TextUtils.color("<reset>"));
        player.sendMessage(TextUtils.color("<red>Entity Clear Task: <reset>" + EntityClearModule.getTimeLeft()));
        player.sendMessage(TextUtils.color("<reset>"));
        player.sendMessage(TextUtils.color("<yellow>System Time: <reset>" + formattedTime));


    }

    @SubCommand("timer")
    @Permission({"zcore.admin", "zcore.command.timer"})
    public void timerCommand(CommandSender sender) {
        if (plugin.getModuleManager().isEnabled(ModuleType.ENTITY_CLEAR)) {
            Messages.ENTITY_CLEAR_TIMER_COMMAND.send(sender, "%time%", EntityClearModule.getTimeLeft());
        } else {
            sender.sendMessage(TextUtils.color("<red>This module is not enabled!"));
        }
    }

}
