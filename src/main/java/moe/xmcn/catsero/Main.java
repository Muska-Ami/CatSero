/*
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright © 2022 XiaMoHuaHuo_CN.
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
package moe.xmcn.catsero;

import moe.xmcn.catsero.event.pluginsystem.QQHelp;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.LibChecker;
import moe.xmcn.catsero.util.Metrics;
import moe.xmcn.catsero.util.ServerTPS;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.logging.Level;

/**
 * 主类
 * 注册监听器
 * 保存默认配置文件
 */
public class Main extends JavaPlugin {

    /**
     * 加载时相关操作
     */
    @Override // 加载插件
    public void onLoad() {
        getLogger().log(Level.INFO, "[CatSero] 正在加载CatSero插件");
        LibChecker.checkLib();
        LibChecker.listLibInstallation();
        Config.saveDefFile();
        if (Config.Config.getBoolean("allow-start-warn")) {
            getLogger().warning("请确保正在使用CatSero官方的构建版本,本人只为官方版本提供支持");
        }
    }

    /**
     * 启用时相关操作
     * 只有安装了MiraiMC才会装载插件
     */
    @Override
    public void onEnable() {
        if (LibChecker.shouldEnablePlugin()) {
            regiserEvents();

            // bStats
            if (Config.Config.getBoolean("allow-bstats")) {
                int pluginId = 14767;
                new Metrics(this, pluginId);
            }

            // 启动TPS计算程序
            Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new ServerTPS(), 100L, 1L);

            getLogger().log(Level.INFO, "CatSero插件加载成功");

            // 异步加载更新检查器
            new BukkitRunnable() {
                @Override
                public void run() {
                    getLogger().log(Level.INFO, "开始检查更新...");
                    getLogger().log(Level.INFO, Updater.startUpdateCheck(false));
                }
            }.runTaskAsynchronously(this);

            // 设置一个定时检查更新任务，一分钟查询一次
            if (Config.plugin.getConfig().getBoolean("check-update.enabled")) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        try {
                            Updater.startTimerUpdateCheck();
                        } catch (InterruptedException e) {
                            Config.plugin.getLogger().log(Level.WARNING, Arrays.toString(e.getStackTrace()));
                        }
                    }
                }.runTaskTimerAsynchronously(Config.plugin, 1200L, 1200L);
            }
        } else {
            getLogger().warning("没有安装MiraiMC，CatSero插件将不会启用");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    /**
     * 卸载时相关操作
     */
    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "正在卸载CatSero插件");
    }

    /**
     * 注册事件
     * 所有Listener监听器的事件和CommandExecutor监听器的事件均由此注册
     */
    private void regiserEvents() {

        getLogger().log(Level.INFO, "正在注册事件 -> 监听器:CommandExecutor");
        // catsero命令
        Bukkit.getPluginCommand("catsero").setExecutor(new moe.xmcn.catsero.event.cmdboot.CatSero());
        // cms命令
        Bukkit.getPluginCommand("cms").setExecutor(new moe.xmcn.catsero.event.cmdboot.CMS());

        getLogger().log(Level.INFO, "正在注册事件 -> 监听器:Listener");
        // PingHost功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.PingHost.OnQQGroupMessage(), this);

        // ChatForward聊天转发功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.ChatForward.OnGameChat(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.ChatForward.OnGroupChat(), this);

        // BanPlayerQQ封禁功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.BanPlayerQQ.OnQQGroupMessage(), this);

        // WeatherInfo天气获取功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.WeatherInfo.OnQQGroupMessage(), this);

        // PlayerJoinQuitForward玩家加入/退出消息->QQ功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.PlayerJoinQuitForward.OnGamePlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.PlayerJoinQuitForward.OnGamePlayerQuit(), this);

        // NewGroupMember群成员变更消息
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.NewGroupMember.OnQQGroupNewMember(), this);

        // OPPlayerQQ QQ添加OP
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.OPPlayerQQ.OnQQGroupMessage(), this);

        // KickPlayerQQ QQ踢人
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.KickPlayerQQ.OnQQGroupMessage(), this);

        // PunyCode PunyCode功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.Punycode.OnQQGroupMessage(), this);

        // GetTPS 获取TPS功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.GetTPS.OnQQGroupMessage(), this);

        // BindQQ 绑定QQ功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.BindQQ.OnQQGroupMessage(), this);

        // QDispatchCommand Q群执行MC命令
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QDispathCommand.OnQQGroupMessage(), this);

        // GetMSPT 获取MSPT功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.GetMSPT.OnQQGroupMessage(), this);

        getLogger().log(Level.INFO, "正在注册事件 -> QQ帮助菜单");
        getServer().getPluginManager().registerEvents(new QQHelp(), this);
    }
}
