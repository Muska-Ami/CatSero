package moe.xmcn.catsero.event.listener.PunyCode

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import moe.xmcn.catsero.utils.Punycode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class OnGroupMessage: Listener {
    @EventHandler
    fun onGroupMessageEvent(event: MiraiGroupMessageEvent) {
        if (Config.UsesConfig.getBoolean("punycode.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            val msg = event.message
            val args = msg.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (args[0].equals("catsero", ignoreCase = true) && args[1].equals("punycode", ignoreCase = true)) {
                try {
                    MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group).sendMessageMirai(Punycode.encode(args[2]))
                } catch (nse: NoSuchElementException) {
                    println("发送消息时发生异常:\n$nse" + nse.stackTrace)
                }
            }
        }
    }
}