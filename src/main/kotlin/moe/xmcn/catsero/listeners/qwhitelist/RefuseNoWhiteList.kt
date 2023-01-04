package moe.xmcn.catsero.listeners.qwhitelist

import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.WhiteListDatabase
import moe.xmcn.catsero.utils.Logger
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class RefuseNoWhiteList : Listener {

    @EventHandler
    fun onPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        try {
            if (
                Configuration.USES_CONFIG.QWHITELIST.ENABLE
                && !WhiteListDatabase().list.contains(e.name)
            ) {
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.QWHITELIST.NO_WHITELIST)
                )
            }
        } catch (ex : Exception) {
            Logger.logCatch(ex)
        }
    }

}