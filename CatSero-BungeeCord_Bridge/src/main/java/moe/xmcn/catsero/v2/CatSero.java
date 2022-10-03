package moe.xmcn.catsero.v2;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class CatSero extends Plugin {

    @Override
    public void onLoad() {
        Loggers.CustomLevel.logLoader("检查运行环境");
        Env.checkDependsLoad();
        ArrayList<String> env = new ArrayList<>(Arrays.asList(
                "依赖装载情况:",
                "- MiraiMC => " + Env.MiraiMC,
                "- TrChat => " + Env.TrChat,
                "",
                "代理版本: " + getProxy().getVersion()
        ));
        Loggers.CustomLevel.logLoader(env);

        if (Env.MiraiMC) {
            Loggers.CustomLevel.logLoader("保存插件文件");
            if (!getDataFolder().exists()) {
                getDataFolder().mkdir();
            }

            if (!Configs.config.exists()) {
                try (InputStream in = getResourceAsStream("config.yml")) {
                    Files.copy(in, Configs.config.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Loggers.CustomLevel.logLoader("成功保存插件文件");
        }
    }

    @Override
    public void onEnable() {
        if (Env.MiraiMC) {
            Loggers.CustomLevel.logLoader("加载完毕，启用插件");
        }
    }

    @Override
    public void onDisable() {
        Loggers.CustomLevel.logLoader("卸载CatSero插件");
    }

}
