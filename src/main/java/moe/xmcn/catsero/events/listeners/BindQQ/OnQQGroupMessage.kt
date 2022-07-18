package moe.xmcn.catsero.events.listeners.BindQQ

import me.dreamvoid.miraimc.api.MiraiMC
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import moe.xmcn.catsero.utils.PlayerUUID
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnQQGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        if (Config.UsesConfig.getBoolean("bind-qq.enabled")) {
            val message = event.message
            val args = message.split(" ")
            if (args[0] == "catsero" && args[1] == "bind") {
                if (event.senderID == Config.QQ_OP) {
                    if (args.size == 5 && args[2] == "add") {
                        if (MiraiMC.getBind(args[3].toLong())
                                .toString() != "" || MiraiMC.getBind(PlayerUUID.getUUIDByName(args[3])) != 0L
                        ) {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.already-bind"))
                        } else {
                            MiraiMC.addBind(PlayerUUID.getUUIDByName(args[4]), args[3].toLong())
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.add-success"))
                        }
                    } else if (args.size == 4 && args[2] == "remove") {
                        if (MiraiMC.getBind(args[3].toLong())
                                .toString() != "" || MiraiMC.getBind(PlayerUUID.getUUIDByName(args[3])) != 0L
                        ) {
                            MiraiMC.removeBind(args[3].toLong())
                            MiraiMC.removeBind(PlayerUUID.getUUIDByName(args[3]))
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.remove-success"))

                        } else {
                            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.bind-qq.no-bind-found"))
                        }
                    }
                } else {
                    Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
                }
            }
        }
    }

}