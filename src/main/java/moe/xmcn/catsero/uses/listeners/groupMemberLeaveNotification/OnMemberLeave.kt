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
package moe.xmcn.catsero.uses.listeners.groupMemberLeaveNotification

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberLeaveEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnMemberLeave(
    private val ThisID: String = Configuration.USESID.GROUP_MEMBER_LEAVE_NOTIFICATION,
    private val enable: Boolean = Configuration.getUses().getBoolean(
        Configuration.buildYaID(
            ThisID, ArrayList(
                listOf(
                    "enable"
                )
            )
        )
    ),
    private val bot: String = Configuration.getUseMiraiBot(ThisID),
    private val groups: List<String> = Configuration.getUseMiraiGroup(ThisID)
) : Listener {

    private var group: Long? = null

    @EventHandler
    fun onGroupMemberLeave(e: MiraiMemberLeaveEvent) {
        if (
            enable
            && Configuration().checkBot(e.botID, bot)
            && Configuration().checkGroup(e.groupID, groups)
        ) {
            group = e.groupID
            try {
                val name = e.memberNick
                val code = e.targetID

                var format = Configuration.getUses().getString(
                    Configuration.buildYaID(
                        ThisID, ArrayList(
                            listOf(
                                "format"
                            )
                        )
                    )
                )
                format = format.replace("%name%", name)
                    .replace("%code%", code.toString())
                MessageSender.sendGroup(format, bot, group!!)

            } catch (ex: Exception) {
                Logger.logCatch(ex)
            }
        }
    }

}