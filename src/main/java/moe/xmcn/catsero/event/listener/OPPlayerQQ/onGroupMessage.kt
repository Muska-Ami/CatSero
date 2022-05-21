package moe.xmcn.catsero.event.listener.OPPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.event.gist.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.permissions.ServerOperator

class onGroupMessage : Listener {

    @EventHandler
    fun onMiraiGroupMessage(event: MiraiGroupMessageEvent) {
        Config.Use_Bots.forEach { bot ->
            Config.Use_Groups.forEach { group ->
                Config.QQ_OPs.forEach { qqop ->
                    val message = event.message
                    val args = message.split(" ")
                    if (args[0] == "catsero" && args[1] == "setop" && Config.UsesConfig.getBoolean("qop-player.enabled") && event.groupID == bot && event.botID == bot) {
                        if (event.senderID == qqop) {
                            val pl = PlayerUUID.getUUIDByName(args[2])
                            val plname: ServerOperator = Bukkit.getPlayer(pl)
                            val isOp = plname.isOp
                            if (isOp) {
                                try {
                                    MiraiBot.getBot(bot).getGroup(group)
                                        .sendMessageMirai(Config.Prefix_QQ + "已经是管理员了！")
                                } catch (nse: NoSuchElementException) {
                                    println("发消息时出现异常：$nse")
                                }
                            } else {
                                plname.isOp = true
                                try {
                                    MiraiBot.getBot(bot).getGroup(group)
                                        .sendMessageMirai(Config.Prefix_QQ + "已添加新的管理员")
                                } catch (nse: NoSuchElementException) {
                                    println("发消息时出现异常：$nse")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}