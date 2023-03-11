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
package moe.xmcn.catsero.listeners.qWhitelist

import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.events.OnQQGroupCommandEvent
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.Player
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.regex.Pattern

class WhiteListEditor(
    private val enable: Boolean = Configuration.USES_CONFIG.QWHITELIST.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onGroupMessage(e: OnQQGroupCommandEvent) {
        try {
            val args = e.arguments
            if (
                enable
                && e.command.equals("whitelist", true)
                && e.bot == Configuration.Interface.getBotCode(bot)
                && e.group == Configuration.Interface.getGroupCode(group)
            ) {
                Logger.logDebug("EDIT WHITELIST")
                if (Configuration.Interface.isQQOp(e.sender)) {
                    when (args[0]) {
                        // 添加
                        "add" -> {
                            if (args.size == 3) {
                                val regex = Configuration.USES_CONFIG.QWHITELIST.REGEX
                                val match = Pattern.matches(regex, args[1])
                                if (match)
                                    if (!WhiteListDatabase.getNameList().contains(args[1])) {
                                        /*
                                    list.getStringList("list").add(args[2])
                                    list.save(configuration.whitelist_file)
                                    list.load(configuration.whitelist_file)

                                     */
                                        if (WhiteListDatabase.insertList(args[1], args[2].toLong())) {

                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.ADD_SUCCESS,
                                                bot,
                                                group
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_SQL,
                                                bot,
                                                group
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_REPEAT,
                                            bot,
                                            group
                                        )
                                else
                                    MessageSender.sendGroup(
                                        Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_NAME_NOT_ALLOWED,
                                        bot,
                                        group
                                    )
                            } else
                                MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, bot, group)
                        }

                        // 移除
                        "remove" -> {
                            if (args.size == 2) {
                                if (WhiteListDatabase.getNameList().contains(args[1])) {
                                    /*
                                    list.getStringList("list").remove(args[2])
                                    list.save(configuration.whitelist_file)
                                    list.load(configuration.whitelist_file)

                                     */

                                    if (WhiteListDatabase.removeList(args[1])) {
                                        // 如果玩家在线，将玩家踢出
                                        if (
                                            Player.getUUIDByName(args[1]) != null
                                            && Player.getPlayer(args[1]).isOnline
                                        ) {
                                            Bukkit.getScheduler().runTask(
                                                Configuration.plugin
                                            ) {
                                                Player.getOnlinePlayer(args[1])
                                                    .kickPlayer(
                                                        ChatColor.translateAlternateColorCodes(
                                                            '&',
                                                            Configuration.I18N.MINECRAFT.USE.QWHITELIST.REMOVE_KICK
                                                        )
                                                    )
                                            }
                                        }

                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_SUCCESS,
                                            bot,
                                            group
                                        )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_ERROR_SQL,
                                            bot,
                                            group
                                        )
                                } else
                                    MessageSender.sendGroup(
                                        Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_ERROR_NOT_FOUND,
                                        bot,
                                        group
                                    )
                            } else
                                MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, bot, group)
                        }

                        // 更新
                        "change" -> {
                            if (args.size == 4) {
                                if (args[1].equals("name", true)) {
                                    if (WhiteListDatabase.getNameList().contains(args[2])) {
                                        /*
                                    list.getStringList("list").remove(args[2])
                                    list.getStringList("list").add(args[3])
                                    list.save(configuration.whitelist_file)
                                    list.load(configuration.whitelist_file)

                                     */

                                        if (WhiteListDatabase.updateList(args[2], args[3])) {
                                            // 如果玩家在线，将玩家踢出
                                            if (
                                                Player.getUUIDByName(args[2]) != null
                                                && Player.getPlayer(args[2]).isOnline
                                            ) {
                                                Bukkit.getScheduler().runTask(
                                                    Configuration.plugin
                                                ) {
                                                    Player.getOnlinePlayer(args[2])
                                                        .kickPlayer(
                                                            ChatColor.translateAlternateColorCodes(
                                                                '&',
                                                                Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_KICK
                                                            )
                                                        )
                                                }
                                            }

                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_SUCCESS,
                                                bot,
                                                group
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_SQL,
                                                bot,
                                                group
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_NOT_FOUND,
                                            bot,
                                            group
                                        )
                                } else if (args[1].equals("qq", true)) {
                                    if (WhiteListDatabase.getCodeList().contains(args[2].toLong())) {
                                        if (WhiteListDatabase.updateList(args[2].toLong(), args[3].toLong())) {
                                            // 如果玩家在线，将玩家踢出
                                            if (
                                                Player.getUUIDByCode(args[2].toLong()) != null
                                                && Player.getPlayer(Player.getUUIDByCode(args[2].toLong())).isOnline
                                            ) {
                                                Bukkit.getScheduler().runTask(
                                                    Configuration.plugin
                                                ) {
                                                    Player.getOnlinePlayer(Player.getUUIDByCode(args[2].toLong()))
                                                        .kickPlayer(
                                                            ChatColor.translateAlternateColorCodes(
                                                                '&',
                                                                Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_KICK
                                                            )
                                                        )
                                                }
                                            }

                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_SUCCESS,
                                                bot,
                                                group
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_SQL,
                                                bot,
                                                group
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_NOT_FOUND,
                                            bot,
                                            group
                                        )
                                }
                            } else
                                MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, bot, group)

                        }

                        // 其他
                        else -> {
                            MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.INVALID_OPTION, bot, group)
                        }
                    }
                } else
                    MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.NO_PERMISSION, bot, group)
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}