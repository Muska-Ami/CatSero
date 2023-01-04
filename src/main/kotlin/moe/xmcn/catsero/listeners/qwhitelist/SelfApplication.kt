package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class SelfApplication : Listener {

    @EventHandler
    fun onGroupMessage(e: MiraiGroupMessageEvent) {
        try {
            if (
                Configuration.USES_CONFIG.QWHITELIST.ENABLE
                && Configuration.USES_CONFIG.QWHITELIST.SELF_APPLICATION.ENABLE
                && e.botID == Configuration.Interface.getBotCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.BOT)
                && e.groupID == Configuration.Interface.getGroupCode(Configuration.USES_CONFIG.GET_ONLINE_LIST.MIRAI.GROUP)
            ) {
                val sf = Configuration.USES_CONFIG.QWHITELIST.SELF_APPLICATION.format.split("%name%")

                if (sf.size == 1) {
                    // 情况1: 占位符后无字符(串)
                    if (
                        e.message.startsWith(sf[0])
                    ) {
                        val playerName = e.message
                            .replaceFirst(sf[0], "")

                        //println(playerName)
                        run(playerName)
                    }
                } else if (sf.size == 2) {
                    // 情况2: 占位符后有字符(串)
                    if (
                        e.message.startsWith(sf[0])
                        && e.message.endsWith(sf[1])
                    ) {
                        val playerName = e.message
                            .replaceFirst(sf[0], "")
                            .replaceLast(sf[1], "")

                        //println(playerName)
                        run(playerName)
                    }
                } else
                /*
                还有其他情况 -> 设置了多个占位符？懒得写（
                多了就用不了呗，又不会报错
                反正我又不修（腐竹的问题不能怪我XD
                才怪
                 */
                    MessageSender.sendGroup(
                        Configuration.I18N.QQ.COMMAND.CONFIG_ERROR,
                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                        Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                    )
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

    private fun run(name : String) {
        if (!WhiteListDatabase().list.contains(name)) {
            if (WhiteListDatabase().insertList(name))
                MessageSender.sendGroup(
                    Configuration.I18N.QQ.USE.QWHITELIST.ADD_SUCCESS,
                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                )
            else
                MessageSender.sendGroup(
                    Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_SQL,
                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                    Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
                )
        } else
            MessageSender.sendGroup(
                Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_REPEAT,
                Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
                Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
            )
    }

    /**
     * 没有replaceLast(String, String)方法差评，自己写一个
     * String.replaceLast(strToReplace: String, replaceWithThis: String)
     */
    private fun String.replaceLast(strToReplace: String, replaceWithThis: String): String {
        return this.replaceFirst(
            ("(?s)" + strToReplace + "(?!.*?" + strToReplace
                    + ")").toRegex(), replaceWithThis
        )
    }
}