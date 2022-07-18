package moe.xmcn.catsero.events.listeners.OPPlayerQQ

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
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.qop-player.already-is-op"))
            } else {
                plname.isOp = true
                Config.sendMiraiGroupMessage(Config.Prefix_QQ + "已添加新的管理员")
            }
        } else {
            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
        }
    }

}