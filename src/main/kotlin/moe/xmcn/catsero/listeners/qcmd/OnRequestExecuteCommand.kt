package moe.xmcn.catsero.listeners.qcmd

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiFriendMessageEvent
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class OnRequestExecuteCommand : Listener {

    @EventHandler
    fun group(e: MiraiGroupMessageEvent) {
        val sender = e.senderID
        val message = e.message
    }

    @EventHandler
    fun friend(e: MiraiFriendMessageEvent) {
        val sender = e.senderID
        val message = e.message
    }

    private fun run() {

    }

    private fun executeCommand(command: String): String? {
        return null
    }

}