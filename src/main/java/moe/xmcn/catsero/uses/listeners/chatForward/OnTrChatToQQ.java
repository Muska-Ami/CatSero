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

import me.arasple.mc.trchat.api.event.TrChatEvent;
import me.arasple.mc.trchat.module.display.ChatSession;
import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Filter;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnTrChatToQQ implements Listener {

    private final I18n i18n = new I18n();
    private final String ThisID = Configuration.USESID.CHAT_FORWARD;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;
    private String message;
    private String mst;

    public OnTrChatToQQ() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }

    @EventHandler
    public void onTrChat(TrChatEvent e) {
        if (!e.isCancelled()) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    try {
                        if (enable) {
                            message = e.getMessage();
                            String channel = e.getChannel().getId();

                            // 先检查聊天频道
                            if (Configuration.getExt_trchat().getStringList(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                                    "channel"
                            )))).contains(channel)) {
                                // Filter
                                if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                        "filter", "enable"
                                ))))) {
                                    new Filter.CHAT_FORWARD().getQQWords().forEach(it -> message = message.replace(it, Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                            "filter", "replace"
                                    ))))));
                                    run1(e.getSession(), message);
                /*
                if (
                        !filter
                        && Configuration.EXTRA_CONFIG.TRCHAT.CHAT_FORWARD.CHANNEL.contains(channel)
                )
                        run(e.getSession(), message);
                else
                    filter = false;

                 */
                                } else
                                    run1(e.getSession(), message);
                            }
                        }
                    } catch (Exception ex) {
                        Logger.logCatch(ex);
                    }
                }
            }.runTaskAsynchronously(CatSero.INSTANCE);
        }
    }

    private void run1(ChatSession e, String message) {
        mst = message;
        groups.forEach(group -> {
            try {
                String format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "format", "to-qq"
                ))));

                // 检查消息是否含有mirai码
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
                        mst = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));

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
