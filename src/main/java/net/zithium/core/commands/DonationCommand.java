package net.zithium.core.commands;

import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Completion;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.annotations.Permission;
import me.mattstudios.mf.base.CommandBase;
import net.zithium.core.ZithiumCore;
import net.zithium.core.config.Messages;
import net.zithium.core.utils.TextUtils;
import net.zithium.library.utils.Color;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("donation")
public class DonationCommand extends CommandBase {

    private final ZithiumCore plugin;

    public DonationCommand(ZithiumCore plugin){
        this.plugin = plugin;
    }

    @Default
    @Permission({"zcore.admin", "zcore.command.donation"})
    @Completion("#players")
    public void onCommand(CommandSender sender, String[] args) {
        if (args.length == 0) {
            Messages.INVALID_ARGUMENT.send(sender);
            return;
        }

        Player target = plugin.getServer().getPlayerExact(args[0]);

        if (target == null){
            Messages.INVALID_PLAYER.send(sender);
            return;
        }
        Bukkit.getOnlinePlayers().forEach(player -> {
            for (String message : plugin.getConfig().getStringList("Donation-Announcement")) {
                player.sendMessage(Color.stringColor(message.replace("{player}", target.getName())));
                break;
            }
        });

    }
}
