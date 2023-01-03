package moe.xmcn.catsero.listeners.qwhitelist

import moe.xmcn.catsero.Configuration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class RefuseNoWhiteList : Listener {

    @EventHandler
    fun onPlayerPreLogin(e : AsyncPlayerPreLoginEvent) {
        if (
            Configuration.USES_CONFIG.QWHITELIST.ENABLE
            && !Configuration.CFI.whitelist_list.getStringList("list").contains(e.name)
        ) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, Configuration.I18N.MINECRAFT.USE.QWHITELIST.NO_WHITELIST);
        }
    }

}