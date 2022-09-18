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
package moe.xmcn.catsero.v2.listeners.GetOnlinePlayers;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import moe.xmcn.catsero.v2.utils.QPS;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class OnGroupMessageEvent implements Listener {

    String online_player_names = "";

    @EventHandler
    public void onMiraiGroupMessageEvent(MiraiGroupMessageEvent e) {
        try {
            if (
                    Configs.JPConfig.uses_config.getBoolean("get-online-players.enabled") &&
                            e.getGroupID() == Configs.getGroupCode(moe.xmcn.catsero.v2.listeners.ChatForward.Utils.X_Group) &&
                            e.getBotID() == Configs.getBotCode(moe.xmcn.catsero.v2.listeners.ChatForward.Utils.X_Bot)
            ) {
                String[] strings = QPS.getParser.parse(e.getMessage());
                if (
                        strings != null &&
                                strings[0].equals("list")
                ) {
                    if (strings.length == 1) {
                        ArrayList<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
                        int players_count = players.toArray().length;
                        players.forEach(it -> online_player_names += it.getName() + ", ");
                        if (!online_player_names.equals("")) online_player_names = online_player_names.substring(0, online_player_names.length() - 2); else online_player_names += "Not player online";

                        String format = Configs.JPConfig.uses_config.getString("get-online-players.format");
                        format = format.replace("%count%", String.valueOf(players_count))
                                .replace("%max%", String.valueOf(Bukkit.getMaxPlayers()))
                                .replace("%list%", online_player_names);

                        Env.AMiraiMC.sendMiraiGroupMessage(format, Utils.X_Bot, Utils.X_Group);
                    } else Env.AMiraiMC.sendMiraiGroupMessage(Configs.getMsgByMsID("qq.invalid-options"), Utils.X_Bot, Utils.X_Group);
                }
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
