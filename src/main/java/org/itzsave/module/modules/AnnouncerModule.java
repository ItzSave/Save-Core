package org.itzsave.modules.impl;

import org.bukkit.event.Listener;
import org.itzsave.SaveCore;
import org.itzsave.modules.Module;
import org.itzsave.modules.ModuleType;

public class AnnouncerModule extends Module implements Listener {
    public AnnouncerModule(SaveCore plugin) {
        super(plugin, ModuleType.ANNOUNCEMENTS);
    }

    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
