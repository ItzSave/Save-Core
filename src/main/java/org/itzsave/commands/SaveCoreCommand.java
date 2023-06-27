package org.itzsave.commands;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.annotations.SubCommand;
import me.mattstudios.mf.base.CommandBase;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.itzsave.SaveCore;
import org.itzsave.config.Messages;
import org.itzsave.utils.TextUtils;

@SuppressWarnings({"unused", "deprecation"})
@Command("savecore")
public class SaveCoreCommand extends CommandBase {

    private final SaveCore plugin;

    public SaveCoreCommand(SaveCore plugin) {
        this.plugin = plugin;
    }

    @Default
    public void execute(Player player, String[] args) {
        //TODO Default help command
    }


    @SubCommand("reload")
    @Permission({"savecore.reload", "savecore.command.reload"})
    public void reloadCommand(CommandSender sender) {
        plugin.onReload();
        long var = System.currentTimeMillis();
        Messages.RELOADED.send(sender, "%ms%", String.valueOf(System.currentTimeMillis() - var));
    }

    @SubCommand("info")
    @Permission({"savecore.admin", "savecore.command.info"})
    public void infoCommand(Player player) {

        player.sendMessage(TextUtils.color("<reset>"));
        player.sendMessage(TextUtils.color("<green>Server Version: <reset>" + plugin.getServer().getVersion()));
        player.sendMessage(TextUtils.color(""));
        player.sendMessage(TextUtils.color("<blue>Modules Loaded: <reset>" + plugin.getModuleManager().getModulesAmount()));
        player.sendMessage(TextUtils.color("<blue>Plugin Version: <reset>" + plugin.getDescription().getVersion()));
        player.sendMessage(TextUtils.color("<reset>"));


    }

}
