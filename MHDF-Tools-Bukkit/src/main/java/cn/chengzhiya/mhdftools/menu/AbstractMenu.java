package cn.chengzhiya.mhdftools.menu;

import cn.chengzhiya.mhdftools.Main;
import cn.chengzhiya.mhdftools.interfaces.Menu;
import cn.chengzhiya.mhdftools.util.config.ConfigUtil;
import cn.chengzhiya.mhdftools.util.scheduler.MHDFScheduler;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

@Getter
public abstract class AbstractMenu implements InventoryHolder, Menu {
    private final boolean enable;
    private final Player player;

    public AbstractMenu(String enableKey, Player player) {
        if (enableKey != null && !enableKey.isEmpty()) {
            this.enable = ConfigUtil.getConfig().getBoolean(enableKey);
        } else {
            this.enable = true;
        }
        this.player = player;
    }

    public void onClick(InventoryClickEvent event) {
        if (!isEnable()) {
            return;
        }

        click(event);
    }

    public void onClose(InventoryCloseEvent event) {
        if (!isEnable()) {
            return;
        }

        close(event);
    }

    public void openMenu() {
        if (!isEnable()) {
            return;
        }

        MHDFScheduler.getAsyncScheduler().runNow(Main.instance, (task) -> {
            Inventory menu = getInventory();

            MHDFScheduler.getGlobalRegionScheduler().run(Main.instance, (task1) -> player.openInventory(menu));
        });
    }
}
