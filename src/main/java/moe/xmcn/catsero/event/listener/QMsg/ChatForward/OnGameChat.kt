package moe.xmcn.catsero.event.listener.QMsg.ChatForward

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.Main
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.plugin.Plugin
import java.io.File

class OnGameChat: Listener {
    var plugin: Plugin = Main.getPlugin(Main::class.java)
    var usc = File(plugin.dataFolder, "usesconfig.yml")
    var usesconfig: FileConfiguration = YamlConfiguration.loadConfiguration(usc)
    @EventHandler
    fun onGameChat(event: AsyncPlayerChatEvent) {
        if (usesconfig.getBoolean("qmsg.forward-chat.enabled")) {
            var playermessage = event.message
            val playername = event.player
            val message = usesconfig.getString("qmsg.forward-chat.format.to-qq")
            message.replace("%player%", playername.toString())
                    .replace("%message%", playermessage)

            val bot = plugin.config.getLong("qbgset.bot")
            val group = plugin.config.getLong("qbgset.group")

            if (usesconfig.getBoolean("qmsg.forward-chat.clean-colorcode")) {
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

                val firstzf = message.first()
                if (usesconfig.getBoolean("qmsg.forward-chat.prefix.enabled") && firstzf.equals(usesconfig.getString("qmsg.forward-chat.prefix.format.to-qq"))) {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                    } catch (nse: NoSuchElementException) {
                        println("发送消息时出现异常:\n$nse")
                    }
                } else {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                    } catch (nse: NoSuchElementException) {
                        println("发送消息时出现异常:\n$nse")
                    }
                }
            } else {
                val firstzf = message.first()
                if (usesconfig.getBoolean("qmsg.forward-chat.prefix.enabled") && firstzf.equals(usesconfig.getString("qmsg.forward-chat.prefix.format.to-qq"))) {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                    } catch (nse: NoSuchElementException) {
                        println("发送消息时出现异常:\n" + nse)
                    }
                } else {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                    } catch (nse: NoSuchElementException) {
                        println("发送消息时出现异常:\n" + nse)
                    }
                }
            }

        }
    }
}

