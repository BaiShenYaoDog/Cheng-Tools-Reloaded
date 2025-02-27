package cn.chengzhiya.mhdftools.command.feature;

import cn.chengzhiya.mhdftools.command.AbstractCommand;
import cn.chengzhiya.mhdftools.util.config.ConfigUtil;
import cn.chengzhiya.mhdftools.util.config.LangUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public final class Bed extends AbstractCommand {
    public Bed() {
        super(
                "bedSettings.enable",
                "回到床的位置",
                "mhdftools.commands.bed",
                true,
                ConfigUtil.getConfig().getStringList("bedSettings.commands").toArray(new String[0])
        );
    }

    @Override
    public void execute(@NotNull Player sender, @NotNull String label, @NotNull String[] args) {
        // 输出帮助信息
        if (args.length != 0) {
            sender.sendMessage(LangUtil.i18n("usageError")
                    .replace("{usage}", LangUtil.i18n("commands.bed.usage"))
                    .replace("{command}", label)
            );
            return;
        }

        Location location = sender.getRespawnLocation();
        if (location == null) {
            sender.sendMessage(LangUtil.i18n("commands.bed.noSleep"));
            return;
        }

        sender.teleport(location);
        sender.sendMessage(LangUtil.i18n("commands.bed.message"));
    }
}
