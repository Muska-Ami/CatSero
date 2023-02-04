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
package moe.xmcn.catsero.listeners.chatforward;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class OnCommonChatToQQ implements Listener {

    private final boolean enable;
    private final String bot;
    private final String group;
    private String message;

    public OnCommonChatToQQ() {
        this.enable = Configuration.USES_CONFIG.CHAT_FORWARD.ENABLE;
        this.bot = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.BOT;
        this.group = Configuration.USES_CONFIG.CHAT_FORWARD.MIRAI.GROUP;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        if (!e.isCancelled()) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    try {
                        if (enable) {
                            message = e.getMessage();

                            // Filter
                            if (Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.ENABLE) {
                                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.ALL_TO_QQ().forEach(it -> message = message.replace(it, Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.REPLACE));
                                run1(e, message);
                /*
                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.VIA.TO_QQ.forEach(it -> {
                    if (message.contains(it)) filter = true;
                });
                if (!filter)
                    run(e, message);
                else
                    filter = false;

                 */
                            } else
                                run1(e, message);
                        }
                    } catch (
                            Exception ex) {
                        Logger.logCatch(ex);
                    }
                }
            }.runTaskAsynchronously(Configuration.plugin);
        }
    }

    private void run1(AsyncPlayerChatEvent e, String message) {
        try {
            String format = Configuration.USES_CONFIG.CHAT_FORWARD.FORMAT.TO_QQ;

            // 检查消息是否存在mirai码
            if (
                    !Configuration.USES_CONFIG.CHAT_FORWARD.ALLOW_MIRAICODE
                            && !message.contains("[mirai:")
            ) {
                // 清理样式代码
                if (Configuration.USES_CONFIG.CHAT_FORWARD.CLEAN_STYLECODE.TO_QQ)
                    message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));

                format = format.replace("%message%", message)
                        .replace("%name%", e.getPlayer().getName())
                        .replace("%display_name%", e.getPlayer().getDisplayName());

                // 权限
                if (e.getPlayer().isOp())
                    format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.ADMIN);
                else
                    format = format.replace("%sender_permission%", Configuration.I18N.MINECRAFT.CALL.PLAYER);

                if (Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.ENABLE) {
                    if (message.startsWith(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_QQ))
                        MessageSender.sendGroup(format.replaceFirst(Configuration.USES_CONFIG.CHAT_FORWARD.HEADER.PREFIX.TO_QQ, ""), bot, group);
                } else
                    MessageSender.sendGroup(format, bot, group);
            } else
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.USE.CHAT_FORWARD.CASE_MIRAICODE));

        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
