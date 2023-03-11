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

import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.message.passive.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Filter;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class OnGroupMessageToMC implements Listener {

    private final I18n i18n = new I18n();
    private final String ThisID = Configuration.USESID.CHAT_FORWARD;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;
    private String message;

    public OnGroupMessageToMC() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent e) {
        try {
            if (
                    enable
                            && new Configuration().checkBot(e.getBotID(), bot)
                            && new Configuration().checkGroup(e.getGroupID(), groups)
            ) {
                message = e.getMessage();

                // Filter
                if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "filter", "enable"
                ))))) {
                    new Filter.CHAT_FORWARD().getMCWords().forEach(
                            it -> message = message.replace(it, Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                    "filter", "replace"
                            )))))
                    );
                    run(e, message);
                /*
                Configuration.USES_CONFIG.CHAT_FORWARD.FILTER.LIST.VIA.TO_MC.forEach(it -> {
                    if (message.contains(it)) filter = true;
                });
                if (!filter)
                    run(e, message);
                else
                    filter = false;

                 */
                } else
                    run(e, message);

            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

    private void run(MiraiGroupMessageEvent e, String message) {
        try {

            // 先判定是否可能为 json卡片消息
            if (
                    !message.startsWith("{\"app\":")
                            && !message.equals("\"ver\"")
                            && !message.endsWith("\"}")
            ) {
                String format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "format", "to-mc"
                ))));

                // 清理样式代码
                if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "clean-stylecode", "to-mc"
                )))))
                    message = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', message));

                format = format.replace("%message%", message);

                // 处理名称
                String name = e.getSenderNameCard();

                if (name.equals("")) {
                    name = e.getSenderName();
                }
                if (
                        Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                "clean-stylecode", "use-bind"
                        ))))
                                && MiraiMC.getBind(e.getSenderID()) != null
                )
                    name = Player.getPlayer(MiraiMC.getBind(e.getSenderID())).getName();

                format = format.replace("%name%", name);

                // 权限
                int sender_permission = e.getSenderPermission();
                switch (sender_permission) {
                    case 0:
                        format = format.replace("%sender_permission%", i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "call", "member"
                        ))));
                        break;
                    case 1:
                        format = format.replace("%sender_permission%", i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "call", "admin"
                        ))));
                        break;
                    case 2:
                        format = format.replace("%sender_permission%", i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "call", "owner"
                        ))));
                        break;
                }
                if (Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                        "header", "enable"
                ))))) {
                    if (message.startsWith(Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                            "header", "prefix", "to-mc"
                    ))))))
                        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format.replaceFirst(Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Arrays.asList(
                                "header", "prefix", "to-mc"
                        )))), "")));
                } else
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', format));
            }

        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
