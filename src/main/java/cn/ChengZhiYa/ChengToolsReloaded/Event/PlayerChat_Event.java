package cn.ChengZhiYa.ChengToolsReloaded.Event;

import cn.ChengZhiYa.ChengToolsReloaded.HashMap.IntHashMap;
import cn.ChengZhiYa.ChengToolsReloaded.main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Objects;

import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.ChatColor;
import static cn.ChengZhiYa.ChengToolsReloaded.Ultis.multi.OpSendMessage;

public class PlayerChat_Event implements Listener {
    @EventHandler
    public void On_Event(AsyncPlayerChatEvent event) {
        String Message = event.getMessage();
        Player player = event.getPlayer();
        if (IntHashMap.Get(player.getName() + "_ChatDelayTime") != null) {
            player.sendMessage(ChatColor("&e聊天冷却中!请等待" + IntHashMap.Get(player.getName() + "_ChatDelayTime" + "秒后重试")));
            event.setCancelled(true);
            return;
        }
        String Format = main.main.getConfig().getString("ChatSettings.ChatFormat");
        Format = Objects.requireNonNull(Format).replaceAll("%Player%", player.getName());
        if (main.main.getConfig().getBoolean("ChatSettings.ChatFormatEnable")) {
            if (player.hasPermission("ChengTools.Chat.Color")) {
                event.setFormat(ChatColor(Format + Message));
            } else {
                event.setFormat(ChatColor(Format) + Message);
            }
        }
        if (main.main.getConfig().getBoolean("ChatSettings.ChatBlackWorkEnable")) {
            if (!player.hasPermission("ChengTools.Chat.BlackWorkBypass")) {
                for (String BlackWork : main.main.getConfig().getStringList("ChatSettings.ChatBlackWork")) {
                    if (Message.contains(BlackWork)) {
                        event.setCancelled(true);
                        player.sendMessage(ChatColor("&c&l你发的内容中有违禁词!"));
                        OpSendMessage("&e&l玩家" + player.getName() + "发送了违禁词:" + BlackWork);
                        return;
                    }
                }
            }
        }
        if (main.main.getConfig().getBoolean("ChatSettings.ChatDelayEnable")) {
            if (!player.hasPermission("ChengTools.Chat.DelayBypass")) {
                IntHashMap.Set(player.getName() + "_ChatDelayTime", main.main.getConfig().getInt("ChatSettings.ChatDelay"));
            }
        }
    }
}
