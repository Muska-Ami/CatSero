package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.*
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class WhiteListEditor : Listener {

    @EventHandler
    fun onGroupMessage(e: MiraiGroupMessageEvent) {
        try {
            val args = QPS.parse(e.message, "whitelist")
            if (args != null) {
                if (
                    Configuration.USES_CONFIG.QWHITELIST.ENABLE
                    && e.botID == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                    && e.groupID == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)
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
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_SQL,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_REPEAT,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                        )
                                } else
                                    MessageSender.sendGroup(
                                        Configuration.I18N.QQ.COMMAND.INVALID_OPTION,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
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
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_ERROR_SQL,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_ERROR_NOT_FOUND,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                        )
                                } else
                                    MessageSender.sendGroup(
                                        Configuration.I18N.QQ.COMMAND.INVALID_OPTION,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                    )
                            }

                            // 更新
                            "change" -> {
                                if (args.size == 3) {
                                    if (WhiteListDatabase.getNameList().contains(args[1])) {
                                        /*
                                        list.getStringList("list").remove(args[2])
                                        list.getStringList("list").add(args[3])
                                        list.save(configuration.whitelist_file)
                                        list.load(configuration.whitelist_file)

                                         */

                                        if (WhiteListDatabase.updateList(args[1], args[2])) {
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
                                                                Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHANGE_KICK
                                                            )
                                                        )
                                                }
                                            }

                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_SUCCESS,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                        } else
                                            MessageSender.sendGroup(
                                                Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_SQL,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                            )
                                    } else
                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_ERROR_NOT_FOUND,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                        )
                                } else
                                    MessageSender.sendGroup(
                                        Configuration.I18N.QQ.COMMAND.INVALID_OPTION,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                    )

                            }

                            // 其他
                            else -> {
                                MessageSender.sendGroup(
                                    Configuration.I18N.QQ.COMMAND.INVALID_OPTION,
                                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                                )
                            }
                        }
                    } else
                        MessageSender.sendGroup(
                            Configuration.I18N.QQ.COMMAND.NO_PERMISSION,
                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                            Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                        )
                }
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}