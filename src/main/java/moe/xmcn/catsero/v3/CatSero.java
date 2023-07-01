package moe.xmcn.catsero.v3;

import moe.xmcn.catsero.v3.util.Logger;
import moe.xmcn.catsero.v3.util.Metrics;
import moe.xmcn.catsero.v3.util.TomlUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class CatSero extends JavaPlugin {

    public static CatSero INSTANCE;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        Logger.info("初始化CatSero");
        Configuration.Companion.saveConfig();
        Configuration.Companion.loadEnv();
        try {
            if (Objects.requireNonNullElse(Configuration.Companion.getPluginConfig().getBoolean("plugin . allow-bstats"), false)) {
                Logger.info("启动bStats统计");
                new Metrics(this, 14767);
            }
        } catch (Exception e) {
            Logger.catchEx(e);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
