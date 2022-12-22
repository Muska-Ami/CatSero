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
package moe.xmcn.catsero.listeners;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.listeners.advancementforward.OnPlayerAdvancementDoneEvent;
import moe.xmcn.catsero.listeners.deadthforward.OnPlayerDeathEvent;
import moe.xmcn.catsero.listeners.gettps.OnGroupMessageEvent;
import moe.xmcn.catsero.listeners.joinquitforward.OnPlayerJoinEvent;
import moe.xmcn.catsero.listeners.joinquitforward.OnPlayerQuitEvent;
import moe.xmcn.catsero.listeners.newgroupmemberwelcome.OnMemberJoinEvent;

public interface ListenerRegister {

    static void register() {
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerJoinEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerQuitEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerDeathEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnGroupMessageEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerAdvancementDoneEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnMemberJoinEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.listeners.getonlinelist.OnGroupMessageEvent(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.listeners.chatforward.OnGroupMessageEvent(), Configuration.plugin);
    }

}
