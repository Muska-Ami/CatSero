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
package moe.xmcn.catsero.listeners.getonlinelist;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.events.OnQQGroupCommandEvent;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class OnGroupMessageRequestList implements Listener {

    private final boolean enable;
    private final String bot;
    private final String group;
    private String player_list = null;

    public OnGroupMessageRequestList() {
        this.enable = Configuration.USES_CONFIG.GET_ONLINE_LIST.ENABLE;
        this.bot = Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT;
        this.group = Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP;
    }


    @EventHandler
    public void onGroupMessage(OnQQGroupCommandEvent e) {
        try {
            // 条件
            if (
                    enable
                            && e.getCommand$CatSero().equalsIgnoreCase("list")
                            && e.getBot$CatSero() == Configuration.Interface.getBotCode(bot)
                            && e.getGroup$CatSero() == Configuration.Interface.getGroupCode(group)
            ) {
                List<Player> list = moe.xmcn.catsero.utils.Player.getOnlinePlayers();

                String format;

                // 0
                format = Configuration.USES_CONFIG.GET_ONLINE_LIST.FORMAT_0;
                format = format.replace("%count%", String.valueOf(list.toArray().length))
                        .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()));
                MessageSender.sendGroup(format, bot, group);

                // 1
                if (list.toArray().length != 0) {
                    list.forEach(it -> player_list += it.getName() + ", ");
                    player_list = player_list.substring(4, player_list.length() - 2);

                    format = Configuration.USES_CONFIG.GET_ONLINE_LIST.FORMAT_1;
                    format = format.replace("%count%", String.valueOf(list.toArray().length))
                            .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()))
                            .replace("%list%", player_list);
                    MessageSender.sendGroup(format, bot, group);

                    player_list = null;
                }
            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
