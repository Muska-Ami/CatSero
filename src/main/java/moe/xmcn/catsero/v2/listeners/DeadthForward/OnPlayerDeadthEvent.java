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
package moe.xmcn.catsero.v2.listeners.DeadthForward;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeadthEvent implements Listener {

    @EventHandler
    public void onPlayerDeadthEvent(PlayerDeathEvent e) {
        try {
            if (Configs.JPConfig.uses_config.getBoolean("send-player-death.enabled")) {
                Player player = e.getEntity();
                String death_message = e.getDeathMessage();

                String format = Configs.JPConfig.uses_config.getString("send-player-death.format");

                format = format.replace("%player%", player.getName());
                format = format.replace("%deathmes%", death_message);
                Env.AMiraiMC.sendMiraiGroupMessage(format, Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
