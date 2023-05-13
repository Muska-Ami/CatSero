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
package moe.xmcn.catsero.uses.listeners.qWhitelist

import moe.xmcn.catsero.CatSero
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.I18n
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
    private val ThisID: String = Configuration.USESID.QWHITELIST,
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

    @EventHandler
    fun onGroupMessage(e: OnQQGroupCommandEvent) {
        try {
            val args = e.arguments
            if (
                enable
                && e.command.equals("whitelist", true)
                && Configuration().checkBot(e.bot, bot)
                && Configuration().checkGroup(e.group, groups)
            ) {
                Logger.logDebug("EDIT WHITELIST")
                groups.forEach { group ->
                    run {
                        if (Configuration().isQQOp(e.sender)) {
                            when (args[0]) {
                                // 添加
                                "add" -> {
                                    if (args.size == 3) {
                                        val regex = Configuration.getUses().getString(
                                            Configuration.buildYaID(
                                                ThisID, ArrayList<String>(
                                                    listOf(
                                                        "regex"
                                                    )
                                                )
                                            )
                                        )
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
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "add-success"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                                } else
                                                    MessageSender.sendGroup(
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "add-error-sql"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                            } else
                                                MessageSender.sendGroup(
                                                    i18n.getI18n(
                                                        ArrayList(
                                                            listOf(
                                                                "qq", "use", "qwhitelist", "add-error-repeat"
                                                            )
                                                        )
                                                    ),
                                                    bot,
                                                    group
                                                )
                                        else
                                            MessageSender.sendGroup(
                                                i18n.getI18n(
                                                    ArrayList(
                                                        listOf(
                                                            "qq", "use", "qwhitelist", "add-error-name-not-allowed"
                                                        )
                                                    )
                                                ),
                                                bot,
                                                group
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            i18n.getI18n(
                                                ArrayList(
                                                    listOf(
                                                        "qq", "command", "invalid-option"
                                                    )
                                                )
                                            ),
                                            bot,
                                            group
                                        )
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
                                                        CatSero.INSTANCE
                                                    ) {
                                                        Player.getOnlinePlayer(args[1])
                                                            .kickPlayer(
                                                                ChatColor.translateAlternateColorCodes(
                                                                    '&',
                                                                    i18n.getI18n(
                                                                        ArrayList(
                                                                            listOf(
                                                                                "qq", "use", "qwhitelist", "remove-kick"
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                    }
                                                }

                                                MessageSender.sendGroup(
                                                    i18n.getI18n(
                                                        ArrayList(
                                                            listOf(
                                                                "qq", "use", "qwhitelist", "remove-success"
                                                            )
                                                        )
                                                    ),
                                                    bot,
                                                    group
                                                )
                                            } else
                                                MessageSender.sendGroup(
                                                    i18n.getI18n(
                                                        ArrayList(
                                                            listOf(
                                                                "qq", "use", "qwhitelist", "remove-error-sql"
                                                            )
                                                        )
                                                    ),
                                                    bot,
                                                    group
                                                )
                                        } else
                                            MessageSender.sendGroup(
                                                i18n.getI18n(
                                                    ArrayList(
                                                        listOf(
                                                            "qq", "use", "qwhitelist", "remove-error-not-found"
                                                        )
                                                    )
                                                ),
                                                bot,
                                                group
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            i18n.getI18n(
                                                ArrayList(
                                                    listOf(
                                                        "qq", "command", "invalid-option"
                                                    )
                                                )
                                            ),
                                            bot,
                                            group
                                        )
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
                                                            CatSero.INSTANCE
                                                        ) {
                                                            Player.getOnlinePlayer(args[2])
                                                                .kickPlayer(
                                                                    ChatColor.translateAlternateColorCodes(
                                                                        '&',
                                                                        i18n.getI18n(
                                                                            ArrayList(
                                                                                listOf(
                                                                                    "qq",
                                                                                    "use",
                                                                                    "qwhitelist",
                                                                                    "change-kick"
                                                                                )
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                        }
                                                    }

                                                    MessageSender.sendGroup(
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "change-success"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                                } else
                                                    MessageSender.sendGroup(
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "change-error-sql"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                            } else
                                                MessageSender.sendGroup(
                                                    i18n.getI18n(
                                                        ArrayList(
                                                            listOf(
                                                                "qq", "use", "qwhitelist", "change-error-not-found"
                                                            )
                                                        )
                                                    ),
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
                                                            CatSero.INSTANCE
                                                        ) {
                                                            Player.getOnlinePlayer(Player.getUUIDByCode(args[2].toLong()))
                                                                .kickPlayer(
                                                                    ChatColor.translateAlternateColorCodes(
                                                                        '&',
                                                                        i18n.getI18n(
                                                                            ArrayList(
                                                                                listOf(
                                                                                    "minecraft",
                                                                                    "use",
                                                                                    "qwhitelist",
                                                                                    "change-kick"
                                                                                )
                                                                            )
                                                                        )
                                                                    )
                                                                )
                                                        }
                                                    }

                                                    MessageSender.sendGroup(
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "change-success"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                                } else
                                                    MessageSender.sendGroup(
                                                        i18n.getI18n(
                                                            ArrayList(
                                                                listOf(
                                                                    "qq", "use", "qwhitelist", "change-error-sql"
                                                                )
                                                            )
                                                        ),
                                                        bot,
                                                        group
                                                    )
                                            } else
                                                MessageSender.sendGroup(
                                                    i18n.getI18n(
                                                        ArrayList(
                                                            listOf(
                                                                "qq", "use", "qwhitelist", "change-error-not-found"
                                                            )
                                                        )
                                                    ),
                                                    bot,
                                                    group
                                                )
                                        }
                                    } else
                                        MessageSender.sendGroup(
                                            i18n.getI18n(
                                                ArrayList(
                                                    listOf(
                                                        "qq", "command", "invalid-option"
                                                    )
                                                )
                                            ),
                                            bot,
                                            group
                                        )

                                }

                                // 其他
                                else -> {
                                    MessageSender.sendGroup(
                                        i18n.getI18n(
                                            ArrayList(
                                                listOf(
                                                    "qq", "command", "invalid-option"
                                                )
                                            )
                                        ), bot, group
                                    )
                                }
                            }
                        } else
                            MessageSender.sendGroup(
                                i18n.getI18n(
                                    ArrayList(
                                        listOf(
                                            "qq", "command", "no-permission"
                                        )
                                    )
                                ), bot, group
                            )
                    }
                }
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}