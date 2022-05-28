package moe.xmcn.catsero.event.listener.QMsg.ChatForward

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.text.DateFormat
import java.util.*

class OnGroupChat : Listener {

    @EventHandler
    fun onGroupChat(event: MiraiGroupMessageEvent) {

        if (Config.UsesConfig.getBoolean("qmsg.forward-chat.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            val groupid = event.groupID
            val groupname = event.groupName
            val senderid = event.senderID
            val sendername = event.senderNameCard
            val emsg = event.message
            val sendtim = event.time

            val df = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA)
            val sendtime = df.format(sendtim)

            var message = Config.UsesConfig.getString("qmsg.forward-chat.format.to-mc")
            message = message.replace("%groupcode%", groupid.toString())
                .replace("%groupname%", groupname)
                .replace("%sendercode%", senderid.toString())
                .replace("%sendername%", sendername)
                .replace("%message%", emsg)
                .replace("%time%", sendtime)
            val firstzf = message.first()
            if (Config.UsesConfig.getBoolean("qmsg.forward-chat.prefix.enabled") && firstzf.toString() == Config.UsesConfig.getString(
                    "qmsg.forward-chat.prefix.format.to-mc"
                )
            ) {
                Bukkit.broadcastMessage(message)
            } else {
                Bukkit.broadcastMessage(message)
            }
        }
    }
}