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
package moe.xmcn.catsero.events.listeners.PingHost;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Punycode;
import moe.xmcn.catsero.utils.QCommandParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.net.UnknownHostException;
import java.util.Objects;

public class OnQQGroupMessage implements Listener {

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent event) {
        String[] args = QCommandParser.getParser.parse(event.getMessage());
        if (args != null) {
            if (Config.UsesConfig.getBoolean("pinghost.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot && args[0].equals("ping")) {
                if (Config.UsesConfig.getBoolean("pinghost.op-only")) {
                    //OP模式
                    if (event.getSenderID() == Config.QQ_OP) {
                        if (args.length == 2) {
                            //有OP
                            try {
                                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.doing"));
                                String result = Utils.PingHostUtils(args[1]);
                                if (Objects.equals(result, "Error")) {
                                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error"));
                                } else {
                                    long flag = Long.parseLong(result);
                                    String message = Config.getMsgByMsID("qq.pinghost.success")
                                            .replace("%address_original%", args[1])
                                            .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                            .replace("%withdraw%", String.valueOf(flag))
                                            .replace("%lost%", String.valueOf(4 - flag))
                                            .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                                    Config.sendMiraiGroupMessage(message);
                                    //MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                                }
                            } catch (UnknownHostException e) {
                                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.failed"));
                            }
                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error-address"));
                        }
                    } else {
                        //无OP
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                    }
                } else {
                    if (args.length == 2) {
                        //通用模式
                        try {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.doing"));
                            String result = Utils.PingHostUtils(args[1]);
                            if (Objects.equals(result, "Error")) {
                                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error"));
                            } else {
                                long flag = Long.parseLong(result);
                                String message = Config.getMsgByMsID("qq.pinghost.success")
                                        .replace("%address_original%", args[1])
                                        .replace("%address_punycode%", Punycode.encodeURL(args[1]))
                                        .replace("%withdraw%", String.valueOf(flag))
                                        .replace("%lost%", String.valueOf(4 - flag))
                                        .replace("%lost_percent%", String.valueOf((4 - flag) * 100 / 4));
                                Config.sendMiraiGroupMessage(message);
                                //MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                            }
                        } catch (UnknownHostException e) {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.failed"));
                        }
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.pinghost.error-address"));
                    }
                }
            }
        }
    }

}