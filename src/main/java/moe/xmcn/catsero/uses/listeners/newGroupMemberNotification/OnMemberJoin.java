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
package moe.xmcn.catsero.uses.listeners.newGroupMemberNotification;

import me.dreamvoid.miraimc.bukkit.event.group.member.MiraiMemberJoinEvent;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnMemberJoin implements Listener {

    private final String ThisID = Configuration.USESID.NEW_GROUP_MEMBER_NOTIFICATION;
    private final boolean enable;
    private final String bot;
    private final List<String> groups;

    public OnMemberJoin() {
        this.enable = Configuration.getUses().getBoolean(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                "enable"
        ))));
        this.bot = Configuration.getUseMiraiBot(ThisID);
        this.groups = Configuration.getUseMiraiGroup(ThisID);
    }

    @EventHandler
    public void onMemberJoin(MiraiMemberJoinEvent e) {
        if (
                enable
                        && new Configuration().checkBot(e.getBotID(), bot)
                        && new Configuration().checkGroup(e.getGroupID(), groups)
        ) {
            long group = e.getGroupID();
            // 格式
            String format = Configuration.getUses().getString(Configuration.buildYaID(ThisID, new ArrayList<>(Collections.singletonList(
                    "format"
            ))));
            format = format.replace("%at%", "[mirai:at:" + e.getMember().getId() + "]")
                    .replace("%code%", String.valueOf(e.getMember().getId()))
                    .replace("%name%", e.getMember().getNick());
            try {
                MessageSender.sendGroup(format, bot, group);
            } catch (IOException ex) {
                Logger.logCatch(ex);
            }
        }
    }

}
