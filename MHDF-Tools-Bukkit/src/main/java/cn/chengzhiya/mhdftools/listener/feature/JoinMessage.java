package cn.chengzhiya.mhdftools.listener.feature;

import cn.chengzhiya.mhdftools.Main;
import cn.chengzhiya.mhdftools.listener.AbstractListener;
import cn.chengzhiya.mhdftools.util.config.ConfigUtil;
import cn.chengzhiya.mhdftools.util.message.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public final class JoinMessage extends AbstractListener {
    public JoinMessage() {
        super(
                "joinMessageSettings.enable"
        );
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String message = ConfigUtil.getConfig().getString("joinMessageSettings." + getGroup(player) + ".message");
        if (message == null) {
            event.setJoinMessage(null);
            return;
        }

        event.setJoinMessage(ColorUtil.color(Main.instance.getPluginHookManager().getPlaceholderAPIHook().placeholder(player, message))
                .replace("{player}", player.getName())
        );
    }

    /**
     * 获取指定玩家所在的组
     *
     * @param player 玩家实例
     * @return 组名称
     */
    private String getGroup(Player player) {
        List<String> groupList = player.getEffectivePermissions().stream()
                .map(PermissionAttachmentInfo::getPermission)
                .filter(permission -> permission.startsWith("mhdftools.group.joinmessage."))
                .map(permission -> permission.replace("mhdftools.group.joinmessage.", ""))
                .toList();

        int maxWeight = 0;
        String maxWeightGroup = "default";

        for (String group : groupList) {
            int weight = ConfigUtil.getConfig().getInt("joinMessageSettings." + group + ".weight");

            if (weight > maxWeight) {
                maxWeight = weight;
                maxWeightGroup = group;
            }
        }

        return maxWeightGroup;
    }
}
