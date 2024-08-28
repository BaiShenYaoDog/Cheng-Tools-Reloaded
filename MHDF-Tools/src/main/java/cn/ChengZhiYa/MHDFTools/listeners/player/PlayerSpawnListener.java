package cn.ChengZhiYa.MHDFTools.listeners.player;

import cn.ChengZhiYa.MHDFTools.PluginLoader;
import cn.ChengZhiYa.MHDFTools.entity.SuperLocation;
import cn.ChengZhiYa.MHDFTools.utils.BungeeCordUtil;
import cn.ChengZhiYa.MHDFTools.utils.command.SpawnUtil;
import com.github.Anon8281.universalScheduler.foliaScheduler.FoliaScheduler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerSpawnListener implements Listener {
    JavaPlugin plugin = PluginLoader.INSTANCE.getPlugin();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!isEnabled()) {
            return;
        }

        Player player = event.getPlayer();
        SuperLocation spawnLocation = SpawnUtil.getSpawnLocation();

        new FoliaScheduler(plugin).runTaskLater(player.getLocation(), () -> {
            BungeeCordUtil.tpPlayerTo(player.getName(), SpawnUtil.getServerName(), spawnLocation);
        }, 5L);
    }

    private boolean isEnabled() {
        return PluginLoader.INSTANCE.getPlugin().getConfig().getBoolean("SpawnSettings.Enable") && PluginLoader.INSTANCE.getPlugin().getConfig().getBoolean("SpawnSettings.JoinTeleport");
    }
}