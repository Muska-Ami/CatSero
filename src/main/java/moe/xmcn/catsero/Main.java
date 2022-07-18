package moe.xmcn.catsero;

import moe.xmcn.catsero.events.listeners.NewGroupMember.OnQQGroupNewMember;
import moe.xmcn.catsero.events.listeners.PingHost.OnQQGroupMessage;
import moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward.OnGamePlayerJoin;
import moe.xmcn.catsero.events.listeners.PlayerJoinQuitForward.OnGamePlayerQuit;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Metrics;
import moe.xmcn.catsero.utils.ServerTPS;
import moe.xmcn.xmcore.ThisAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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
        Config.saveDefFile();
        ThisAPI.Companion.savXMCore("xmcore.info");
        getLogger().log(Level.INFO, "[CatSero] 正在加载CatSero插件");
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
        if (Bukkit.getPluginManager().getPlugin("MiraiMC") != null) {
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
    public void regiserEvents() {

        getLogger().log(Level.INFO, "正在注册事件 -> 监听器:CommandExecutor");
        // catsero命令
        Bukkit.getPluginCommand("catsero").setExecutor(new moe.xmcn.catsero.events.cmdboots.CatSero());
        // csm命令
        Bukkit.getPluginCommand("csm").setExecutor(new moe.xmcn.catsero.events.cmdboots.SendMessageQQ());

        getLogger().log(Level.INFO, "正在注册事件 -> 监听器:Listener");
        // PingHost功能
        getServer().getPluginManager().registerEvents(new OnQQGroupMessage(), this);

        // ChatForward聊天转发功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.ChatForward.OnGameChat(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.ChatForward.OnGroupChat(), this);

        // QBanPlayer封禁功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.QBanPlayer.OnQQGroupMessage(), this);

        // WeatherInfo天气获取功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.WeatherInfo.OnQQGroupMessage(), this);

        // PlayerJoinQuitForward玩家加入/退出消息->QQ功能
        getServer().getPluginManager().registerEvents(new OnGamePlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new OnGamePlayerQuit(), this);

        // NewGroupMember群成员变更消息
        getServer().getPluginManager().registerEvents(new OnQQGroupNewMember(), this);

        // OPPlayerQQ QQ添加OP
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.OPPlayerQQ.OnGroupMessage(), this);

        // KickPlayerQQ QQ踢人
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.KickPlayerQQ.OnGroupMessage(), this);

        // PunyCode PunyCode功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.Punycode.OnGroupMessage(), this);

        // GetTPS 获取TPS功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.GetTPS.OnQQGroupMessage(), this);

        // BindQQ 绑定QQ功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.events.listeners.BindQQ.OnQQGroupMessage(), this);

        getLogger().log(Level.INFO, "正在注册事件 -> QQ帮助菜单");
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.QQHelp(), this);
    }
}