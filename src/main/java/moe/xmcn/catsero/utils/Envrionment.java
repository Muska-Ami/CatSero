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

import com.alibaba.fastjson.JSON;
import moe.xmcn.catsero.Configuration;
import org.bukkit.Bukkit;

import java.io.BufferedReader;
import java.io.FileReader;

public interface Envrionment {
    String server_version = Configuration.plugin.getServer().getVersion();
    String bukkit_version = Configuration.plugin.getServer().getBukkitVersion();
    String plugin_version = getVersion();

    class Depends {
        public static boolean MiraiMC = false;
        public static boolean PlaceholderAPI = false;
        public static boolean TrChat = false;
    }
    static void check() {
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null) Depends.MiraiMC = true;
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) Depends.PlaceholderAPI = true;
        if (Bukkit.getPluginManager().getPlugin("TrChat") != null) Depends.TrChat = true;
    }

    static String getVersion() {
        try {
            BufferedReader in = new BufferedReader(new FileReader(Configuration.CFI.version_file));
            String body;
            StringBuilder data = new StringBuilder();
            while ((body = in.readLine()) != null) {
                data.append(body);
            }
            return JSON.parseObject(data.toString()).getString("version");
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
    }
}
