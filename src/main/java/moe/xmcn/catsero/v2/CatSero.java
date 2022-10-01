/*
 * CatSero v2
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero.v2;

import moe.xmcn.catsero.v2.executors.ExecutorRegister;
import moe.xmcn.catsero.v2.listeners.ListenerRegister;
import moe.xmcn.catsero.v2.utils.*;
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
                "- MiraiMC => " + Env.MiraiMC,
                "- PlaceholderAPI => " + Env.PlaceholderAPI,
                "- TrChat => " + Env.TrChat,
                "",
                "服务器版本: " + Bukkit.getBukkitVersion()
        ));
        Loggers.CustomLevel.logLoader(env);

        if (Env.MiraiMC) {
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
                saveResource("locale/zh_CN.lang", false);
            }
            if (!new File(getDataFolder(), "/mirai-configs/qq-op.yml").exists()) {
                saveResource("mirai-configs/qq-op.yml", false);
            }
            ThisAPI.Companion.savePlugin("CatSero");
            Loggers.CustomLevel.logLoader("成功保存插件文件");

            if (Configs.JPConfig.config.getBoolean("allow-bstats", true)) {
                Loggers.CustomLevel.logLoader("加载bStats统计");
                new Metrics((JavaPlugin) Configs.plugin, 14767);
                Loggers.CustomLevel.logLoader("成功加载bStats统计");
            }
        } else Loggers.CustomLevel.logLoader("未检测到MiraiMC");
    }

    @Override
    public void onEnable() {
        if (Env.MiraiMC) {
            Loggers.CustomLevel.logLoader("注册监听器");
            ListenerRegister.register();
            ExecutorRegister.register();
            Loggers.CustomLevel.logLoader("成功注册监听器");

            Loggers.CustomLevel.logLoader("启动TPS计算器");
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Configs.plugin, new ServerTPS(), 100L, 1L);
            Loggers.CustomLevel.logLoader("成功启动TPS计算器");

            Loggers.CustomLevel.logLoader("加载完毕，启用插件");
        } else getServer().getPluginManager().disablePlugin(this);
    }

    @Override
    public void onDisable() {
        Loggers.CustomLevel.logLoader("卸载CatSero插件，感谢您使用CatSero");
    }
}
