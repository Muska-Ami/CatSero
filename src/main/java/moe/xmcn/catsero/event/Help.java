package moe.xmcn.catsero.event;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class Help implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (args.length < 1) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e] &c无法找到使用方法"));
        }
        return false;
    }
}
