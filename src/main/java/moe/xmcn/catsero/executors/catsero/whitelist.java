package moe.xmcn.catsero.executors.catsero;

import moe.xmcn.catsero.CatSero;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.I18n;
import moe.xmcn.catsero.utils.Player;
import moe.xmcn.catsero.utils.WhiteListDatabase;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

public class whitelist {

    private final I18n i18n = new I18n();

    public void command(CommandSender sender, String[] args) {
        if (sender.hasPermission("catsero.admin")) {
            //白名单
            if (args.length == 4) {
                // 添加
                if (args[1].equalsIgnoreCase("add")) {
                    String regex = Configuration.getUses().getString(Configuration.buildYaID(Configuration.USESID.QWHITELIST, new ArrayList<>(Collections.singletonList(
                            "regex"
                    ))));
                    boolean match = Pattern.matches(regex, args[2]);
                    if (match)
                        if (!WhiteListDatabase.getNameList().contains(args[2])) {
                            if (WhiteListDatabase.insertList(args[2], Long.parseLong(args[3]))) {
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "add-success"
                                )))));
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "add-error-sql"
                                )))));
                        } else
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                    "minecraft", "use", "qwhitelist", "add-error-repeat"
                            )))));
                    else
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "use", "qwhitelist", "add-error-name-not-allowed"
                        )))));
                }
            } else if (args.length == 3) {
                // 移除
                if (args[1].equalsIgnoreCase("remove")) {
                    if (WhiteListDatabase.getNameList().contains(args[2])) {
                        if (WhiteListDatabase.removeList(args[2])) {
                            if (
                                    Player.getUUIDByName(args[2]) != null
                                            && Player.getPlayer(args[2]).isOnline()
                            ) {
                                Bukkit.getScheduler().runTask(
                                        CatSero.INSTANCE,
                                        () -> Player.getOnlinePlayer(args[2])
                                                .kickPlayer(
                                                        ChatColor.translateAlternateColorCodes(
                                                                '&',
                                                                i18n.getI18n(new ArrayList<>(Arrays.asList(
                                                                        "minecraft", "use", "qwhitelist", "remove-kick"
                                                                )))
                                                        )
                                                )
                                );
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "remove-success"
                                )))));
                        } else
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                    "minecraft", "use", "qwhitelist", "remove-error-sql"
                            )))));
                    } else
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                "minecraft", "use", "qwhitelist", "remove-error-not-found"
                        )))));
                }
            } else if (args.length == 5) {
                // 更新
                if (args[1].equalsIgnoreCase("change")) {
                    if (args[2].equalsIgnoreCase("name")) {
                        if (WhiteListDatabase.getNameList().contains(args[3])) {
                            if (WhiteListDatabase.updateList(args[3], args[4])) {
                                // 如果玩家在线，将玩家踢出
                                if (
                                        Player.getUUIDByName(args[3]) != null
                                                && Player.getPlayer(args[3]).isOnline()
                                ) {
                                    Bukkit.getScheduler().runTask(
                                            CatSero.INSTANCE,
                                            () -> Player.getOnlinePlayer(args[3])
                                                    .kickPlayer(
                                                            ChatColor.translateAlternateColorCodes(
                                                                    '&',
                                                                    i18n.getI18n(new ArrayList<>(Arrays.asList(
                                                                            "minecraft", "use", "qwhitelist", "remove-kick"
                                                                    )))
                                                            )
                                                    )
                                    );
                                }
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "change-success"
                                )))));
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "change-error-sql"
                                )))));
                        } else
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                    "minecraft", "use", "qwhitelist", "change-error-not-found"
                            )))));
                    } else if (args[2].equalsIgnoreCase("qq")) {
                        if (WhiteListDatabase.getNameList().contains(args[3])) {
                            if (WhiteListDatabase.updateList(Long.parseLong(args[3]), Long.parseLong(args[4]))) {
                                // 如果玩家在线，将玩家踢出

                                if (
                                        Player.getUUIDByCode(Long.parseLong(args[3])) != null
                                                && Player.getPlayer(Player.getUUIDByCode(Long.parseLong(args[3]))).isOnline()
                                ) {
                                    Bukkit.getScheduler().runTask(
                                            CatSero.INSTANCE,
                                            () -> Player.getOnlinePlayer(Player.getUUIDByCode(Long.parseLong(args[3])))
                                                    .kickPlayer(
                                                            ChatColor.translateAlternateColorCodes(
                                                                    '&',
                                                                    i18n.getI18n(new ArrayList<>(Arrays.asList(
                                                                            "minecraft", "use", "qwhitelist", "remove-kick"
                                                                    )))
                                                            )
                                                    )
                                    );
                                }
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "change-success"
                                )))));
                            } else
                                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                        "minecraft", "use", "qwhitelist", "change-error-sql"
                                )))));
                        } else
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                                    "minecraft", "use", "qwhitelist", "change-error-not-found"
                            )))));
                    }
                }
            } else
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                        "minecraft", "command", "invalid-option"
                )))));
        } else
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', i18n.getI18n(new ArrayList<>(Arrays.asList(
                    "minecraft", "command", "no-permission"
            )))));
    }

}
