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
package moe.xmcn.catsero.uses;

import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.uses.listeners.advancementForward.OnPlayerAdvancementDone;
import moe.xmcn.catsero.uses.listeners.chatForward.OnCommonChatToQQ;
import moe.xmcn.catsero.uses.listeners.chatForward.OnGroupMessageToMC;
import moe.xmcn.catsero.uses.listeners.chatForward.OnTrChatToQQ;
import moe.xmcn.catsero.uses.listeners.deathForward.OnPlayerDeath;
import moe.xmcn.catsero.uses.listeners.getOnlineList.OnGroupMessageRequestList;
import moe.xmcn.catsero.uses.listeners.getTPS.OnGroupMessageRequestTPS;
import moe.xmcn.catsero.uses.listeners.groupMemberLeaveNotification.OnMemberLeave;
import moe.xmcn.catsero.uses.listeners.joinQuitForward.OnPlayerJoin;
import moe.xmcn.catsero.uses.listeners.joinQuitForward.OnPlayerQuit;
import moe.xmcn.catsero.uses.listeners.newGroupMemberNotification.OnMemberJoin;
import moe.xmcn.catsero.uses.listeners.qCmd.OnRequestExecuteCommand;
import moe.xmcn.catsero.uses.listeners.qWhitelist.RefuseNoWhiteList;
import moe.xmcn.catsero.uses.listeners.qWhitelist.SelfApplication;
import moe.xmcn.catsero.uses.listeners.qWhitelist.WhiteListEditor;
import moe.xmcn.catsero.uses.timers.infoPlaceholder.BotNamePlaceholder;
import moe.xmcn.catsero.uses.timers.infoPlaceholder.TitlePlaceholder;
import moe.xmcn.catsero.utils.Envrionment;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public interface Register {

    Server server = CatSero.INSTANCE.getServer();
    PluginManager pm = server.getPluginManager();
    BukkitScheduler bs = server.getScheduler();

    static void register() {
        // 玩家加入/退出转发
        pm.registerEvents(new OnPlayerJoin(), CatSero.INSTANCE);
        pm.registerEvents(new OnPlayerQuit(), CatSero.INSTANCE);

        // 玩家死亡转发
        pm.registerEvents(new OnPlayerDeath(), CatSero.INSTANCE);

        // TPS获取
        pm.registerEvents(new OnGroupMessageRequestTPS(), CatSero.INSTANCE);

        // 进度转发
        pm.registerEvents(new OnPlayerAdvancementDone(), CatSero.INSTANCE);

        // 新群员
        pm.registerEvents(new OnMemberJoin(), CatSero.INSTANCE);

        // 玩家列表
        pm.registerEvents(new OnGroupMessageRequestList(), CatSero.INSTANCE);

        // 聊天转发
        pm.registerEvents(new OnGroupMessageToMC(), CatSero.INSTANCE);
        if (Envrionment.Depends.TrChat)
            pm.registerEvents(new OnTrChatToQQ(), CatSero.INSTANCE);
        else
            pm.registerEvents(new OnCommonChatToQQ(), CatSero.INSTANCE);

        // QQ白名单
        pm.registerEvents(new RefuseNoWhiteList(), CatSero.INSTANCE);
        pm.registerEvents(new WhiteListEditor(), CatSero.INSTANCE);
        pm.registerEvents(new SelfApplication(), CatSero.INSTANCE);

        // 群员离群
        pm.registerEvents(new OnMemberLeave(), CatSero.INSTANCE);

        // 命令执行
        pm.registerEvents(new OnRequestExecuteCommand(), CatSero.INSTANCE);

        // QQ占位符
        if (Configuration.getUses().getBoolean(Configuration.buildYaID(
                Configuration.USESID.INFO_PLACEHOLDER,
                new ArrayList<>(Collections.singletonList("enable"))
        ))) {

            if (Configuration.getUses().getStringList(Configuration.buildYaID(
                    Configuration.USESID.INFO_PLACEHOLDER,
                    new ArrayList<>(Collections.singletonList("should-updates"))
            )).contains("group"))
                // 群名称
                bs.scheduleSyncRepeatingTask(
                        CatSero.INSTANCE,
                        new TitlePlaceholder(),
                        0L,
                        Configuration.getUses().getLong(Configuration.buildYaID(
                                Configuration.USESID.INFO_PLACEHOLDER,
                                new ArrayList<>(Arrays.asList(
                                        "interval", "title"
                                ))
                        )) * 20L);

            if (Configuration.getUses().getStringList(Configuration.buildYaID(
                    Configuration.USESID.INFO_PLACEHOLDER,
                    new ArrayList<>(Collections.singletonList("should-updates"))
            )).contains("bot"))
                // Bot名称
                bs.scheduleSyncRepeatingTask(
                        CatSero.INSTANCE,
                        new BotNamePlaceholder(),
                        0L,
                        Configuration.getUses().getLong(Configuration.buildYaID(
                                Configuration.USESID.INFO_PLACEHOLDER,
                                new ArrayList<>(Arrays.asList(
                                        "interval", "bot-name"
                                ))
                        )) * 20L
                );

        }

    }

}
