package org.itzsave.module.modules;


import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.itzsave.SaveCore;
import org.itzsave.config.ConfigType;
import org.itzsave.module.Module;
import org.itzsave.module.ModuleType;
import org.itzsave.utils.TextUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 Inspiration from https://github.com/ItzSave/DeluxeHub/
 Credit to ItsLewizzz
 */

public class AnnouncerModule extends Module implements Runnable {

    private Map<Integer, List<String>> broadcasts;
    private int broadcastTask = 0;
    private int count = 0;
    private int size = 0;

    public AnnouncerModule(SaveCore plugin) {
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
            broadcastTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), this, 60L, config.getInt("Auto-Announcer.delay") * 20L);

        getPlugin().getLogger().info("[Module] Loaded auto broadcaster");

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
