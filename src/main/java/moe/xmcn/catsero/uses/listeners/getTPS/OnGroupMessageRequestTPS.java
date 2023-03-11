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
package moe.xmcn.catsero.uses.listeners.getTPS;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.events.OnQQGroupCommandEvent;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.timers.TPSCalculator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnGroupMessageRequestTPS implements Listener {

    private final I18n i18n = new I18n();

    private final String ThisID = Configuration.USESID.GET_TPS;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;

    public OnGroupMessageRequestTPS() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }

    @EventHandler
    public void onGroupMessageEvent(OnQQGroupCommandEvent e) {
        try {
            String[] args = e.getArguments$CatSero().toArray(new String[0]);
            // 条件
            if (
                    enable
                            && e.getCommand$CatSero().equalsIgnoreCase("tps")
                            && new Configuration().checkBot(e.getBot$CatSero(), bot)
                            && new Configuration().checkGroup(e.getGroup$CatSero(), groups)
            ) {
                long group = e.getGroup$CatSero();
                Logger.logDebug("GET_TPS");
                if (args.length == 1) {
                    // 处理TPS
                    double tps = TPSCalculator.getTPS();
                    BigDecimal around_tps = BigDecimal.valueOf(tps).setScale(1, RoundingMode.HALF_UP);

                    switch (args[0]) {
                        case "accurate":
                            // 精确
                            MessageSender.sendGroup("TPS: " + tps, bot, group);
                            break;
                        case "around":
                            // 大概
                            MessageSender.sendGroup("TPS: " + around_tps, bot, group);
                            break;
                        default:
                            MessageSender.sendGroup(i18n.getI18n(new ArrayList<>(Arrays.asList(
                                    "qq", "command", "invalid-option"
                            ))), bot, group);
                    }
                } else
                    MessageSender.sendGroup(i18n.getI18n(new ArrayList<>(Arrays.asList(
                            "qq", "command", "invalid-option"
                    ))), bot, group);
            }

        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
