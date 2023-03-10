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
package moe.xmcn.catsero.listeners.groupmemberleave

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnMemberLeave(
    private val enable: Boolean = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onGroupMemberLeave(e: MiraiMemberLeaveEvent) {
        try {
            if (
                enable
                && e.botID == Configuration.Interface.getBotCode(bot)
                && e.groupID == Configuration.Interface.getGroupCode(group)
            ) {
                val name = e.memberNick
                val code = e.targetID

                var format = Configuration.USES_CONFIG.GROUP_MEMBER_LEAVE.FORMAT
                format = format.replace("%name%", name)
                    .replace("%code%", code.toString())
                MessageSender.sendGroup(format, bot, group)
            }

        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}