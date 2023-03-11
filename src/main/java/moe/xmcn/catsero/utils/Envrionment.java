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
package moe.xmcn.catsero.utils;

import moe.xmcn.catsero.CatSero;
import org.bukkit.Bukkit;

public interface Envrionment {
    String server_version = CatSero.INSTANCE.getServer().getVersion();
    String bukkit_version = CatSero.INSTANCE.getServer().getBukkitVersion();
    String plugin_version = getVersion();

    static void check() {
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null)
            Depends.MiraiMC = true;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            Depends.PlaceholderAPI = true;
        if (Bukkit.getPluginManager().getPlugin("TrChat") != null)
            Depends.TrChat = true;
        if (Bukkit.getPluginManager().getPlugin("floodgate") != null) {
            Depends.Floodgate = true;
        } else if (Bukkit.getPluginManager().getPlugin("Geyser-Spigot") != null) {
            Logger.logINFO("检测到您已安装Geyser-Spigot，但未安装floodgate，互通服优化将自动禁用");
        }
    }

    static String getVersion() {
        return CatSero.INSTANCE.getDescription().getVersion();
    }

    class Depends {
        public static boolean MiraiMC = false;
        public static boolean PlaceholderAPI = false;
        public static boolean TrChat = false;
        public static boolean Floodgate = false;
    }
}
