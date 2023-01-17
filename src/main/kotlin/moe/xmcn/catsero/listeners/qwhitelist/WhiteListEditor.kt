package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class WhiteListEditor(
    private val enable: Boolean = Configuration.USES_CONFIG.QWHITELIST.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onGroupMessage(e: MiraiGroupMessageEvent) {
        try {
            val args = QPS.parse(e.message, "whitelist")
            if (args != null) {
                if (
                    Configuration.USES_CONFIG.QWHITELIST.ENABLE
                    && e.botID == Configuration.Interface.getBotCode(bot)
                    && e.groupID == Configuration.Interface.getGroupCode(group)
                ) {
                    if (Configuration.Interface.isQQOp(e.senderID)) {
                        when (args[0]) {
                            // 添加
                            "add" -> {
                                if (args.size == 3) {
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
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}