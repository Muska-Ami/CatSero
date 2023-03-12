package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Envrionment;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class version {

    private final I18n i18n = new I18n();

    public void command(CommandSender sender, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("catsero.admin")) {
                List<String> env = Arrays.asList(
                        "&eCatSero &bby &eXiaMoHuaHuo_CN, version: " + Envrionment.plugin_version,
                        "&eGitHub: &1https://github.com/XiaMoHuaHuo-CN/CatSero",
                        "&eAuthor's blog: &1https://huahuo-cn.tk",
                        "&b===== &dCatSero Runtime Checker &b=====",
                        "&bServer Version: " + Envrionment.server_version,
                        "&bBukkit Version: " + Envrionment.bukkit_version,
                        "&bPlugin Version: " + Envrionment.plugin_version,
                        "&bDepends:",
                        "- &bMiraiMC &a=> &e" + Envrionment.Depends.MiraiMC,
                        "&bSoft-depends:",
                        "- &bPlaceholderAPI &a=> &e" + Envrionment.Depends.PlaceholderAPI,
                        "- &bTrChat &a=> &e" + Envrionment.Depends.TrChat,
                        "- &bfloodgate &a=> &e" + Envrionment.Depends.Floodgate,
                        "&b==================================="
                );
                for (int i = 1; i <= env.toArray().length; i++) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', (String) env.toArray()[i - 1]));
                }
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
