package moe.xmcn.catsero.v3.core.listener.chatForward

import me.arasple.mc.trchat.api.event.TrChatEvent
import moe.xmcn.catsero.v3.CatSero
import moe.xmcn.catsero.v3.Configuration
import moe.xmcn.catsero.v3.I18n
import moe.xmcn.catsero.v3.core.timer.chatForward.Filter
import moe.xmcn.catsero.v3.util.Logger
import moe.xmcn.catsero.v3.util.MessageSender
import moe.xmcn.catsero.v3.util.PAPI
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.scheduler.BukkitRunnable
import java.text.SimpleDateFormat
import java.util.*

class OnTrChatChat: Listener {

    private val config = Configuration.usesConfig
    private val bot = Configuration.getBot(config.getArray("chatForward . mirai")?.get(0).toString())
    private val group = Configuration.getGroup(config.getArray("chatForward . mirai")?.get(1).toString())

    @EventHandler
    fun onTrChatEvent(event: TrChatEvent) {
        try {
            if (!event.isCancelled) {
                val player = event.session.player
                val message = event.message

                val formatter = SimpleDateFormat(
                    config.getString("general . timeFormat")
                        ?: "HH:mm:ss, yyyy-MM-dd"
                )
                val date = Date(System.currentTimeMillis())

                val format = I18n.getFormat("chatForward.to-qq")

                var res = format.replace("%name%", player.name)
                    .replace("%message%", message)
                    .replace("%time%", formatter.format(date))
                res = PAPI.transPlaceholders(res, player)

                Filter.fullWords.forEach {
                    res = res.replace(it, Configuration.usesConfig.getString("chatForward.filter . replace") ?: "**")
                }

                object : BukkitRunnable() {
                    override fun run() {
                        MessageSender.sendGroupMessage(res, bot ?: 0, group ?: 0)
                    }
                }.runTaskAsynchronously(CatSero.INSTANCE)
            }
        } catch (e: Exception) {
            Logger.catchEx(e)
        }
    }

}