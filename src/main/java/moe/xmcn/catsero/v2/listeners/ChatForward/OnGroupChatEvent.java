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
package moe.xmcn.catsero.v2.listeners.ChatForward;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.listeners.JoinQuitForward.Utils;
import moe.xmcn.catsero.v2.utils.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupChatEvent implements Listener {

    private boolean Cancel = false;
    private String message;

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        this.message = e.getMessage();
        String sender_name = e.getSenderNameCard();
        if (sender_name.equals("")) {
            sender_name = e.getSenderName();
        }
        long sender_code = e.getSenderID();

        String format = Configs.getConfig("uses-config.yml").getString("chat-forward.format.to-mc");

        if (Configs.getConfig("uses-config.yml").getBoolean("chat-forward.clean-colorcode")) {
            message = Utils.cleanColorCode(message);
        }
        if (Configs.getConfig("uses-config.yml").getBoolean("chat-forward.filter.enabled")) {
            Configs.getConfig("uses-config.yml").getStringList("chat-forward.filter.list").forEach(it -> {
                if (Configs.getConfig("uses-config.yml").getBoolean("chat-forward.filter.replace-only")) {
                    message = message.replace(it, "***");
                } else if (!message.contains(it)) {
                    this.Cancel = true;
                }
            });
        }

        if (Configs.getConfig("uses-config.yml").getBoolean("chat-forward.prefix.enabled")) {
            if (message.startsWith(Configs.getConfig("uses-config.yml").getString("chat-forward.prefix.format.to-qq"))) {
                message = message.replaceFirst(Configs.getConfig("uses-config.yml").getString("chat-forward.prefix.format.to-mc"), "");
            } else {
                this.Cancel = true;
            }
        }

        if (!Cancel) {
            format = format.replace("%name%", sender_name);
            format = format.replace("%code%", String.valueOf(sender_code));
            format = format.replace("%message%", this.message);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
        }
    }

}
