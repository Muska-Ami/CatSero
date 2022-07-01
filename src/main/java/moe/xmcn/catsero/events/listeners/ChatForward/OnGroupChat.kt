package moe.xmcn.catsero.events.listeners.ChatForward

import me.dreamvoid.miraimc.api.MiraiMC
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.events.gists.PlayerUUID
import moe.xmcn.catsero.utils.Config
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.text.DateFormat
import java.util.*

class OnGroupChat : Listener {

    @EventHandler
    fun onGroupChat(event: MiraiGroupMessageEvent) {
        if (Config.UsesConfig.getBoolean("forward-chat.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            isEnabled(event)
        }
    }

    private fun isEnabled(event: MiraiGroupMessageEvent) {
        val groupid = event.groupID
        val groupname = event.groupName
        val senderid = event.senderID
        val sendername = event.senderNameCard
        val emsg = event.message
        val sendtim = event.time

        val df = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA)
        val sendtime = df.format(sendtim)

        if (Config.UsesConfig.getBoolean("forward-chat.use-bind")) {
            var message = Config.UsesConfig.getString("forward-chat.format.to-mc")
            message = (if (Config.UsesConfig.getBoolean("forward-chat.use-bind")) {
                message.replace("%groupcode%", groupid.toString())
                    .replace("%groupname%", groupname)
                    .replace("%sendercode%", senderid.toString())
                    .replace("%sendername%", PlayerUUID.getNameByUUID(MiraiMC.getBinding(senderid)))
                    .replace("%message%", emsg)
                    .replace("%time%", sendtime)
            } else {
                message.replace("%groupcode%", groupid.toString())
                    .replace("%groupname%", groupname)
                    .replace("%sendercode%", senderid.toString())
                    .replace("%sendername%", sendername)
                    .replace("%message%", emsg)
                    .replace("%time%", sendtime)
            }).toString()
            message = (if (Config.UsesConfig.getBoolean("forward-chat.clean-colorcode")) {
                message.replace("§1", "")
                    .replace("§2", "")
                    .replace("§3", "")
                    .replace("§4", "")
                    .replace("§5", "")
                    .replace("§6", "")
                    .replace("§7", "")
                    .replace("§8", "")
                    .replace("§9", "")
                    .replace("§0", "")
                    .replace("§a", "")
                    .replace("§b", "")
                    .replace("§c", "")
                    .replace("§d", "")
                    .replace("§e", "")
                    .replace("§f", "")
                    .replace("§k", "")
                    .replace("§l", "")
                    .replace("§m", "")
                    .replace("§n", "")
                    .replace("§o", "")
                    .replace("§r", "")
            } else {
                message
            }).toString()
            val firstzf = message.first()
            if (Config.UsesConfig.getBoolean("forward-chat.prefix.enabled") && firstzf.toString() == Config.UsesConfig.getString(
                    "forward-chat.prefix.format.to-mc"
                )
            ) {
                Bukkit.broadcastMessage(message)
            } else {
                Bukkit.broadcastMessage(message)
            }
        }
    }

}