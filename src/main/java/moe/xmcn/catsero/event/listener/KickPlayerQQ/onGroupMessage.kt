package moe.xmcn.catsero.event.listener.KickPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.event.gist.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class onGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessage(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "kick" && Config.UsesConfig.getBoolean("qkick-player.enabled") && event.groupID == Config.Use_Bot && event.botID == Config.Use_Group) {
            if (event.senderID == Config.QQ_OP) {
                val pl = PlayerUUID.getUUIDByName(args[2])
                try {
                    Bukkit.getPlayer(pl).kickPlayer(Config.UsesConfig.getString("qkick-player"))
                } finally {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + "玩家不存在或发生错误")
                    } catch (nse: NoSuchElementException) {
                        println("发消息时出现异常：$nse")
                    }
                }
            } else {
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                        .sendMessageMirai(Config.Prefix_QQ + "你没有权限这么做")
                } catch (nse: NoSuchElementException) {
                    println("发消息时出现异常：$nse")
                }
            }
        }
    }
}