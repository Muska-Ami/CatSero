package moe.xmcn.catsero;

import moe.xmcn.catsero.event.command.CatSero;
import moe.xmcn.catsero.event.command.SendMessageQQ;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * 主类
 * 注册监听器
 * 保存默认配置文件
 */
public class Main extends JavaPlugin {

    FileConfiguration config = getConfig();

    /**
     * 加载时相关操作
     */
    @Override // 加载插件
    public void onLoad() {
        Config.INSTANCE.saveDefConfig();
        System.out.println("[CatSero] 正在加载CatSero插件");
        if (config.getBoolean("utils.allow-start-warn")) {
            getLogger().warning("请确保正在使用CatSero官方的构建版本,本人只为官方版本提供支持");
        }
    }

    /**
     * 启用时相关操作
     */
    @Override
    public void onEnable() {

        regiserEvents();

        // bStats
        if (config.getBoolean("allow-bstats")) {
            int pluginId = 14767;
            new Metrics((JavaPlugin) Config.INSTANCE.getPlugin(), pluginId);
        }

        System.out.println("CatSero插件加载成功");

        // 异步加载更新检查器
        new BukkitRunnable() {
            @Override
            public void run() {
                Updater.onEnable();
            }
        }.runTaskAsynchronously(this);
    }

    /**
     * 卸载时相关操作
     */
    @Override
    public void onDisable() {
        System.out.println("正在卸载CatSero插件");
    }

    /**
     * 注册事件
     * 所有Listener监听器的事件和CommandExecutor监听器的事件均由此注册
     */
    public void regiserEvents() {
        System.out.println("正在注册事件 -> 监听器:CommandExecutor");
        // catsero命令
        Bukkit.getPluginCommand("catsero").setExecutor(new CatSero());
        // csm命令
        Bukkit.getPluginCommand("csm").setExecutor(new SendMessageQQ());

        System.out.println("正在注册事件 -> 监听器:Listener");
        // PingHost功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.PingHost.onGroupMessage(), this);

        // ChatForward聊天转发功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QMsg.ChatForward.OnGameChat(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QMsg.ChatForward.OnGroupChat(), this);

        // QBanPlayer封禁功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QBanPlayer.onGroupMessage(), this);

        // WeatherInfo天气获取功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.WeatherInfo.onGroupMessage(), this);

        // PlayerJoinQuitForward玩家加入/退出消息->QQ功能
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward.onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward.onPlayerQuit(), this);

        // NewGroupMember群成员变更消息
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.NewGroupMember.onGroupMemberAdd(), this);

        // JoinQuitMessage玩家加入/退出消息自定义
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.JoinQuitMessage.onPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.JoinQuitMessage.onPlayerQuit(), this);

        // OPPlayerQQ QQ添加OP
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.OPPlayerQQ.onGroupMessage(), this);

        // KickPlayerQQ QQ踢人
        getServer().getPluginManager().registerEvents(new moe.xmcn.catsero.event.listener.KickPlayerQQ.onGroupMessage(), this);
    }
}