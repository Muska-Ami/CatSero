package moe.xmcn.catsero.event.cmdboot;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.util.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CMS implements TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("send")) {
            //发送消息，使用配置文件中规定的Bot与Group
            Config.sendMiraiGroupMessage(args[1]);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.sent")));
        } else if (args.length == 4 && args[0].equalsIgnoreCase("sendcustom")) {
            //发送消息，使用自定义Bot与Group
            try {
                MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(args[3]);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.sent")));
            } catch (Exception e) {
                //无论什么异常均捕捉返回
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.error")));
                Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq-error").replace("%error%", e + Arrays.toString(e.getStackTrace())));
            }
        } else {
            //参数数量不正确
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.too-many-or-low-options")));
            return false;
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        List<String> sublist = new ArrayList<>();
        switch (args.length) {
            case 2:
                sublist.add("send");
                sublist.add("sendcustom");
                return sublist;
            case 3:
                switch (args[0]) {
                    case "send":
                        sublist.add("消息");
                        return sublist;
                    case "sendcustom":
                        sublist.add("Bot");
                        return sublist;
                    default:
                        return null;
                }
            case 4:
                if (Objects.equals(args[0], "sendcustom")) {
                    sublist.add("Group");
                    return sublist;
                }
            case 5:
                if (Objects.equals(args[0], "sendcustom")) {
                    sublist.add("消息");
                    return sublist;
                }
            default:
                return null;
        }
    }
}
