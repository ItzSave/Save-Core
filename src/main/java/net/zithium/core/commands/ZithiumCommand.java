package net.zithium.core.commands;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.zithium.core.config.Messages;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.ModuleType;
import net.zithium.core.module.modules.EntityClearModule;
import net.zithium.library.utils.Color;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import net.zithium.core.utils.TextUtils;

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
        plugin.onReload();
        long var = System.currentTimeMillis();
        Messages.RELOADED.send(sender, "%ms%", String.valueOf(System.currentTimeMillis() - var));
    }

    @SubCommand("info")
    @Permission({"zcore.admin", "zcore.command.info"})
    public void infoCommand(Player player) {

        player.sendMessage(Color.stringColor("<reset>"));
        player.sendMessage(Color.stringColor("<green>Server Version: <reset>" + plugin.getServer().getVersion()));
        player.sendMessage(Color.stringColor(""));
        player.sendMessage(Color.stringColor("<blue>Modules Loaded: <reset>" + plugin.getModuleManager().getModulesAmount()));
        player.sendMessage(Color.stringColor("<blue>Plugin Version: <reset>" + plugin.getDescription().getVersion()));
        player.sendMessage(Color.stringColor("<reset>"));
        player.sendMessage(Color.stringColor("<red>Entity Clear Task: <reset>" + EntityClearModule.getTimeLeft()));


    }

    @SubCommand("timer")
    @Permission({"zcore.admin", "zcore.command.timer"})
    public void timerCommand(CommandSender sender){
        if (plugin.getModuleManager().isEnabled(ModuleType.ENTITY_CLEAR)) {
            Messages.ENTITY_CLEAR_TIMER_COMMAND.send(sender, "%time%", EntityClearModule.getTimeLeft());
        } else {
            sender.sendMessage("<red>This module is not enabled!");
        }
    }

}
