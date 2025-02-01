package cn.ChengZhiYa.MHDFTools.menu;

import cn.ChengZhiYa.MHDFTools.Main;
import cn.ChengZhiYa.MHDFTools.interfaces.Menu;
import cn.ChengZhiYa.MHDFTools.util.config.ConfigUtil;
import cn.ChengZhiYa.MHDFTools.util.scheduler.MHDFScheduler;
import lombok.Getter;
import org.bukkit.entity.Player;
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
