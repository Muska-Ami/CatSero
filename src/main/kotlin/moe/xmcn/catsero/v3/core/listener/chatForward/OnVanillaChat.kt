package moe.xmcn.catsero.v3.core.listener.chatForward

import moe.xmcn.catsero.v3.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class OnVanillaChat : Listener {

    val config = Configuration.getUsesConfig()
    val bot = Configuration.getBot(config.getArray("chat-forward . mirai")?.get(0).toString())
    val group = Configuration.getBot(config.getArray("chat-forward . mirai")?.get(1).toString())

    @EventHandler
    fun onAsyncChatEvent(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message


    }

}