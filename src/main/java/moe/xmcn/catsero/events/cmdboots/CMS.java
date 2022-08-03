package moe.xmcn.catsero.events.cmdboots;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class CMS implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2 && args[0].equalsIgnoreCase("send")) {
            Config.sendMiraiGroupMessage(args[1]);
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.sent")));
        } else if (args.length == 4 && args[0].equalsIgnoreCase("sendcustom")) {
            try {
                MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessageMirai(args[3]);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.sent")));
            } catch (Exception e) {
                Config.plugin.getLogger().warning(Config.getMsgByMsID("general.send-message-qq-error").replace("%error%", e + Arrays.toString(e.getStackTrace())));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + Config.getMsgByMsID("minecraft.cms.too-many-or-low-options")));
            return false;
        }
        return true;
    }

}
