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
            if (
                enable
                && !WhiteListDatabase.getNameList().contains(e.name)
            )
                allow = false
            try {
                if (
                    Configuration.USES_CONFIG.QWHITELIST.CHECK_IF_ON_GROUP
                    && MiraiBot.getBot(Configuration.Interface.getBotCode(bot))
                        .getGroup(Configuration.Interface.getGroupCode(group))
                        .getMember(WhiteListDatabase.getCode(e.name)) == null
                )
                    allow = false
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