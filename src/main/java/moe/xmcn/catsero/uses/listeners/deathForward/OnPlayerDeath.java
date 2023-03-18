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
package moe.xmcn.catsero.uses.listeners.deathForward;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.PAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnPlayerDeath implements Listener {

    private final String ThisID = Configuration.USESID.SEND_PLAYER_DEATH;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;

    public OnPlayerDeath() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        try {
            if (enable) {
                // 检查权限
                if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                        "need-permission"
                ))))) {
                    if (e.getEntity().hasPermission("catsero.send-death"))
                        run(e);
                } else
                    run(e);
            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    public void run(PlayerDeathEvent e) {
        groups.forEach(group -> {
            try {
                Player player = e.getEntity();
                String death_message = e.getDeathMessage();

                String message = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                        "format"
                ))));

                message = message.replace("%player%", player.getName())
                        .replace("%message%", death_message);
                message = PAPI.toPAPI(player, message);
                MessageSender.sendGroup(message, bot, group);

            } catch (Exception ex) {
                Logger.logCatch(ex);
            }
        });
    }

}