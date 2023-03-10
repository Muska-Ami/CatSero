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
package moe.xmcn.catsero.listeners.advancementforward;

import me.croabeast.advancementinfo.AdvancementInfo;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.PAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class OnPlayerAdvancementDone implements Listener {

    private final boolean enable;
    private final String bot;
    private final String group;

    public OnPlayerAdvancementDone() {
        this.enable = Configuration.USES_CONFIG.SEND_ADVANCEMENT.ENABLE;
        this.bot = Configuration.USES_CONFIG.SEND_ADVANCEMENT.MIRAI.BOT;
        this.group = Configuration.USES_CONFIG.SEND_ADVANCEMENT.MIRAI.GROUP;
    }

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent e) {
        try {
            if (enable) {
                if (Configuration.USES_CONFIG.SEND_ADVANCEMENT.NEED_PERMISSION) {
                    if (e.getPlayer().hasPermission("catsero.send-advancement"))
                        run(e);
                } else
                    run(e);
            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    public void run(PlayerAdvancementDoneEvent e) {
        try {
            AdvancementInfo ai = new AdvancementInfo(e.getAdvancement());
            String adv_name = ai.getTitle();
            String adv_description = ai.getDescription();
            Player player = e.getPlayer();

            String format = Configuration.USES_CONFIG.SEND_ADVANCEMENT.FORMAT;
            if (adv_name != null) {
                format = format.replace("%player%", player.getName())
                        .replace("%name%", adv_name)
                        .replace("%description%", adv_description);
                format = PAPI.toPAPI(player, format);
                MessageSender.sendGroup(format, bot, group);
            }

        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
