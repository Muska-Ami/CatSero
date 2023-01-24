package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class RefuseNoWhiteList(
    private val enable: Boolean = Configuration.USES_CONFIG.QWHITELIST.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        var allow = true

        try {
            try {
                if (enable) {
                    if (!WhiteListDatabase.getNameList().contains(e.name))
                        allow = false
                    if (
                        Configuration.USES_CONFIG.QWHITELIST.CHECK_IF_ON_GROUP
                        && MiraiBot.getBot(Configuration.Interface.getBotCode(bot))
                            .getGroup(Configuration.Interface.getGroupCode(group))
                            .getMember(WhiteListDatabase.getCode(e.name)) == null
                    )
                        allow = false
                }
            } catch (exc: Exception) {
                e.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    ChatColor.translateAlternateColorCodes(
                        '&',
                        Configuration.I18N.MINECRAFT.USE.QWHITELIST.CHECK_IF_IN_GROUP_ERROR
                    )
                )
                Logger.logCatch(exc)
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