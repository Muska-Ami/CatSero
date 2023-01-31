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
package moe.xmcn.catsero.listeners.chatforward;

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.List;

public class OnGroupMessageToMC implements Listener {

    private final boolean enable;
    private final String bot;
    private final String group;
    private String message;

    public OnGroupMessageToMC() {
        this.enable = Configuration.USES_CONFIG.CHAT_FORWARD.ENABLE;
        this.bot = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT;
        this.group = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP;
    }

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent e) {
        try {
            if (
                    enable
                            && e.getBotID() == Configuration.Interface.getBotCode(bot)
                            && e.getGroupID() == Configuration.Interface.getGroupCode(group)
            ) {
                message = e.getMessage();

                // Filter
                if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                    Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.ALL_TO_MC().forEach(it -> message = message.replace(it, Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.REPLACE));
                    run(e, message);
                /*
                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.VIA.TO_MC.forEach(it -> {
                    if (message.contains(it)) filter = true;
                });
                if (!filter)
                    run(e, message);
                else
                    filter = false;

                 */
                } else
                    run(e, message);

            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    private void run(MiraiGroupMessageEvent e, String message) {
        String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_MC;

        // 清理样式代码
        if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_MC)
            message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));

        format = format.replace("%message%", message);

        // 处理名称
        String name = e.getSenderNameCard();

        if (name.equals("")) {
            name = e.getSenderName();
        }
        if (
                Configuration.USES_CONFIG.CHAT_FORWARD.USE_BIND
                        && MiraiMC.getBind(e.getSenderID()) != null
        )
            name = Player.getPlayer(MiraiMC.getBind(e.getSenderID())).getName();

        format = format.replace("%name%", name);

        // 权限
        int sender_permission = e.getSenderPermission();
        switch (sender_permission) {
            case 0:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.MEMBER);
                break;
            case 1:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.ADMIN);
                break;
            case 2:
                format = format.replace("%sender_permission%", Configuration.I18N.QQ.CALL.OWNER);
                break;
        }
        if (Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.ENABLE) {
            if (message.startsWith(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_MC))
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format.replaceFirst(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_MC, "")));
        } else
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
    }

}
