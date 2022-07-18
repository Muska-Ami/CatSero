package moe.xmcn.catsero.events.listeners.KickPlayerQQ

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import moe.xmcn.catsero.utils.PlayerUUID
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "kick" && Config.UsesConfig.getBoolean("qkick-player.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            isEnabled(event, args)
        }
    }

    private fun isEnabled(event: MiraiGroupMessageEvent, args: List<String>) {
        if (event.senderID == Config.QQ_OP) {
            val pl = PlayerUUID.getUUIDByName(args[2])
            Bukkit.getPlayer(pl).kickPlayer(Config.UsesConfig.getString("qkick-player.message"))
            Config.sendMiraiGroupMessage(
                Config.Prefix_QQ + Config.getMsgByMsID("qq.qkick-player.kick").replace("%player%", args[2])
            )
        } else {
            Config.sendMiraiGroupMessage(Config.Prefix_QQ + Config.getMsgByMsID("qq.no-permission"))
        }
    }

}