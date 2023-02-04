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
package moe.xmcn.catsero.listeners.qcmd

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.QPS
import moe.xmcn.catsero.utils.QQCommandSender
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnRequestExecuteCommand(
    private val enable: Boolean = Configuration.USES_CONFIG.QCMD.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QCMD.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QCMD.MIRAI.GROUP
) : Listener {

    private var output: List<String> = ArrayList()

    @EventHandler
    fun group(e: MiraiGroupMessageEvent) {
        if (
            e.botID == Configuration.Interface.getBotCode(bot)
            && e.groupID == Configuration.Interface.getGroupCode(group)
        ) {
            val sender = e.senderID
            val message = e.message
            run(sender, message, false)
        }
    }

    @EventHandler
    fun friend(e: MiraiFriendMessageEvent) {
        val sender = e.senderID
        val message = e.message
        run(sender, message, true)
    }

    private fun run(sender: Long, message: String, isFriend: Boolean) {
        try {
            if (enable) {
                val args = QPS.parse(message, "cmd")
                if (args != null) {
                    if (Configuration.Interface.isQQOp(sender)) {
                        // 读取命令原文
                        val command = iterateArray(args)
                        val result = executeCommand(command)

                        // 每一个返回值做一条消息发送，防止触发QQ消息长度限制
                        if (isFriend)
                            for (item in result) {
                                MessageSender.sendFriend(item, bot, sender)
                            }
                        else
                            MessageSender.sendGroup(Configuration.I18N.QQ.USE.QCMD.SUCCESS, bot, group)
                        for (item in result) {
                            MessageSender.sendFriend(item, bot, sender)
                        }
                    } else if (isFriend)
                        MessageSender.sendFriend(Configuration.I18N.QQ.COMMAND.NO_PERMISSION, bot, sender)
                    else
                        MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.NO_PERMISSION, bot, group)
                }
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

    /**
     * 执行命令
     * @param command 命令内容
     */
    private fun executeCommand(command: String): List<String> {
        val cs = QQCommandSender()
        Bukkit.getScheduler().runTask(Configuration.plugin) {
            Bukkit.dispatchCommand(cs, command)
            output = QQCommandSender.getCmdOutput()
        }
        return output
    }

    /**
     * 获得完整命令
     * @param items 解析后的命令数组
     */
    private fun iterateArray(items: Array<String?>): String {
        val ifd = StringBuilder()
        for (element in items) {
            ifd.append(element)
            ifd.append(" ")
        }
        println(ifd)
        return ifd.toString().substring(0, ifd.length - 1)
    }
}