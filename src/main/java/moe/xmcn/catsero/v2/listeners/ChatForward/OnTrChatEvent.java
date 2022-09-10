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

import me.arasple.mc.trchat.api.event.TrChatEvent;
import moe.xmcn.catsero.v2.listeners.JoinQuitForward.Utils;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnTrChatEvent implements Listener {

    private boolean Cancel = false;
    private String message;

    @EventHandler
    public void onTrChatEvent(TrChatEvent e) {
        if (Configs.getConfig("uses-config.yml").getBoolean("chat-forward.enabled")) {
            this.message = e.getMessage();
            String channel = e.getChannel().getId();
            Player sender = e.getSession().getPlayer();
            String world = sender.getWorld().getName();

            String format = Configs.getConfig("uses-config.yml").getString("chat-forward.format.to-qq");

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
                    message = message.replaceFirst(Configs.getConfig("uses-config.yml").getString("chat-forward.prefix.format.to-qq"), "");
                } else {
                    this.Cancel = true;
                }
            }

            if (!Cancel) {
                format = format.replace("%channel%", channel);
                format = format.replace("%world%", world);
                format = format.replace("%player%", sender.getName());
                format = format.replace("%message%", message);
                Env.AMiraiMC.sendMiraiGroupMessage(format, Utils.X_Bot, Utils.X_Group);
            }
        }
    }

}
