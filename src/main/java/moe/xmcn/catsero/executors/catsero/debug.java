package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class debug {

    private final I18n i18n = new I18n();

    public void command(CommandSender sender, String[] args) {
        if (sender.hasPermission("catsero.admin")) {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("readconfig")) {
                    if (args.length == 4) {
                        switch (args[2]) {
                            case "config.yml":
                                sender.sendMessage(Objects.requireNonNull(Configuration.getPlugin().getString(args[3]), "## Empty"));
                                break;
                            case "uses-config.yml":
                                sender.sendMessage(Objects.requireNonNull(Configuration.getUses().getString(args[3]), "## Empty"));
                        }
                    } else
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "command", "no-permission"
                        )))));
                }
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                        "minecraft", "command", "invalid-option"
                )))));
        }
    }
}
