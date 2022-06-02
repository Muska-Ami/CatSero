package moe.xmcn.catsero.events.listeners.Punycode

import me.dreamvoid.miraimc.api.MiraiBot
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent
import moe.xmcn.catsero.utils.Config
import moe.xmcn.catsero.utils.Punycode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnGroupMessage : Listener {
    @EventHandler
    fun onMiraiGroupMessageEvent(event: MiraiGroupMessageEvent) {
        if (Config.UsesConfig.getBoolean("punycode.enabled") && event.groupID == Config.Use_Group && event.botID == Config.Use_Bot) {
            val msg = event.message
            val args = msg.split(" ")
            if (args[0] == "catsero" && args[1] == "punycode") {
                if (args[3] == "urlmode") {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Punycode.encodeURL(args[2]))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                } else {
                    try {
                        MiraiBot.getBot(Config.Use_Bot).getGroup(Config.Use_Group)
                            .sendMessageMirai(Punycode.encode(args[2]))
                    } catch (nse: NoSuchElementException) {
                        Config.plugin.logger.warning(
                            Config.getMsgByMsID("general.send-message-qq-error")
                                .replace("%error%", nse.toString() + nse.stackTrace)
                        )
                    }
                }
            }
        }
    }
}