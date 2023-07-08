package moe.xmcn.catsero.v3.core.listener.joinQuitForward

import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.I18n
import moe.xmcn.catsero.v3.util.Logger
import moe.xmcn.catsero.v3.util.MessageSender
import moe.xmcn.catsero.v3.util.PAPI
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class OnPlayerJoin: Listener {

    private val config = Configuration.usesConfig!!
    private val bot = Configuration.getBot(config.getArray("joinQuitForward . mirai")?.get(0).toString())
    private val group = Configuration.getGroup(config.getArray("joinQuitForward . mirai")?.get(1).toString())

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        try {
            val player = event.player

            var res: String
            // Copy from event.joinMessage()
            if (config.getBoolean("joinQuitForward . copyMessage") == false) {
                val format = I18n.getFormat("joinQuitForward.join")
                res = format.replace("%name%", player.name)
                res = PAPI.transPlaceholders(res, player)
            } else {
                res = ChatColor.stripColor(event.joinMessage)
            }

            object : BukkitRunnable() {
                override fun run() {
                    MessageSender.sendGroupMessage(res, bot ?: 0, group ?: 0)
                }
            }
        } catch (e: Exception) {
            Logger.catchEx(e)
        }
    }

}