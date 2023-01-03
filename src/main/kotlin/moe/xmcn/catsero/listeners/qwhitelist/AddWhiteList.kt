package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.QPS
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class AddWhiteList : Listener {

    @EventHandler
    fun onGroupMessage(e : MiraiGroupMessageEvent) {
        val args = QPS.parse(e.message)
        if (args != null) {
            if (
                Configuration.USES_CONFIG.QWHITELIST.ENABLE
                && args[0].equals("whitelist", true)
                && args[1].equals("add", true)
                && e.botID == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                && e.groupID == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)
            ) {

            }
        }
    }

}