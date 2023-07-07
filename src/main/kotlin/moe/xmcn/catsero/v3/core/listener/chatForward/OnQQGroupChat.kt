package moe.xmcn.catsero.v3.core.listener.chatForward

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.I18n
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.text.SimpleDateFormat
import java.util.*

class OnQQGroupChat: Listener {

    private val config = Configuration.usesConfig
    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        val groupName = event.groupName
        val groupId = event.groupID
        val senderName = if (event.senderNameCard != "")
            event.senderNameCard
        else
            event.senderName
        val senderId = event.senderID
        val message = event.message

        val formatter = SimpleDateFormat(
            config.getString("general . timeFormat")
                ?: "HH:mm:ss, yyyy-MM-dd"
        )
        val date = Date(System.currentTimeMillis())

        val format = I18n.getFormat("chatForward.to-mc")

        var res = format.replace("%sender_name%", senderName)
            .replace("%sender_id%", senderId.toString())
            .replace("%group_name%", groupName)
            .replace("%group_id%", groupId.toString())
            .replace("%message%", message)
            .replace("%time%", formatter.format(date))

        Filter.fullWords.forEach {
            res = res.replace(it, Configuration.usesConfig.getString("chatForward.filter . replace")?: "**")
        }
        if (CatSero.INSTANCE.server.onlinePlayers.isNotEmpty())
            CatSero.INSTANCE.server.broadcastMessage(ChatColor.translateAlternateColorCodes('&', res))
    }

}