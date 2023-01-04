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
import moe.xmcn.catsero.listeners.advancementforward.OnPlayerAdvancementDone;
import moe.xmcn.catsero.listeners.chatforward.OnAsyncPlayerChatToQQ;
import moe.xmcn.catsero.listeners.chatforward.OnGroupMessageToMC;
import moe.xmcn.catsero.listeners.chatforward.OnTrChatToQQ;
import moe.xmcn.catsero.listeners.deadthforward.OnPlayerDeath;
import moe.xmcn.catsero.listeners.getonlinelist.OnGroupMessageRequestList;
import moe.xmcn.catsero.listeners.gettps.OnGroupMessageRequestTPS;
import moe.xmcn.catsero.listeners.joinquitforward.OnPlayerJoin;
import moe.xmcn.catsero.listeners.joinquitforward.OnPlayerQuit;
import moe.xmcn.catsero.listeners.newgroupmemberwelcome.OnMemberJoin;
import moe.xmcn.catsero.listeners.qwhitelist.RefuseNoWhiteList;
import moe.xmcn.catsero.listeners.qwhitelist.SelfApplication;
import moe.xmcn.catsero.listeners.qwhitelist.WhiteListEditor;
import moe.xmcn.catsero.utils.Envrionment;

public interface ListenerRegister {

    static void register() {
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerQuit(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerDeath(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnGroupMessageRequestTPS(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnPlayerAdvancementDone(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnMemberJoin(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnGroupMessageRequestList(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new OnGroupMessageToMC(), Configuration.plugin);
        if (Envrionment.Depends.TrChat)
            Configuration.plugin.getServer().getPluginManager().registerEvents(new OnTrChatToQQ(), Configuration.plugin);
        else
            Configuration.plugin.getServer().getPluginManager().registerEvents(new OnAsyncPlayerChatToQQ(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new RefuseNoWhiteList(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new WhiteListEditor(), Configuration.plugin);
        Configuration.plugin.getServer().getPluginManager().registerEvents(new SelfApplication(), Configuration.plugin);
    }

}
