package cn.ChengZhiYa.ChengToolsReloaded.Listener;

import cn.ChengZhiYa.ChengToolsReloaded.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public class EntityPickupItem implements Listener {
    @EventHandler
    public void On_Event(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            if (main.main.getConfig().getBoolean("LoginSystemSettings.Enable")) {
                if (!getLogin(((Player) event.getEntity()))) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
