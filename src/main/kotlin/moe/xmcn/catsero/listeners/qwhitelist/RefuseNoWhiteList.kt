package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class RefuseNoWhiteList : Listener {

    @EventHandler
    fun onPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        var allow = true

        try {
            if (
                Configuration.USES_CONFIG.QWHITELIST.ENABLE
                && !WhiteListDatabase.getNameList().contains(e.name)
            ) {
                if (
                    Configuration.USES_CONFIG.QWHITELIST.CHECK_IF_ON_GROUP
                    && MiraiBot.getBot(Configuration.Interface.getBotCode(Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT))
                        .getGroup(Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP))
                        .getMember(WhiteListDatabase.getCode(e.name)) != null
                )
                    allow = false
            }
            if (!allow) {
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        Configuration.I18N.MINECRAFT.USE.QWHITELIST.NO_WHITELIST
                    )
                )
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}