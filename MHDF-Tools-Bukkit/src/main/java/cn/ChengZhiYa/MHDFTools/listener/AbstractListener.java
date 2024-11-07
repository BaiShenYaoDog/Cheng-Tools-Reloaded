package cn.ChengZhiYa.MHDFTools.listener;

import cn.ChengZhiYa.MHDFTools.util.config.ConfigUtil;
import lombok.Getter;
import org.bukkit.event.Listener;

@Getter
public abstract class AbstractListener implements Listener {

    private final boolean enable;

    public AbstractListener(String enableKey) {
        this.enable = ConfigUtil.getConfig().getBoolean(enableKey);
    }
}
