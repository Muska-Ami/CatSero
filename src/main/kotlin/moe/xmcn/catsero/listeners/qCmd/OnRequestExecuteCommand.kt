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
package moe.xmcn.catsero.listeners.qCmd

import moe.xmcn.catsero.Configuration
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
    private val enable: Boolean = Configuration.USES_CONFIG.QCMD.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QCMD.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QCMD.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun group(e: OnQQGroupCommandEvent) {
        if (
            e.command == "cmd"
            && e.bot == Configuration.Interface.getBotCode(bot)
            && e.group == Configuration.Interface.getGroupCode(group)
        ) {
            val sender = e.sender
            val args = e.arguments
            run(sender, args, false)
        }
    }

    @EventHandler
    fun friend(e: OnQQFriendCommandEvent) {
        if (
            e.command.equals("cmd", true)
            && e.bot == Configuration.Interface.getBotCode(bot)
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
                if (Configuration.Interface.isQQOp(sender)) {
                    // 读取命令原文
                    val command = iterateArray(args)

                    // 如果不是私聊则发一条提示
                    if (!isFriend)
                        MessageSender.sendGroup(Configuration.I18N.QQ.USE.QCMD.SUCCESS, bot, group)

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
                    MessageSender.sendFriend(Configuration.I18N.QQ.COMMAND.NO_PERMISSION, bot, sender)
                else
                    MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.NO_PERMISSION, bot, group)
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
        Bukkit.getScheduler().runTask(Configuration.plugin) {
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