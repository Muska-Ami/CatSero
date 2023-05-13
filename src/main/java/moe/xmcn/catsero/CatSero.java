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

import moe.xmcn.catsero.events.bridge.EventCaller;
import moe.xmcn.catsero.executors.Register;
import moe.xmcn.catsero.uses.timers.infoPlaceholder.OnServerDisable;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.Filter;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.WhiteListDatabase;
import moe.xmcn.catsero.utils.timers.NotNPP;
import moe.xmcn.catsero.utils.timers.TPSCalculator;
import moe.xmcn.catsero.utils.timers.bStatsMetrics;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CatSero extends JavaPlugin {

    public static CatSero INSTANCE;

    public static boolean findProcess(String processName) {
        BufferedReader bufferedReader = null;
        try {
            Process proc = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + processName + "\"");
            bufferedReader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(processName)) {
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

    @Override
    public void onLoad() {

        INSTANCE = this;

        Logger.logLoader("Start loading CatSero...");

        Logger.logLoader("Checking server information...");
        Envrionment.check();

        Logger.logLoader("Saving files...");
        Configuration.saveFiles();
        Logger.logLoader("Saved all files.");

        Logger.logLoader("Loading configurations...");
        try {
            Configuration.loadConfiguration();
        } catch (IOException e) {
            Logger.logCatch(e);
            getServer().getPluginManager().disablePlugin(this);
        }
        Logger.logLoader("Loaded.");

        /*
        if (
                Configuration.USES_CONFIG.QWHITELIST.ENABLE
                        && !new File(getDataFolder(), "extra-configs/whitelist.yml").exists()
        ) {
            Logger.logLoader("Creating whitelist data...");
            saveResource("extra-configs/whitelist.yml", false);
            Logger.logLoader("Created.");
        }

         */
        if (Configuration.getUses().getBoolean(Configuration.buildYaID(
                Configuration.USESID.QWHITELIST,
                new ArrayList<>(Collections.singletonList(
                        "enable"
                ))
        ))) {
            Logger.logLoader("Init whitelist database...");
            WhiteListDatabase.initDatabase();
            Logger.logLoader("Init success.");
        }

        // 把信息输出一遍
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
                "- floodgate => " + Envrionment.Depends.Floodgate,
                "==================================="
        );
        Logger.logLoader(env);
    }

    @Override
    public void onEnable() {

        if (Envrionment.Depends.MiraiMC) {
            Logger.logLoader("Registering Executors...");
            Register.register();
            Logger.logLoader("Registered.");

            Logger.logLoader("Registering Listeners...");
            moe.xmcn.catsero.uses.Register.register();
            Logger.logLoader("Registered.");

            Logger.logLoader("Registering QQCommands...");
            getServer().getPluginManager().registerEvents(new EventCaller(), this);
            Configuration.registerQQCommand();
            Logger.logLoader("Registered.");

            if (Configuration.getPlugin().getBoolean("bstats")) {
                Logger.logINFO("Start bStats.");
                new bStatsMetrics(this, 14767);
            }

            Logger.logLoader("Start TPSCalculator.");
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new TPSCalculator(), 100L, 1L);

            Logger.logLoader("Start check Notepad++.");
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new NotNPP(), 10L, 200L);

            Logger.logLoader("CatSero loaded.");

            if (Configuration.getPlugin().getBoolean("check-update.enable")) {
                Logger.logLoader("Start checking update.");
                getServer().getScheduler().runTaskTimerAsynchronously(this, new Updater(), 0L, Configuration.getPlugin().getLong("check-update.interval") * 20L);
                getServer().getPluginManager().registerEvents(new Updater(), this);
            }

            Filter.startUpdate();

        } else {
            Logger.logWARN("[Loader] Warning, not install MiraiMC, CatSero will not run!");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        Logger.logLoader("Disabling CatSero.");
        if (Configuration.getUses().getBoolean(Configuration.buildYaID(
                Configuration.USESID.INFO_PLACEHOLDER,
                new ArrayList<>(Collections.singletonList("enable"))
        ))) new OnServerDisable().run();
        Logger.logINFO("If you love CatSero, don't forget to give it a Star on GitHub!");
    }
}
