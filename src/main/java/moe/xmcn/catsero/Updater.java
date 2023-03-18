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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.HttpClient;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.IOException;
import java.util.Arrays;

public class Updater implements Runnable, Listener {

    private static final String now_version = Envrionment.plugin_version;
    private static String latest_version = Envrionment.plugin_version;
    private static String[] beta_version;

    @Override
    public void run() {
        HttpClient hc = new HttpClient();
        String data = null;
        try {
            data = hc.getRequest(Configuration.getPlugin().getString("check-update.api-url"));
        } catch (IOException ignored) {
        }
        JSONObject object = JSON.parseObject(data);

        if (object != null) {
            latest_version = object.getJSONObject("latest").getString("tag");
            beta_version = Arrays.asList(
                    object.getJSONObject("beta").getString("jar_zip"),
                    object.getJSONObject("beta").getString("full_zip")
            ).toArray(new String[0]);
            if (Configuration.getPlugin().getString("check-update.mode").equalsIgnoreCase("latest")) {
                Logger.logINFO("CatSero latest version: " + latest_version);
                if (!now_version.equals(latest_version))
                    Logger.logINFO("Your version is not latest, your version: " + now_version);
            } else if (Configuration.getPlugin().getString("check-update.mode").equalsIgnoreCase("beta"))
                Logger.logINFO(
                        "CatSero actions build:" +
                                " \nJar Artifact - " + beta_version[0] +
                                " \nFull Artifact - " + beta_version[1]
                );
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOp()) {
            if (Configuration.getPlugin().getString("check-update.mode").equalsIgnoreCase("latest") && latest_version != null && !now_version.equals(latest_version))
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&bCatSero has new version: &e" +
                                        latest_version +
                                        "&b, your version: &e" + now_version
                        )
                );
            else if (Configuration.getPlugin().getString("check-update.mode").equalsIgnoreCase("beta") && beta_version != null)
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&bCatSero actions build:" +
                                        "\n Jar Artifact - &e" + beta_version[0] +
                                        "\n &bFull Artifact - &e" + beta_version[1] +
                                        "\n&byour version: &e" + now_version
                        )
                );
        }
    }

}
