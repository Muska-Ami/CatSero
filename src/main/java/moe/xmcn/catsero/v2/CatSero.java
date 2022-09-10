package moe.xmcn.catsero.v2;

import moe.xmcn.catsero.v2.qqlisteners.ListennerRegister;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

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

        if (Configs.getConfig("config.yml").getBoolean("allow-bstats", true)) {
            Loggers.CustomLevel.logLoader("加载bStats统计");
            new Metrics((JavaPlugin) Configs.plugin, 14767);
            Loggers.CustomLevel.logLoader("成功加载bStats统计");
        }
    }

    @Override
    public void onEnable() {
        Loggers.CustomLevel.logLoader("注册监听器");
        ListennerRegister.register();
        Loggers.CustomLevel.logLoader("加载完毕，启用插件");
    }

}
