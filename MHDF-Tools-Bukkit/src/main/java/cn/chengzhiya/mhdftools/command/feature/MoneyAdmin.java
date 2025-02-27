package cn.chengzhiya.mhdftools.command.feature;

import cn.chengzhiya.mhdftools.command.AbstractCommand;
import cn.chengzhiya.mhdftools.entity.data.EconomyData;
import cn.chengzhiya.mhdftools.util.BigDecimalUtil;
import cn.chengzhiya.mhdftools.util.config.ConfigUtil;
import cn.chengzhiya.mhdftools.util.config.LangUtil;
import cn.chengzhiya.mhdftools.util.database.EconomyDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class MoneyAdmin extends AbstractCommand {
    public MoneyAdmin() {
        super(
                "economySettings.enable",
                "经济管理",
                "mhdftools.commands.moneyadmin",
                false,
                ConfigUtil.getConfig().getStringList("economySettings.moneyadminCommands").toArray(new String[0])
        );
    }

    @Override
    public void execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 3) {
            switch (args[0]) {
                case "set", "add", "take" -> {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    BigDecimal change = BigDecimalUtil.toBigDecimal(Double.parseDouble(args[2]));

                    EconomyData economyData = EconomyDataUtil.getEconomyData(player);

                    if (args[0].equals("set")) {
                        economyData.setBigDecimal(change);
                    }

                    if (args[0].equals("add")) {
                        economyData.setBigDecimal(economyData.getBigDecimal().add(change));
                    }

                    if (args[0].equals("take")) {
                        economyData.setBigDecimal(economyData.getBigDecimal().subtract(change));
                    }

                    EconomyDataUtil.updateEconomyData(economyData);

                    sender.sendMessage(LangUtil.i18n("commands.moneyadmin.subCommands." + args[0] + ".message")
                            .replace("{player}", args[0])
                            .replace("{change}", change.toString())
                            .replace("{amount}", economyData.getBigDecimal().toString())
                    );
                    return;
                }
            }
        }

        // 输出帮助信息
        {
            sender.sendMessage(LangUtil.i18n("commands.moneyadmin.subCommands.help.message")
                    .replace("{helpList}", LangUtil.getHelpList("moneyadmin"))
                    .replace("{command}", label)
            );
        }
    }

    @Override
    public List<String> tabCompleter(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1) {
            return new ArrayList<>(LangUtil.getKeys("commands.moneyadmin.subCommands"));
        }
        return null;
    }
}
