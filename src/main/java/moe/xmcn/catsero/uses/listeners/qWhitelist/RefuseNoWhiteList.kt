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
package moe.xmcn.catsero.uses.listeners.qWhitelist

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.Configuration
import moe.xmcn.catsero.I18n
import moe.xmcn.catsero.utils.Logger
import moe.xmcn.catsero.utils.WhiteListDatabase
import org.bukkit.ChatColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

class RefuseNoWhiteList(
    private val ThisID: String = Configuration.USESID.QWHITELIST,
    private val enable: Boolean = Configuration.getUses().getBoolean(
        Configuration.buildYaID(
            ThisID, ArrayList<String>(
                listOf(
                    "enable"
                )
            )
        )
    ),
    private val bot: String = Configuration.getUseMiraiBot(ThisID),
    private val groups: List<String> = Configuration.getUseMiraiGroup(ThisID)
) : Listener {

    private val i18n = I18n()

    @EventHandler
    fun onPlayerPreLogin(e: AsyncPlayerPreLoginEvent) {
        try {
            var allow = true
            groups.forEach { group ->
                run {


                    if (
                        enable
                        && !WhiteListDatabase.getNameList().contains(e.name)
                    )
                        allow = false
                    try {
                        if (
                            Configuration.getUses().getBoolean(
                                Configuration.buildYaID(
                                    ThisID, ArrayList<String>(
                                        listOf(
                                            "check-if-on-group"
                                        )
                                    )
                                )
                            )
                            && MiraiBot.getBot(Configuration().getBotCode(bot))
                                .getGroup(Configuration().getGroupCode(group))
                                .getMember(WhiteListDatabase.getCode(e.name)) == null
                        )
                            allow = false
                    } catch (exc: Exception) {
                        e.disallow(
                            AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                            ChatColor.translateAlternateColorCodes(
                                '&',
                                i18n.getI18n(
                                    ArrayList(
                                        listOf(
                                            "minecraft", "use", "qwhitelist", "check-if-in-group-error"
                                        )
                                    )
                                )
                            )
                        )
                        Logger.logCatch(exc)
                    }
                    if (!allow) {
                        e.disallow(
                            AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                            ChatColor.translateAlternateColorCodes(
                                '&',
                                i18n.getI18n(
                                    ArrayList(
                                        listOf(
                                            "minecraft", "use", "qwhitelist", "no-whitelist"
                                        )
                                    )
                                )
                            )
                        )
                    }
                }
            }
        } catch (ex: Exception) {
            Logger.logCatch(ex)
        }
    }

}