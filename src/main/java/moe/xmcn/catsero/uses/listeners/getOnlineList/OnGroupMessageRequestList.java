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
package moe.xmcn.catsero.uses.listeners.getOnlineList;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.events.OnQQGroupCommandEvent;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnGroupMessageRequestList implements Listener {

    private final String ThisID = Configuration.USESID.GET_ONLINE_LIST;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;
    private String player_list = null;

    public OnGroupMessageRequestList() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }


    @EventHandler
    public void onGroupMessage(OnQQGroupCommandEvent e) {
        try {
            // 条件
            if (
                    enable
                            && e.getCommand$CatSero().equalsIgnoreCase("list")
                            && new Configuration().checkBot(e.getBot$CatSero(), bot)
                            && new Configuration().checkGroup(e.getGroup$CatSero(), groups)
            ) {
                long group = e.getGroup$CatSero();
                Logger.logDebug("GET_ONLINE_LIST");
                List<Player> list = moe.xmcn.catsero.utils.Player.getOnlinePlayers();

                String format;

                // 0
                format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "format", "0"
                ))));
                format = format.replace("%count%", String.valueOf(list.toArray().length))
                        .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()));
                MessageSender.sendGroup(format, bot, group);

                // 1
                if (list.toArray().length != 0) {
                    list.forEach(it -> player_list += it.getName() + ", ");
                    player_list = player_list.substring(4, player_list.length() - 2);

                    format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                            "format", "1"
                    ))));
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
