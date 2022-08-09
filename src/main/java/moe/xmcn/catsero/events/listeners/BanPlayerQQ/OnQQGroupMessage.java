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
package moe.xmcn.catsero.events.listeners.BanPlayerQQ;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Players;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnQQGroupMessage implements Listener {


    @EventHandler
    public void onMiraiGroupMessageEvent0(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    //有OP权限
                    if (args.length == 3) {
                        //添加到Bukkit内置绑定
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], Config.UsesConfig.getString("qban-player.reason"), null, null);
                        if (Players.getPlayer(args[2]).isOnline()) {
                            //如果玩家在线，那么将其踢出
                            Bukkit.getScheduler().runTask(Config.plugin, () -> Players.getPlayer(args[2]).kickPlayer(Config.UsesConfig.getString("qban-player.reason")));
                        }
                        String message = Config.getMsgByMsID("qq.qban-player.success-ban")
                                .replace("%player%", args[2]);
                        Config.sendMiraiGroupMessage(message);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.invalid-options"));
                    }
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }

    @EventHandler
    public void onMiraiGroupMessageEvent1(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("qban-player.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("unban")) {
                if (event.getSenderID() == Config.QQ_OP) {
                    //有OP权限
                    if (args.length == 3) {
                        //从Bukkit内置封禁移除
                        Bukkit.getBanList(BanList.Type.NAME).pardon(args[2]);
                        String message = Config.getMsgByMsID("qq.qban-player.success-unban")
                                .replace("%player%", args[2]);
                        Config.sendMiraiGroupMessage(message);
                    } else {
                        Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.invalid-options"));
                    }
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"));
                }
            }
        }
    }

}
