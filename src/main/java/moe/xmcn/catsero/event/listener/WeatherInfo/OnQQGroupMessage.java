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
package moe.xmcn.catsero.event.listener.WeatherInfo;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.QCommandParser;
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
                    String[] resvi = Utils.getWeather(args[1]);
                    if (resvi.length == 5) {
                        String message = Config.getMsgByMsID("qq.weatherinfo.success")
                                .replace("%type%", resvi[4])
                                .replace("%temperature%", resvi[1])
                                .replace("%wind%", resvi[2])
                                .replace("%wind_direction%", resvi[3])
                                .replace("%date%", resvi[0]);
                        Config.sendMiraiGroupMessage(message);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.error"));
                    }
                } catch (UnsupportedEncodingException uee) {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.error"));
                }
            }
        }.runTaskAsynchronously(Config.plugin);
    }

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String[] args = QCommandParser.getParser.parse(event.getMessage());
        if (args != null) {
            if (Config.UsesConfig.getBoolean("weatherinfo.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot && args[0].equals("weather")) {
                if (Config.UsesConfig.getBoolean("weatherinfo.op-only")) {
                    //仅OP模式
                    if (event.getSenderID() == Config.QQ_OP) {
                        //有OP
                        if (args.length == 2 && event.getGroupID() == Config.Use_Group) {
                            WeatherMain(args);
                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                        }
                    } else {
                        //无OP
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                    }
                } else {
                    //通用模式
                    if (args.length == 2 && event.getGroupID() == Config.Use_Group) {
                        WeatherMain(args);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.weatherinfo.null-city"));
                    }
                }
            }
        }
    }

}
