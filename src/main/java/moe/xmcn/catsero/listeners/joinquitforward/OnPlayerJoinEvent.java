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
package moe.xmcn.catsero.listeners.joinquitforward;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.PAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    @EventHandler
    public void onGamePlayerJoinEvent(PlayerJoinEvent pje) {
        try {
            if (Configuration.USES_CONFIG.SEND_PLAYER_JOIN_QUIT.ENABLE) {
                if (Configuration.USES_CONFIG.SEND_PLAYER_JOIN_QUIT.NEED_PERMISSION) {
                    //权限模式
                    if (pje.getPlayer().hasPermission("catsero.send-player-join-quit.join"))
                        run(pje);
                } else
                    run(pje);
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    public void run(PlayerJoinEvent pje) {
        String player_name = pje.getPlayer().getName();
        String join_message = Configuration.USES_CONFIG.SEND_PLAYER_JOIN_QUIT.FORMAT.JOIN;
        join_message = join_message.replace("%player%", player_name);
        join_message = PAPI.toPAPI(pje.getPlayer(), join_message);
        MessageSender.sendGroup(join_message, Configuration.USES_CONFIG.SEND_PLAYER_JOIN_QUIT.MIRAI.BOT, Configuration.USES_CONFIG.SEND_PLAYER_JOIN_QUIT.MIRAI.GROUP);
    }

}