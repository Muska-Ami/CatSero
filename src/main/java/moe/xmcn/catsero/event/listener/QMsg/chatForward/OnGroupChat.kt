package moe.xmcn.catsero.event.listener.QMsg.chatForward

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.Main
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import java.io.File
import java.text.DateFormat
import java.util.*

class OnGroupChat: Listener {

    var plugin: Plugin = Main.getPlugin(Main::class.java)
    var usc = File(plugin.dataFolder, "usesconfig.yml")
    var usesconfig: FileConfiguration = YamlConfiguration.loadConfiguration(usc)

    @EventHandler
    fun onGroupChat(event: MiraiGroupMessageEvent) {
        if (usesconfig.getBoolean("qmsg.chat-forward.enabled")) {
            val groupid = event.groupID
            val groupname = event.groupName
            val senderid = event.senderID
            val sendername = event.senderNameCard
            val emsg = event.message
            val sendtim = event.time

            val df = DateFormat.getDateInstance(DateFormat.FULL, Locale.CHINA)
            val sendtime = df.format(sendtim)

            var message = usesconfig.getString("qmsg.chat-forward.format.to-mc")
            message = message.replace("%groupcode%", groupid.toString())
                .replace("%groupname%", groupname)
                .replace("%sendercode%", senderid.toString())
                .replace("%sendername%", sendername)
                .replace("%message%", emsg)
                .replace("%time%", sendtime)
            Bukkit.broadcastMessage(message)
        }
    }
}