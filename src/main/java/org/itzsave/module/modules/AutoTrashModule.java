package org.itzsave.module.modules;

import org.itzsave.SaveCore;
import org.itzsave.module.Module;
import org.itzsave.module.ModuleType;

@SuppressWarnings("unused")
public class AutoTrashModule extends Module {

    public AutoTrashModule(SaveCore plugin){
        super(plugin, ModuleType.AUTO_TRASH);
    }
    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

        // Handle shutdown.
    }
}
