package moe.xmcn.catsero.executors.cms;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

public class Method implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length >= 1) {
                if (sender.hasPermission("catsero.cms")) {
                    if (args.length >= 3) {
                        StringBuilder message = new StringBuilder();
                        for (var i = 2; i <= args.length - 1; i++) {
                            message.append(args[i])
                                    .append(" ");
                        }
                        message = new StringBuilder(message.substring(0, message.length() - 1));
                        MessageSender.sendGroup(message.toString(), args[0], args[1]);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.CMS.SENT));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                    }
                } else
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
            }
        } catch (Exception e) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.CMS.ERROR));
            Logger.logCatch(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            if (args.length >= 1) {
                List<String> sublist = new ArrayList<>();
                switch (args.length) {
                    case 1 -> {
                        sublist.add("BotID");
                        return sublist;
                    }
                    case 2 -> {
                        sublist.add("GroupID");
                        return sublist;
                    }
                    case 3 -> {
                        sublist.add("Message");
                        return sublist;
                    }
                }
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
    }
}
