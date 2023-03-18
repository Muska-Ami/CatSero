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
package moe.xmcn.catsero.uses.listeners.qCmd

import moe.xmcn.catsero.CatSero
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.I18n
import moe.xmcn.catsero.events.OnQQFriendCommandEvent
import moe.xmcn.catsero.events.OnQQGroupCommandEvent
import moe.xmcn.catsero.utils.CommandSender
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.text.SimpleDateFormat
import java.util.*

class OnRequestExecuteCommand(
    private val ThisID: String = Configuration.USESID.QCMD,
    private val enable: Boolean = Configuration.getUses().getBoolean(
        Configuration.buildYaID(
            ThisID, ArrayList<String>(
                listOf(
                    "enable"
                )
            )
        )
    ),
    private val bot: String = Configuration.getUseMiraiBot(ThisID),
    private val groups: List<String> = Configuration.getUseMiraiGroup(ThisID)
) : Listener {

    private val i18n = I18n()
    private var group: Long? = null

    @EventHandler
    fun groupCommand(e: OnQQGroupCommandEvent) {
        if (
            e.command == "cmd"
            && Configuration().checkBot(e.bot, bot)
            && Configuration().checkGroup(e.group, groups)
        ) {
            val sender = e.sender
            val args = e.arguments
            group = e.group
            run(sender, args, false)
        }
    }

    @EventHandler
    fun friendCommand(e: OnQQFriendCommandEvent) {
        if (
            e.command.equals("cmd", true)
            && Configuration().checkBot(e.bot, bot)
        ) {
            val sender = e.sender
            val args = e.arguments
            run(sender, args, true)
        }
    }

    private fun run(sender: Long, args: List<String>, isFriend: Boolean) {
        try {
            if (enable) {
                Logger.logDebug("QCMD Friend: $isFriend")
                if (Configuration().isQQOp(sender)) {
                    // 读取命令原文
                    val command = iterateArray(args)

                    // 如果不是私聊则发一条提示
                    if (!isFriend)
                        MessageSender.sendGroup(
                            i18n.getI18n(
                                ArrayList(
                                    listOf(
                                        "qq", "use", "qcmd", "success"
                                    )
                                )
                            ), bot, group!!
                        )

                    // 设置信息
                    CommandSender.setBot(bot)
                    CommandSender.setFriend(sender)

                    // 执行
                    MessageSender.sendFriend(
                        "--- " + SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Date()) + " ---",
                        bot,
                        sender
                    )
                    executeCommand(command)
                } else if (isFriend)
                    MessageSender.sendFriend(
                        i18n.getI18n(
                            ArrayList(
                                listOf(
                                    "qq", "command", "no-permission"
                                )
                            )
                        ), bot, sender
                    )
                else
                    MessageSender.sendGroup(
                        i18n.getI18n(
                            ArrayList(
                                listOf(
                                    "qq", "command", "no-permission"
                                )
                            )
                        ), bot, group!!
                    )
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

    /**
     * 执行命令
     * @param command 命令内容
     */
    private fun executeCommand(command: String) {
        val cs = CommandSender()
        Bukkit.getScheduler().runTask(CatSero.INSTANCE) {
            Bukkit.dispatchCommand(cs, command)
        }
    }

    /**
     * 获得完整命令
     * @param items 解析后的命令数组
     */
    private fun iterateArray(items: List<String>): String {
        val ifd = StringBuilder()
        for (element in items) {
            ifd.append(element)
            ifd.append(" ")
        }
        println(ifd)
        return ifd.toString().substring(0, ifd.length - 1)
    }
}