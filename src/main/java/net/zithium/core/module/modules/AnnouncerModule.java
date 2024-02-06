package net.zithium.core.module.modules;


import net.zithium.core.ZithiumCore;
import net.zithium.core.module.Module;
import net.zithium.core.utils.TextUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import net.zithium.core.config.ConfigType;
import net.zithium.core.module.ModuleType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 Inspiration from https://github.com/ItzSave/DeluxeHub/
 */

public class AnnouncerModule extends Module implements Runnable {

    private Map<Integer, List<String>> broadcasts;
    private int broadcastTask = 0;
    private int count = 0;
    private int size = 0;

    public AnnouncerModule(ZithiumCore plugin) {
        super(plugin, ModuleType.ANNOUNCEMENTS);
    }

    public void onEnable() {
        FileConfiguration config = getConfig(ConfigType.SETTINGS);

        broadcasts = new HashMap<>();
        int count = 0;
        for (String key : config.getConfigurationSection("Auto-Announcer.announcements").getKeys(false)) {
            broadcasts.put(count, config.getStringList("Auto-Announcer.announcements." + key));
            count++;
        }

        size = broadcasts.size();
        if (size > 0)
            // Convert minutes to ticks (20 ticks per second)
            broadcastTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), this,
                    60L * config.getInt("Auto-Announcer.delay") * 20L,  // Delay in ticks
                    60L * config.getInt("Auto-Announcer.delay") * 20L); // Repeat interval in ticks

        getPlugin().getLogger().info("[Module] Loaded auto announcer module");
    }


    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(broadcastTask);
        getPlugin().getLogger().info("[Module] Disabled auto broadcaster");
    }

    @Override
    public void run() {
        if (count == size) count = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            broadcasts.get(count).forEach(message -> player.sendMessage(TextUtils.color(message)));
        }
        count++;
    }

}
