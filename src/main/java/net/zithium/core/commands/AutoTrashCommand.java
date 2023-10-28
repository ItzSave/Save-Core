package net.zithium.core.commands;

import me.mattstudios.mf.annotations.*;
import me.mattstudios.mf.base.CommandBase;
import net.zithium.core.config.Messages;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.modules.AutoTrashModule;
import org.bukkit.Material;
import org.bukkit.entity.Player;

@Command("autotrash")
@Alias("at")
@SuppressWarnings("unused")
public class AutoTrashCommand extends CommandBase {
    private final ZithiumCore plugin = ZithiumCore.getPlugin(ZithiumCore.class);

    private final AutoTrashModule autoTrashModule = new AutoTrashModule(plugin);

    public AutoTrashCommand(ZithiumCore plugin) {
        AutoTrashModule autoTrashModule = new AutoTrashModule(plugin);
    }


    @Default
    @Permission("zcore.command.autotrash")
    public void execute(Player player) {
        if (player == null) return;
        Messages.AUTO_TRASH_HELP.send(player);
    }

    @SubCommand("add")
    public void addItemCommand(Player player, String[] args) {
        if (args[0].equals(null)) {
            Messages.AUTO_TRASH_NULL_ITEM.send(player);
            return;
        }

        Material material = Material.getMaterial(args[1].toUpperCase());

        if (material == null) {
            Messages.AUTO_TRASH_NULL_ITEM.send(player);
            return;
        }

        
        autoTrashModule.addAutoTrashItem(player, material);
        Messages.AUTO_TRASH_ITEM_ADDED.send(player, "%item%", material.name());
    }
}
