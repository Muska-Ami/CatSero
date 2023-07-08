package moe.xmcn.catsero.v3;

import moe.xmcn.catsero.v3.core.CoreRegister;
import moe.xmcn.catsero.v3.util.Logger;
import moe.xmcn.catsero.v3.util.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.tomlj.TomlParseResult;

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
        Logger.info("初始化 CatSero");
        Configuration.Companion.saveConfig();
        Configuration.Companion.loadEnv();
        TomlParseResult config = Configuration.Companion.getPluginConfig();

        // 检测是否安装必要依赖
        if (Configuration.Depend.Companion.getMiraiMC()) {

            try {
                // bStats
                if (Objects.requireNonNullElse(config.getBoolean("plugin . allow-bstats"), false)) {
                    Logger.info("启动 bStats 统计");
                    new Metrics(this, 14767);
                }

                // 核心事件注册
                CoreRegister.Companion.registerListener();
                CoreRegister.Companion.registerTimer();
            } catch (Exception e) {
                Logger.catchEx(e);
            }

            Logger.info("CatSero 加载完毕");

        } else {
            if (Objects.requireNonNullElse(config.getBoolean("plugin . birdge-mode"), false)) {
                Logger.info("CatSero 桥接模式加载完毕");
            } else {
                Logger.error("未安装 MiraiMC ，且桥接模式处于禁用状态， CatSero 将自动禁用");
                getPluginLoader().disablePlugin(this);
            }
        }

    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
