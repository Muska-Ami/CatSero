package moe.xmcn.catsero.events.listeners.OPPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import moe.xmcn.catsero.utils.PlayerUUID
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.permissions.ServerOperator

class OnGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "setop" && Config.UsesConfig.getBoolean("qop-player.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            isEnabled(event, args)
        }
    }

    private fun isEnabled(event: MiraiGroupMessageEvent, args: List<String>) {
        if (event.senderID == Config.QQ_OP) {
            val pl = PlayerUUID.getUUIDByName(args[2])
            val plname: ServerOperator = Bukkit.getPlayer(pl)
            val isOp = plname.isOp
            if (isOp) {
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                        .sendMessageMirai(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-is-op"))
                } catch (nse: NoSuchElementException) {
                    Config.plugin.logger.warning(
                        Config.getMsgByMsID("general.send-message-qq-error")
                            .replace("%error%", nse.toString() + nse.stackTrace)
                    )
                }
            } else {
                plname.isOp = true
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                        .sendMessageMirai(Config.Prefix_QQ + "已添加新的管理员")
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
    }

}