package moe.xmcn.catsero.event;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class DefCmd implements CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e] &c无法找到使用方法"));
        }
        if (args[0] == "reload") {
            plugin.reloadConfig();
            commandSender.sendMessage("&e[&bCatSero&e]&a配置文件已重载");
        }
        return false;
    }
}
