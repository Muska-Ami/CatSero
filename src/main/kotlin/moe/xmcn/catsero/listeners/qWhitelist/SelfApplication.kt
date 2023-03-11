/*
 * CatSero v2
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero.listeners.qWhitelist

import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.utils.Envrionment
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.MessageSender
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.geysermc.floodgate.api.FloodgateApi
import java.util.regex.Pattern

class SelfApplication(
    private val enable: Boolean = Configuration.USES_CONFIG.QWHITELIST.ENABLE,
    private val bot: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.BOT,
    private val group: String = Configuration.USES_CONFIG.QWHITELIST.MIRAI.GROUP
) : Listener {

    private var prefix: String? = null

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
                //println(code)

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
                        //println(code)
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

    private fun run(nam: String, code: Long) {
        try {
            var name = nam
            if (Envrionment.Depends.Floodgate) {
                // 互通服优化
                // 鬼知道我为什么要导入 Floodgate API 然后又去读一遍 Floodgate 的配置
                val floodgateApi = FloodgateApi.getInstance()
                prefix = floodgateApi.playerPrefix

                if (
                    prefix != null
                    && prefix?.let { it1 -> name.startsWith(it1) } == true
                    && Configuration.Interface.FLOODGATE.REPLACE_SPACES
                ) {
                    name = name.replace(" ", "_")
                    MessageSender.sendGroup(
                        Configuration.I18N.QQ.USE.QWHITELIST.REPLACE_SPACE_SUCCESS.replace(
                            "%name%",
                            name
                        ), bot, group
                    )
                }
            }

            val regex = Configuration.USES_CONFIG.QWHITELIST.REGEX
            val match = if (Envrionment.Depends.Floodgate)
                Pattern.matches(regex, prefix?.let { name.replaceFirst(it, "") })
            else
                Pattern.matches(regex, name)
            if (match)
                if (
                    !WhiteListDatabase.getNameList().contains(name)
                ) {
                    // 1Q1号
                    if (Configuration.USES_CONFIG.QWHITELIST.A_QQ_ONLY_AN_ACCOUNT)
                        if (!WhiteListDatabase.getCodeList().contains(code))
                            run2(name, code)
                        else
                            MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.AQOAA_ERROR_REPEAT, bot, group)
                    else
                        run2(name, code)
                } else
                    MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_REPEAT, bot, group)
            else
                MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_NAME_NOT_ALLOWED, bot, group)

        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

    private fun run2(name: String, code: Long) {
        try {
            if (WhiteListDatabase.insertList(name, code))
                MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_SUCCESS, bot, group)
            else
                MessageSender.sendGroup(Configuration.I18N.QQ.USE.QWHITELIST.ADD_ERROR_SQL, bot, group)

        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
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