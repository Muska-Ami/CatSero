package moe.xmcn.catsero.v2.executors;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CatSero implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            switch (strings[0]) {
                case "version":
                    if (commandSender.hasPermission("catsero.admin")) {
                        ArrayList<String> env = new ArrayList<>(Arrays.asList(
                                "&e插件版本: &b" + Configs.PluginInfo.getString("version"),
                                "&e插件作者: &b" + Configs.PluginInfo.getString("author"),
                                "&e依赖装载情况:",
                                "- MiraiMC &6=>&r " + Env.MiraiMC,
                                "- PlaceholderAPI &6=>&r " + Env.PlaceholderAPI,
                                "- TrChat &6=>&r " + Env.TrChat,
                                "",
                                "&e服务器版本: &b" + Bukkit.getBukkitVersion()
                        ));
                        for (int i = 1; i <= env.toArray().length; i++) {
                            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) env.toArray()[i - 1]));
                        }
                    }
                    break;
                case "send":
                    break;
                default:
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c命令不存在"));
                    break;
            }
        } else commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bCatSero&r by &eXiaMoHuaHuo_CN"));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 1) {
            List<String> sublist = new ArrayList<>();
            switch (strings.length) {
                case 2:
                    sublist.add("version");
                    return sublist;
                default:
                    return null;
            }
        }
        return null;
    }
}
