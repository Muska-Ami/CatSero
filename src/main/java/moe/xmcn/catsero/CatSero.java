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
package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.bStatsMetrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public class CatSero extends JavaPlugin {

    @Override
    public void onLoad() {
        Logger.logLoader("Start loading CatSero...");

        Logger.logLoader("Saving files...");
        Configuration.saveFiles();
        Logger.logLoader("Saved all files.");

        Logger.logLoader("Checking server information...");
        Envrionment.check();
        List<String> env = Arrays.asList(
                "===== CatSero Runtime Checker =====",
                "Server Version: " + Envrionment.server_version,
                "Bukkit Version: " + Envrionment.bukkit_version,
                "Plugin Version: " + Envrionment.plugin_version,
                "Depends:",
                "- MiraiMC => " + Envrionment.Depends.MiraiMC,
                "Soft-depends:",
                "- PlaceholderAPI => " + Envrionment.Depends.PlaceholderAPI,
                "- TrChat => " + Envrionment.Depends.TrChat,
                "==================================="
        );
        Logger.logLoader(env);
    }

    @Override
    public void onEnable() {
        if (Envrionment.Depends.MiraiMC) {
            if (Configuration.PLUGIN.BSTATS) {
                Logger.logINFO("Start bStats.");
                new bStatsMetrics(this, 14767);
            }
            Logger.logLoader("CatSero loaded.");
            if (Configuration.PLUGIN.CHECK_UPDATE.ENABLE) {
                Logger.logLoader("Start checking update...");
                getServer().getScheduler().scheduleSyncRepeatingTask(this, new Updater(), 0L, Configuration.PLUGIN.CHECK_UPDATE.INTERVAL * 1000L);
                getServer().getPluginManager().registerEvents(new Updater(), this);
            }
        } else {
            Logger.logWARN("[Loader] Warning, not install MiraiMC, CatSero will not run!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Logger.logLoader("Stopping CatSero.");
    }

}