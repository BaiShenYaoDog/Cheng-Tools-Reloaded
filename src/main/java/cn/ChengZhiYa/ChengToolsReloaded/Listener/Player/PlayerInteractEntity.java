package cn.ChengZhiYa.ChengToolsReloaded.Listener.Player;

import cn.ChengZhiYa.ChengToolsReloaded.ChengToolsReloaded;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.*;

public final class PlayerInteractEntity implements Listener {
    @EventHandler
    public void On_Event(PlayerInteractEntityEvent event) {
        if (ChengToolsReloaded.instance.getConfig().getBoolean("LoginSystemSettings.Enable")) {
            if (!getLogin(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }
}
