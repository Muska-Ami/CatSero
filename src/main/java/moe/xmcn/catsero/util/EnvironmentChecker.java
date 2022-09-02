/*
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright © 2022 XiaMoHuaHuo_CN.
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
package moe.xmcn.catsero.util;

import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

public class EnvironmentChecker {

    public static boolean MiraiMC = false;
    public static boolean PlaceholderAPI = false;
    public static boolean TrChat = false;

    public static void checkEnvironment() {
        checkLib();
    }
    private static void checkLib() {
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null) {
            MiraiMC = true;
        }
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            PlaceholderAPI = true;
        }
        if (Bukkit.getPluginManager().getPlugin("TrChat") != null) {
            TrChat = true;
        }
    }

    public static void listEnvironmentInstallation() {
        Set<String> list = new HashSet<>(Arrays.asList(
                "=== CatSero 运行时环境检查 ===",
                "MiraiMC => " + MiraiMC,
                "PlaceholderAPI => " + PlaceholderAPI,
                "TrChat => " + TrChat,
                "<--------->",
                "服务器版本: " + Bukkit.getBukkitVersion(),
                "启用插件: " + shouldEnablePluginText(),
                "<--------->",
                "插件版本: " + Config.PluginInfo.getString("version"),
                "插件作者: " + Config.PluginInfo.getString("author"),
                "============================="
        ));
        list.forEach(it -> Config.plugin.getLogger().log(Level.INFO, it));
    }

    private static String shouldEnablePluginText() {
        if (MiraiMC) {
            return "是";
        }
        return "否";
    }

    public static boolean shouldEnablePlugin() {
        return MiraiMC;
    }
}
