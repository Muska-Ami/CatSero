/*
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
package moe.xmcn.catsero.events.listeners.WeatherInfo;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.UnsupportedEncodingException;

public class OnQQGroupMessage implements Listener {

    private static void WeatherMain(@NotNull String[] args) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.doing"));
                    String[] resvi = Utils.getWeather(args[2]);
                    String message = Config.getMsgByMsID("qq.weatherinfo.success")
                            .replace("%type%", resvi[4])
                            .replace("%temperature%", resvi[1])
                            .replace("%wind%", resvi[2])
                            .replace("%wind_direction%", resvi[3])
                            .replace("%date%", resvi[0]);
                    Config.sendMiraiGroupMessage(message);
                } catch (UnsupportedEncodingException uee) {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.error"));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("weatherinfo.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("weather")) {
                if (Config.UsesConfig.getBoolean("weatherinfo.op-only")) {
                    if (event.getSenderID() == Config.QQ_OP) {
                        if (args.length == 3 && event.getGroupID() == Config.Use_Group) {
                            WeatherMain(args);
                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                        }
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                    }
                } else {
                    if (args.length == 3 && event.getGroupID() == Config.Use_Group) {
                        WeatherMain(args);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                    }
                }
            }
        }
    }

}
