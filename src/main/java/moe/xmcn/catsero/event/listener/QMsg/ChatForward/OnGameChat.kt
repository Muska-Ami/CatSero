package moe.xmcn.catsero.event.listener.QMsg.ChatForward

import me.clip.placeholderapi.PlaceholderAPI
import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.utils.Config
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class OnGameChat : Listener {

    @EventHandler
    fun onAsyncGameChat(event: AsyncPlayerChatEvent) {
        Config.Use_Bots.forEach { bot ->
            Config.Use_Groups.forEach { group ->
                if (Config.UsesConfig.getBoolean("qmsg.forward-chat.enabled") && Config.UsesConfig.getBoolean("qmsg.chat-forward.async")) {
                    val playermessage = event.message
                    val playername = event.player.displayName
                    var message = Config.UsesConfig.getString("qmsg.forward-chat.format.to-qq")
                    message = message.replace("%player%", playername.toString())
                        .replace("%message%", playermessage)
                    message = PlaceholderAPI.setBracketPlaceholders(event.player, message)

                    if (Config.UsesConfig.getBoolean("qmsg.forward-chat.clean-colorcode")) {
                        message = message.replace("§1", "")
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
                        if (Config.UsesConfig.getBoolean("qmsg.forward-chat.prefix.enabled") && firstzf.toString() == Config.UsesConfig.getString(
                                "qmsg.forward-chat.prefix.format.to-qq"
                            )
                        ) {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        } else {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        }
                    } else {
                        val firstzf = message.first()
                        if (Config.UsesConfig.getBoolean("qmsg.forward-chat.prefix.enabled") && firstzf.toString() == Config.UsesConfig.getString(
                                "qmsg.forward-chat.prefix.format.to-qq"
                            )
                        ) {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        } else {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message)
                            } catch (nse: NoSuchElementException) {
                                println("发送消息时出现异常:\n$nse" + nse.stackTrace)
                            }
                        }
                    }
                }
            }
        }
    }
}

