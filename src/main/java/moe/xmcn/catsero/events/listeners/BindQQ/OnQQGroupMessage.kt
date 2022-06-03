package moe.xmcn.catsero.events.listeners.BindQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.api.MiraiMC
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
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
                if (event.senderID == Config.QQ_OP) {
                    if (args.size == 5 && args[2] == "add") {
                        if (MiraiMC.getBinding(args[3].toLong()) != "" || MiraiMC.getBinding(args[3]) != 0L) {
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
                            MiraiMC.addBinding(PlayerUUID.getUUIDByName(args[4]).toString(), args[3].toLong())
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
                        if (MiraiMC.getBinding(args[3].toLong()) != "" || MiraiMC.getBinding(args[3]) != 0L) {
                            MiraiMC.removeBinding(args[3].toLong())
                            MiraiMC.removeBinding(args[3])
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
            }
        }
    }

}