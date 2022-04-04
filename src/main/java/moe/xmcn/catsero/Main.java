package moe.xmcn.catsero;

import moe.xmcn.catsero.event.Help;
import moe.xmcn.catsero.event.PingHost;
import moe.xmcn.catsero.ext.qmsg.onPlayerJoinQuit;
import moe.xmcn.catsero.utils.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    FileConfiguration config = getConfig();
    @Override // 加载插件
    public void onLoad() {
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        System.out.println("[CatSero] 正在加载CatSero插件");
        if (config.getString("utils.allow-start-warn") == "true") {
            System.out.println(ChatColor.YELLOW + "请确保正在使用CatSero官方的构建版本,本人只为官方版本提供支持");
        }
    }

    @Override
    public void onEnable() {

        Bukkit.getPluginCommand("catsero").setExecutor(new Help());
        Bukkit.getPluginCommand("catsero").setExecutor(new PingHost());

        getServer().getPluginManager().registerEvents(new PingHost(), this);
        getServer().getPluginManager().registerEvents(new onPlayerJoinQuit(), this);

        System.out.println("[CatSero] CatSero插件加载成功");
        if (config.getString("utils.allow-bstats") == "true") {
            // All you have to do is adding the following two lines in your onEnable method.
            // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
            int pluginId = 14767; // <-- Replace with the id of your plugin!
            new Metrics(this, pluginId);
        }
    }

    @Override
    public void onDisable() {
        System.out.println("[CatSero] 正在卸载CatSero插件");
    }

    /**
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 1 && args[0] == "reload") {
                reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e] &a配置文件已重载"));
            }
        return true;
    }
    */
}
