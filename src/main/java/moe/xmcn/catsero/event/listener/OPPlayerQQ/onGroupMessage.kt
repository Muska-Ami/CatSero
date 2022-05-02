package moe.xmcn.catsero.event.listener.OPPlayerQQ

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.Config
import moe.xmcn.catsero.Main
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.permissions.ServerOperator
import org.bukkit.plugin.Plugin

class onGroupMessage : Listener {

    val plugin: Plugin = Main.getPlugin(Main::class.java)

    @EventHandler
    fun onMiraiGroupMessage(event: MiraiGroupMessageEvent) {
        val message = event.message
        val args = message.split(" ")
        if (args[0] == "catsero" && args[1] == "setop" && event.groupID == Config.Use_Group) {
            if (event.senderID == Config.QQ_OP) {
                val plname: ServerOperator = args[2] as Player
                val isOp = plname.isOp
                if (isOp) {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Config.Prefix_QQ + "已经是管理员了！")
                    } catch (nse: NoSuchElementException) {
                        println("发消息时出现异常：$nse")
                    }
                }
            }
        }
    }
}