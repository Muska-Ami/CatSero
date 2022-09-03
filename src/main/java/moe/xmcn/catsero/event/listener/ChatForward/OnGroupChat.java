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
package moe.xmcn.catsero.event.listener.ChatForward;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.Players;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupChat implements Listener {

    @EventHandler
    public void onGroupChat(MiraiGroupMessageEvent event) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled") && event.getGroupID() == Config.Use_Group && event.getBotID() == Config.Use_Bot) {
            long groupid = event.getGroupID();
            String groupname = event.getGroupName();
            long senderid = event.getSenderID();
            String sendername = event.getSenderNameCard();
            if (sendername.equals("")) {
                sendername = event.getSenderName();
            }
            String emsg = event.getMessage();

            if (!emsg.startsWith("!catsero") && !emsg.startsWith("/catsero") && !emsg.startsWith("!" + Config.Config.getString("format-list.custom-command-head.prefix")) && !emsg.startsWith("/" + Config.Config.getString("format-list.custom-command-head.prefix"))) {
                String message = Config.UsesConfig.getString("forward-chat.format.to-mc");
                message = tryUseBind(message, groupid, groupname, senderid, sendername, emsg);
                if (Config.UsesConfig.getBoolean("forward-chat.clean-colorcode")) {
                    message = Utils.removeColorCode(message);
                }
                if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled")) {
                    //启用了Prefix
                    if (message.startsWith(Config.UsesConfig.getString("forward-chat.prefix.format.to-qq"))) {
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
                    }
                } else {
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
                }
            }
        }
    }

    private String tryUseBind(String message, long groupid, String groupname, long senderid, String sendername, String emsg) {
        String mes = message.replace("%groupcode%", String.valueOf(groupid))
                .replace("%groupname%", groupname)
                .replace("%sendercode%", String.valueOf(senderid));
        if (Config.UsesConfig.getBoolean("forward-chat.use-bind")) {
            //使用MiraiMC内置绑定模式
            return mes.replace("%sendername%", Players.getPlayer(MiraiMC.getBind(senderid)).getName())
                    .replace("%message%", emsg);
        } else {
            //通用模式
            return mes.replace("%sendername%", sendername)
                    .replace("%message%", emsg);
        }
    }

}
