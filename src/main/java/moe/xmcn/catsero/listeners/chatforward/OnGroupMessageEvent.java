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

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import me.dreamvoid.miraimc.api.MiraiMC;
import moe.xmcn.catsero.utils.Player;
import moe.xmcn.catsero.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent e) {
        if (
                Configuration.USES_CONFIG.CHAT_FORWARD.ENAbLE
                && e.getBotID() == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT)
                && e.getGroupID() == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP)
        ) {
            String message = e.getMessage();

            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                if (!message.contains(Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.TO_MC))
                    run(e, message);
            } else
                run(e, message);

        }
    }

    private void run(MiraiGroupMessageEvent e, String message) {
        String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_MC;

        if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_MC)
            message = cleanStyleCodes(message);
        format = format.replace("%message%", message);

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
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));

    }

    private static String cleanStyleCodes(String s) {
        Set<String> s0 = new HashSet<>(Arrays.asList(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "a",
                "b",
                "c",
                "d",
                "e",
                "f",
                "k",
                "l",
                "o",
                "r"
        ));
        for (var i=0;i<s0.toArray().length; i++) {
            s = s.replace("" + s0.toArray()[i], "");
        }
        return s;
    }

}
