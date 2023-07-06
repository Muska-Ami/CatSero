package moe.xmcn.catsero.v3.core.listener.chatForward

import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.I18n
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import moe.xmcn.catsero.v3.util.MessageSender
import moe.xmcn.catsero.v3.util.PAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.scheduler.BukkitRunnable
import java.text.SimpleDateFormat
import java.util.*

class OnVanillaChat : Listener {

    private val config = Configuration.usesConfig
    private val bot = Configuration.getBot(config.getArray("chatForward . mirai")?.get(0).toString())
    private val group = Configuration.getBot(config.getArray("chatForward . mirai")?.get(1).toString())

    @EventHandler
    fun onAsyncChatEvent(event: AsyncPlayerChatEvent) {
        if (!event.isCancelled) {
            val player = event.player
            val message = event.message

            val formatter = SimpleDateFormat(
                config.getString("general . time-format")
                    ?: "HH:mm:ss, yyyy-MM-dd"
            )
            val date = Date(System.currentTimeMillis())

            val format = I18n.getFormat("chatForward.to-qq")

            var res = format.replace("%player%", player.name)
                .replace("%message%", message)
                .replace("%time%", formatter.format(date))
            res = PAPI.transPlaceholders(res, player)

            Filter.fullWords.forEach {
                res = res.replace(it, Configuration.usesConfig.getString("chatForward.filter . replace ")?: "**")
            }

            object : BukkitRunnable() {
                override fun run() {
                    MessageSender.sendGroupMessage(res, bot?: 0, group?: 0)
                }
            }.runTaskAsynchronously(CatSero.INSTANCE)
        }
    }

}