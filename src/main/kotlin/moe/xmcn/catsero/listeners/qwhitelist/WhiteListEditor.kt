package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.QPS
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.lang.Exception

class WhiteListEditor : Listener {

    val list: FileConfiguration = Configuration.CFI.whitelist_list

    @EventHandler
    fun onGroupMessage(e: MiraiGroupMessageEvent) {
        try {
            val args = QPS.parse(e.message)
            if (args != null) {
                if (
                    Configuration.USES_CONFIG.QWHITELIST.ENABLE
                    && args[0].equals("whitelist", true)
                    && e.botID == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                    && e.groupID == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)
                ) {
                    if (Configuration.Interface.isQQOp(e.senderID)) {
                        when (args[1]) {
                            // 添加
                            "add" -> {
                                if (args.size == 3) {
                                    if (!list.getStringList("list").contains(args[2])) {
                                        list.getStringList("list").add(args[2])
                                        list.save(Configuration.CFI.whitelist_file)
                                        list.load(Configuration.CFI.whitelist_file)

                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.ADD_SUCCESS,
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
                                if (args.size == 3) {
                                    if (list.getStringList("list").contains(args[2])) {
                                        list.getStringList("list").remove(args[2])
                                        list.save(Configuration.CFI.whitelist_file)
                                        list.load(Configuration.CFI.whitelist_file)

                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.REMOVE_SUCCESS,
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
                                if (args.size == 4) {
                                    if (list.getStringList("list").contains(args[2])) {
                                        list.getStringList("list").remove(args[2])
                                        list.getStringList("list").add(args[3])
                                        list.save(Configuration.CFI.whitelist_file)
                                        list.load(Configuration.CFI.whitelist_file)

                                        MessageSender.sendGroup(
                                            Configuration.I18N.QQ.USE.QWHITELIST.CHANGE_SUCCESS,
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
        } catch (ex : Exception) {
            Logger.logCatch(ex)
        }
    }

}