package cn.ChengZhiYa.MHDFTools.command.feature;

import cn.ChengZhiYa.MHDFTools.command.AbstractCommand;
import cn.ChengZhiYa.MHDFTools.util.config.ConfigUtil;
import cn.ChengZhiYa.MHDFTools.util.config.LangUtil;
import cn.ChengZhiYa.MHDFTools.util.feature.NickUtil;
import cn.ChengZhiYa.MHDFTools.util.message.ColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class Nick extends AbstractCommand {
    public Nick() {
        super(
                "nickSettings.enable",
                "匿名",
                "mhdftools.commands.nick",
                true,
                ConfigUtil.getConfig().getStringList("customMenuSettings.commands").toArray(new String[0])
        );
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        Player player = null;

        // 修改玩家自己的匿名名称
        if (args.length == 1 && sender instanceof Player) {
            player = (Player) sender;
        }

        // 修改其他玩家的匿名名称
        if (args.length >= 2) {
            if (Bukkit.getPlayer(args[1]) == null) {
                sender.sendMessage(LangUtil.i18n("playerOffline"));
                return;
            }
            player = Bukkit.getPlayer(args[1]);

            if (!sender.hasPermission("mhdftools.commands.nick.give")) {
                sender.sendMessage(LangUtil.i18n("noPermission"));
                return;
            }
        }

        // 输出帮助信息
        if (player == null) {
            sender.sendMessage(LangUtil.i18n("usageError")
                    .replace("{usage}", LangUtil.i18n("commands.nick.usage"))
                    .replace("{command}", label)
            );
            return;
        }

        NickUtil.setNickName(player, ColorUtil.color(args[1]));

        sender.sendMessage(LangUtil.i18n("commands.nick.message")
                .replace("{player}", player.getName())
                .replace("{name}", ColorUtil.color(args[1]))
        );
    }
}
