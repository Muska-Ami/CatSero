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
package moe.xmcn.catsero.uses.listeners.chatForward;

import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Filter;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnCommonChatToQQ implements Listener {

    private final I18n i18n = new I18n();
    private final String ThisID = Configuration.USESID.CHAT_FORWARD;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;
    private String message;
    private String mst;

    public OnCommonChatToQQ() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
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
                            if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                    "filter", "enable"
                            ))))) {
                                new Filter.CHAT_FORWARD().getQQWords().forEach(it -> message = message.replace(it, Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                        "filter", "replace"
                                ))))));
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
            }.runTaskAsynchronously(CatSero.INSTANCE);
        }
    }

    private void run1(AsyncPlayerChatEvent e, String message) {
        mst = message;
        groups.forEach(group -> {
            try {
                String format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "format", "to-qq"
                ))));

                // 检查消息是否存在mirai码
                if (
                        !Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                                "allow-miraicode"
                        ))))
                                && !mst.contains("[mirai:")
                ) {
                    // 清理样式代码
                    if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                            "clean-stylecode", "to-qq"
                    )))))
                        mst = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', mst));

                    format = format.replace("%message%", mst)
                            .replace("%name%", e.getPlayer().getName())
                            .replace("%display_name%", e.getPlayer().getDisplayName());

                    // 权限
                    if (e.getPlayer().isOp())
                        format = format.replace("%sender_permission%", i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "call", "admin"
                        ))));
                    else
                        format = format.replace("%sender_permission%", i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "call", "player"
                        ))));

                    // Header
                    if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                            "header", "enable"
                    ))))) {
                        if (mst.startsWith(Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                "header", "prefix", "to-qq"
                        ))))))
                            MessageSender.sendGroup(format.replaceFirst(Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                    "header", "prefix", "to-qq"
                            )))), ""), bot, group);
                    } else
                        MessageSender.sendGroup(format, bot, group);
                } else
                    e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                            "minecraft", "use", "chat-forward", "case-miraicode"
                    )))));

            } catch (Exception ex) {
                Logger.logCatch(ex);
            }
        });
    }

}
