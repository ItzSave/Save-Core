package net.zithium.core.module.modules;

import dev.rosewood.rosestacker.api.RoseStackerAPI;
import net.zithium.core.ZithiumCore;
import net.zithium.core.config.ConfigType;
import net.zithium.core.config.Messages;
import net.zithium.core.module.Module;
import net.zithium.core.module.ModuleType;
import net.zithium.core.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.logging.Level;


public class EntityClearModule extends Module {

    private static BukkitTask entityClear = null;
    private static long timeLeft;

    private RoseStackerAPI rsAPI;
    private final ZithiumCore plugin;

    public EntityClearModule(ZithiumCore plugin) {
        super(plugin, ModuleType.ENTITY_CLEAR);

        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        if (Bukkit.getPluginManager().isPluginEnabled("RoseStacker")) {
            this.rsAPI = RoseStackerAPI.getInstance();
        }


        entityClear = new BukkitRunnable() {


            @Override
            public void run() {
                if (getConfig(ConfigType.SETTINGS).getInt("entity-clearing.delay") > 0) {
                    if (timeLeft == 0) {
                        Bukkit.getOnlinePlayers().forEach(Messages.ENTITY_CLEAR_FINISHED::send);

                        if (shouldClearItems()) {
                            rsAPI.removeAllItemStacks();
                        }

                        if (shouldClearEntities()) {
                            rsAPI.removeAllEntityStacks();
                        }

                        timeLeft = getConfig(ConfigType.SETTINGS).getInt("entity-clearing.delay");
                        return;
                    }

                    if (timeLeft == 5 || timeLeft == 30 || timeLeft == 60) {
                        // Timer message
                        Bukkit.getOnlinePlayers().forEach(player -> Messages.ENTITY_CLEAR_WARNING.send(player, "%timeleft%", timeLeft));
                    }

                    timeLeft -= 1;

                }
            }
        }.runTaskTimer(getPlugin(), 20L, 20L);
        plugin.getLogger().log(Level.INFO,"[Module] Loaded entity clear module");
    }


    @Override
    public void onDisable() {
        if (entityClear != null) {
            entityClear.cancel();
        }
    }

    public static String getTimeLeft() {
        return TextUtils.formatTime(timeLeft);
    }


    private boolean shouldClearItems() {
        return getConfig(ConfigType.SETTINGS).getBoolean("entity-clearing.clear-items");
    }

    private boolean shouldClearEntities() {
        return getConfig(ConfigType.SETTINGS).getBoolean("entity-clearing.clear-entities");
    }

}
