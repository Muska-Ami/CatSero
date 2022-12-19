package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Method implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {
            if (args.length >= 1) {
                switch (args[0]) {
                    case "version" -> {
                        if (args.length == 1) {
                            if (sender.hasPermission("catsero.admin")) {
                                List<String> env = Arrays.asList(
                                        "&b===== &dCatSero Runtime Checker &b=====",
                                        "&bServer Version: " + Envrionment.server_version,
                                        "&bBukkit Version: " + Envrionment.bukkit_version,
                                        "&bPlugin Version: " + Envrionment.plugin_version,
                                        "&bDepends:",
                                        "- &bMiraiMC &a=> &e" + Envrionment.Depends.MiraiMC,
                                        "&bSoft-depends:",
                                        "- &bPlaceholderAPI &a=> &e" + Envrionment.Depends.PlaceholderAPI,
                                        "- &bTrChat &a=> &e" + Envrionment.Depends.TrChat,
                                        "&b==================================="
                                );
                                for (int i = 1; i <= env.toArray().length; i++) {
                                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) env.toArray()[i - 1]));
                                }
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                        }
                    }
                    case "reload" -> {
                        if (args.length == 1) {
                            if (sender.hasPermission("catsero.admin")) {
                                Configuration.reloadFiles();
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.RELOAD.SUCCESS));
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.NO_PERMISSION));
                        } else {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                        }
                    }
                    default ->
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Configuration.I18N.MINECRAFT.COMMAND.INVALID_OPTION));
                }
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eCatSero &bby &eXiaMoHuaHuo_CN&b, version: " + Envrionment.plugin_version));

        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        try {
            if (args.length >= 1) {
                List<String> sublist = new ArrayList<>();
                if (args.length == 1) {
                    sublist.add("version");
                    sublist.add("reload");
                    return sublist;
                }
                return null;
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        return null;
    }

}
