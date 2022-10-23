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
package moe.xmcn.catsero.v2.listeners.NewGroupMemberWelcome;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import moe.xmcn.catsero.v2.utils.Loggers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnGroupMessageEvent implements Listener {

    @EventHandler
    public void onMiraiGroupMemberJoinEvent(MiraiMemberJoinEvent event) {
        try {
            if (
                    Configs.JPConfig.uses_config.getBoolean("new-group-member-message.enabled") &&
                            event.getGroupID() == Configs.getGroupCode(Utils.X_Group) &&
                            event.getBotID() == Configs.getBotCode(Utils.X_Bot)
            ) {
                long code = event.getNewMemberID();
                String message = Configs.JPConfig.uses_config.getString("new-group-member-message.format");
                message = message
                        .replace("%code%", String.valueOf(code))
                        .replace("%at%", "[mirai:at:" + code + "]");
                Env.AMiraiMC.sendMiraiGroupMessage(message, Utils.X_Bot, Utils.X_Group);
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
