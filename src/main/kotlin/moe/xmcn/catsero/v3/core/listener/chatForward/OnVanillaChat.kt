package moe.xmcn.catsero.v3.core.listener.chatForward

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class OnVanillaChat: Listener {

    @EventHandler
    fun onAsyncChatEvent(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message


    }

}