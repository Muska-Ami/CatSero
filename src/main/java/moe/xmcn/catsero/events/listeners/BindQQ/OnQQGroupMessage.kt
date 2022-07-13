package moe.xmcn.catsero.events.listeners.BindQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.api.MiraiMC
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.events.gists.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnQQGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "bind") {
            if (Config.UsesConfig.getBoolean("bind-qq.enabled")) {
                isEnabled(event, args)
            }
        }
    }

    private fun isEnabled(event: MiraiGroupMessageEvent, args: List<String>) {
        if (args.size == 5 && args[2] == "add") {
            if (event.senderID == Config.QQ_OP) {
                if (MiraiMC.getBind(args[3].toLong()).toString() != "" || MiraiMC.getBind(PlayerUUID.getUUIDByName(args[3])) != 0L) {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Bot)
                            .sendMessageMirai(Config.getMsgByMsID("qq.bind-qq.already-bind"))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                } else {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                }
            } else {
                MiraiMC.addBind(PlayerUUID.getUUIDByName(args[4]), args[3].toLong())
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Bot)
                        .sendMessageMirai(Config.getMsgByMsID("qq.bind-qq.add-success"))
                } catch (nse: NoSuchElementException) {
                    Config.plugin.logger.warning(
                        Config.getMsgByMsID("general.send-message-qq-error")
                            .replace("%error%", nse.toString() + nse.stackTrace)
                    )
                }
            }
        } else if (args.size == 4 && args[2] == "remove") {
            if (event.senderID == Config.QQ_OP) {
                if (MiraiMC.getBind(args[3].toLong()).toString() != "" || MiraiMC.getBind(PlayerUUID.getUUIDByName(args[3])) != 0L) {
                    MiraiMC.removeBind(args[3].toLong())
                    MiraiMC.removeBind(PlayerUUID.getUUIDByName(args[3]))
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Bot)
                            .sendMessageMirai(Config.getMsgByMsID("qq.bind-qq.remove-success"))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                } else {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Bot)
                            .sendMessageMirai(Config.getMsgByMsID("qq.bind-qq.no-bind-found"))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                }
            } else {
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                        .sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
                } catch (nse: NoSuchElementException) {
                    Config.plugin.logger.warning(
                        Config.getMsgByMsID("general.send-message-qq-error")
                            .replace("%error%", nse.toString() + nse.stackTrace)
                    )
                }
            }
        } else if (args.size == 2 && Config.UsesConfig.getBoolean("self-service-application.enabled") && args[0] == Config.UsesConfig.getString(
                "bind-qq.self-service-application.prefix"
            )
        ) {
            if (MiraiMC.getBind(PlayerUUID.getUUIDByName(args[1])) != 0L) {
                MiraiMC.addBind(PlayerUUID.getUUIDByName(args[1]), event.senderID)
                try {
                    val mesbd = Config.getMsgByMsID("qq.bind-qq.add-ssa-success")
                        .replace("%name%", args[1])
                        .replace("%code%", event.senderID.toString())
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Bot)
                        .sendMessageMirai(mesbd)
                } catch (nse: NoSuchElementException) {
                    Config.plugin.logger.warning(
                        Config.getMsgByMsID("general.send-message-qq-error")
                            .replace("%error%", nse.toString() + nse.stackTrace)
                    )
                }
            }
        }
    }

}