package moe.xmcn.catsero.listeners.qwhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class SelfApplication(
    private val enable: Boolean = Configuration.USES_CONFIG.QWHITELIST.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
) : Listener {

    @EventHandler
    fun onGroupMessage(e: MiraiGroupMessageEvent) {
        try {
            if (
                enable
                && Configuration.USES_CONFIG.QWHITELIST.SELF_APPLICATION.ENABLE
                && e.botID == Configuration.Interface.getBotCode(bot)
                && e.groupID == Configuration.Interface.getGroupCode(group)
            ) {
                val sf = Configuration.USES_CONFIG.QWHITELIST.SELF_APPLICATION.FORMAT.split("%name%")

                val code = e.senderID

                if (sf.size == 1) {
                    // 情况1: 占位符后无字符(串)
                    if (
                        e.message.startsWith(sf[0])
                    ) {
                        val playerName = e.message
                            .replaceFirst(sf[0], "")

                        //println(playerName)
                        run(playerName, code)
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
                        run(playerName, code)
                    }
                } else
                /*
                还有其他情况 -> 设置了多个占位符？懒得写（
                多了就用不了呗，又不会报错
                反正我又不修（腐竹的问题不能怪我XD
                才怪
                 */
                    MessageSender.sendGroup(Configuration.I18N.QQ.COMMAND.CONFIG_ERROR, bot, group)
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

    private fun run(name: String, code: Long) {
        if (
            !WhiteListDatabase.getNameList().contains(name)
        ) {
            // 1Q1号
            if (Configuration.USES_CONFIG.QWHITELIST.SELF_APPLICATION.A_QQ_ONLY_AN_ACCOUNT
            )
                if (!WhiteListDatabase.getCodeList().contains(code))
                    run2(name, code)
                else
                    MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.AQOAA_ERROR_REPEAT, bot, group)
            else
                run2(name, code)
        } else
            MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_REPEAT, bot, group)
    }

    private fun run2(name: String, code: Long) {

        if (WhiteListDatabase.insertList(name, code))
            MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_SUCCESS, bot, group)
        else
            MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_SQL, bot, group)

    }

    /*
    没有replaceLast(String, String)方法差评，自己写一个
    String.replaceLast(strToReplace: String, replaceWithThis: String)
     */
    private fun String.replaceLast(strToReplace: String, replaceWithThis: String): String {
        return this.replaceFirst(
            ("(?s)" + strToReplace + "(?!.*?" + strToReplace
                    + ")").toRegex(), replaceWithThis
        )
    }
}