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
package moe.xmcn.catsero.listeners.gettps;

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.QPS;
import moe.xmcn.catsero.utils.TPSCalculator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OnGroupMessageRequestTPS implements Listener {

    @EventHandler
    public void onGroupMessageEvent(MiraiGroupMessageEvent e) {
        String[] args = QPS.parse(e.getMessage(), "tps");

        if (args != null) {
            // 条件
            if (
                    Configuration.USES_CONFIG.GET_TPS.ENABLE
                            && e.getBotID() == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                            && e.getGroupID() == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)

            ) {
                if (args.length == 1) {
                    // 处理TPS
                    double tps = TPSCalculator.getTPS();
                    BigDecimal around_tps = BigDecimal.valueOf(tps).setScale(1, RoundingMode.HALF_UP);

                    switch (args[0]) {
                        case "accurate":
                            // 精确
                            MessageSender.sendGroup("TPS: " + tps, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                            break;
                        case "around":
                            // 大概
                            MessageSender.sendGroup("TPS: " + around_tps, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                            break;
                        default:
                            MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
                    }
                } else
                    MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, Configuration.USES_CONFIG.GET_TPS.MIRAI.BOT, Configuration.USES_CONFIG.GET_TPS.MIRAI.GROUP);
            }
        }
    }

}
