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
package moe.xmcn.catsero.v2.listeners;

import moe.xmcn.catsero.v2.listeners.ChatForward.OnGameChatEvent;
import moe.xmcn.catsero.v2.listeners.ChatForward.OnGroupChatEvent;
import moe.xmcn.catsero.v2.listeners.ChatForward.OnTrChatEvent;
import moe.xmcn.catsero.v2.listeners.DeadthForward.OnPlayerDeadthEvent;
import moe.xmcn.catsero.v2.listeners.JoinQuitForward.OnPlayerJoinEvent;
import moe.xmcn.catsero.v2.listeners.JoinQuitForward.OnPlayerQuitEvent;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;

public interface ListenerRegister {

    static void register() {
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), Configs.plugin);
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), Configs.plugin);

        Configs.plugin.getServer().getPluginManager().registerEvents(new OnGroupChatEvent(), Configs.plugin);
        if (Env.TrChat) {
            Configs.plugin.getServer().getPluginManager().registerEvents(new OnTrChatEvent(), Configs.plugin);
        } else {
            Configs.plugin.getServer().getPluginManager().registerEvents(new OnGameChatEvent(), Configs.plugin);
        }

        Configs.plugin.getServer().getPluginManager().registerEvents(new OnPlayerDeadthEvent(), Configs.plugin);
    }

}
