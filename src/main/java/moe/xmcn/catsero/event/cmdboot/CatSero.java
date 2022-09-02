package moe.xmcn.catsero.event.cmdboot;

import moe.xmcn.catsero.Updater;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.HelpList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CatSero implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length >= 1) {
            // Ping功能
            if (moe.xmcn.catsero.event.listener.PingHost.OnGameCommand.onCommand(sender, command, label, args)) {
                // 天气获取
            } else if (moe.xmcn.catsero.event.listener.WeatherInfo.OnGameCommand.onCommand(sender, command, label, args)) {
                // Punycode
            } else if (moe.xmcn.catsero.event.listener.Punycode.OnGameCommand.onCommand(sender, command, label, args)) {
                // 重载配置
            } else if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("catsero.admin")) {
                    Config.reloadConfig();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.reload")));
                    return true;
                }
                // 检查更新
            } else if (args[0].equalsIgnoreCase("update")) {
                if (sender.hasPermission("catsero.admin")) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&a开始检查更新..."));
                            sender.sendMessage(Updater.startUpdateCheck(true));
                        }
                    }.runTaskAsynchronously(Config.plugin);
                    return true;
                }
                // 帮助
            } else if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage(HelpList.Companion.getList("mc"));
                return true;
                // 传入了参数但是未找到方法
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.undefined-usage")));
                return false;
            }
        } else {
            // 啥都没有，返回插件信息
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "This server is running CatSero v" + ChatColor.ITALIC + Config.PluginInfo.getString("version") + ChatColor.RESET + " By " + ChatColor.ITALIC + Config.PluginInfo.getString("author")));
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> sublist = new ArrayList<>();
        switch (args.length) {
            case 2:
                sublist.add("ping");
                sublist.add("weather");
                sublist.add("punycode");
                sublist.add("update");
                sublist.add("reload");
                return sublist;
            case 3:
                switch (args[0]) {
                    case "ping":
                        sublist.add("地址");
                        return sublist;
                    case "weather":
                        sublist.add("中国大陆城市");
                        return sublist;
                    case "punycode":
                        sublist.add("字符串");
                        return sublist;
                    default:
                        return null;
                }
            default:
                return null;
        }
    }
}
