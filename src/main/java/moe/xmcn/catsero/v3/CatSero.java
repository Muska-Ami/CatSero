package moe.xmcn.catsero.v3;

import moe.xmcn.catsero.v3.util.Logger;
import moe.xmcn.catsero.v3.util.Metrics;
import moe.xmcn.catsero.v3.util.TomlUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class CatSero extends JavaPlugin {

    public static CatSero INSTANCE;

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        Configuration.Companion.saveConfig();

        if (TomlUtil.Companion.getTomlResult(getResource("config.toml")).getBoolean("plugin . allow-bstats")) {
            Logger.info("启动bStats统计");
            new Metrics(this, 14767);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
