package net.zithium.core.commands;

import me.mattstudios.mf.annotations.Alias;
import me.mattstudios.mf.annotations.Command;
import me.mattstudios.mf.annotations.Default;
import me.mattstudios.mf.base.CommandBase;
import net.zithium.core.config.Messages;
import org.bukkit.entity.Player;

@Command("rule")
@Alias("rules")
public class RulesCommand extends CommandBase {


    @Default
    public void ruleCommand(Player sender) {
        Messages.RULES.send(sender);

    }
}
