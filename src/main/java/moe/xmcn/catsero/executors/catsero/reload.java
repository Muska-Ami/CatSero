package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.I18n;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

public class reload {

    private final I18n i18n = new I18n();

    public void command(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("catsero.admin")) {
                CatSero.INSTANCE.reloadConfig();
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                        "minecraft", "command", "reload", "success"
                )))));
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                        "minecraft", "command", "no-permission"
                )))));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                    "minecraft", "command", "invalid-option"
            )))));
        }
    }

}
