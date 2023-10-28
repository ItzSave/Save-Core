package net.zithium.core.module.modules;

import me.mattstudios.mf.base.CommandManager;
import net.zithium.core.ZithiumCore;
import net.zithium.core.module.Module;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import net.zithium.core.commands.AutoTrashCommand;
import net.zithium.core.module.ModuleType;
import net.zithium.core.utils.TextUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class AutoTrashModule extends Module implements Listener {


    public HashMap<UUID, List<Material>> playerItems;

    public AutoTrashModule(ZithiumCore plugin) {
        super(plugin, ModuleType.AUTO_TRASH);
    }

    @Override
    public void onEnable() {
        getPlugin().getComponentLogger().info(TextUtils.color("<green>[Module] Loaded auto trash module."));

        CommandManager cm = new CommandManager(getPlugin());
        getPlugin().getComponentLogger().info(TextUtils.color("<green>[Module] Loaded auto trash command."));
        cm.register(new AutoTrashCommand(getPlugin()));
    }

    @Override
    public void onDisable() {
        // Handle shutdown.
    }

    @EventHandler
    public void itemPickup(@NotNull EntityPickupItemEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player) event.getEntity();
            Material material = event.getItem().getItemStack().getType();

            if (getTrashItems(player) != null && getTrashItems(player).contains(material)) {
                event.getItem().remove();
                event.setCancelled(true);
            }
        }

    }

    @EventHandler
    public void onInventoryPickup(@NotNull InventoryPickupItemEvent event) {
        for (HumanEntity entity : event.getInventory().getViewers()) {
            Material material = event.getItem().getItemStack().getType();
            if (entity instanceof Player player && getTrashItems(player).contains(material)){
                event.getItem().remove();
            }
        }
    }

    public void addAutoTrashItem(Player p, Material item) {
        if (!playerItems.containsKey(p.getUniqueId())) {
            List<Material> temp = new ArrayList<>();
            temp.add(item);
            playerItems.put(p.getUniqueId(), temp);
        } else {
            List<Material> temp = new ArrayList<>(playerItems.get(p.getUniqueId()));
            temp.add(item);
            playerItems.remove(p.getUniqueId());
            playerItems.put(p.getUniqueId(), temp);
        }
    }

    public void remAutoTrashItem(Player p, Material item) {
        if (playerItems.containsKey(p.getUniqueId()) && playerItems.get(p.getUniqueId()).contains(item)) {
            List<Material> temp = new ArrayList<>(playerItems.get(p.getUniqueId()));
            temp.remove(item);
            playerItems.remove(p.getUniqueId());
            playerItems.put(p.getUniqueId(), temp);
        }
    }

    public List<String> getTrashItems(Player p) {
        if (!playerItems.containsKey(p.getUniqueId()))
            return null;
        List<String> ret = new ArrayList<>();
        for (Material item : playerItems.get(p.getUniqueId()))
            ret.add(item.name());
        return ret;
    }

    public List<Material> getTrashItemsMat(Player p) {
        if (!playerItems.containsKey(p.getUniqueId()))
            return null;
        return new ArrayList<>(playerItems.get(p.getUniqueId()));
    }

    public void resetTrashItems(Player p) {
        //noinspection RedundantCollectionOperation
        if (playerItems.containsKey(p.getUniqueId()))
            playerItems.remove(p.getUniqueId());
    }
}
