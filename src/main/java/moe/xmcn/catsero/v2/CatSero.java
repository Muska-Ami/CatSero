package moe.xmcn.catsero.v2;

import moe.xmcn.catsero.v2.executors.ExecutorRegister;
import moe.xmcn.catsero.v2.listeners.ListenerRegister;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.Metrics;
import moe.xmcn.xmcore.ThisAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CatSero extends JavaPlugin {

    @Override
    public void onLoad() {
        Loggers.CustomLevel.logLoader("检查运行环境");
        Env.checkDependsLoad();
        ArrayList<String> env = new ArrayList<>(Arrays.asList(
                "依赖装载情况:",
                "- MiraiMC" + Env.MiraiMC,
                "- PlaceholderAPI" + Env.PlaceholderAPI,
                "- TrChat" + Env.TrChat,
                "",
                "服务器版本: " + Bukkit.getBukkitVersion()
        ));
        Loggers.CustomLevel.logLoader(env);

        Loggers.CustomLevel.logLoader("保存插件文件");
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
        if (!new File(getDataFolder(), "uses-config.yml").exists()) {
            saveResource("uses-config.yml", false);
        }
        if (!new File(getDataFolder(), "/extra-configs/trchat.yml").exists()) {
            saveResource("extra-configs/trchat.yml", false);
        }
        if (!new File(getDataFolder(), "/mirai-configs/bot.yml").exists()) {
            saveResource("mirai-configs/bot.yml", false);
        }
        if (!new File(getDataFolder(), "/mirai-configs/group.yml").exists()) {
            saveResource("mirai-configs/group.yml", false);
        }
        if (!new File(getDataFolder(), "locale/zh_CN.lang").exists()) {
            saveResource("locale/zh_CN.lang", Configs.getConfig("config.yml").getBoolean("update.auto-zh"));
        }
        ThisAPI.Companion.savePlugin("CatSero");

        if (Configs.getConfig("config.yml").getBoolean("allow-bstats", true)) {
            Loggers.CustomLevel.logLoader("加载bStats统计");
            new Metrics((JavaPlugin) Configs.plugin, 14767);
            Loggers.CustomLevel.logLoader("成功加载bStats统计");
        }
    }

    @Override
    public void onEnable() {
        Loggers.CustomLevel.logLoader("注册监听器");
        ListenerRegister.register();
        ExecutorRegister.register();
        Loggers.CustomLevel.logLoader("加载完毕，启用插件");
    }

}
