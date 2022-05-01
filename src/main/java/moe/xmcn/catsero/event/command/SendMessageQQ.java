package moe.xmcn.catsero.event.command;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.NoSuchElementException;

public class SendMessageQQ implements CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    String prefixmc = plugin.getConfig().getString("format-list.prefix.to-mc");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length == 3) {
            sender.sendMessage(prefixmc + "&a由Bot[" + args[0] + "]发送消息[" + args[2] + "]" + "到群[" + args[1] + "]");
            try {
                MiraiBot.getBot(Long.valueOf(args[0])).getGroup(Long.valueOf(args[1])).sendMessageMirai(args[2]);
            } catch (NoSuchElementException nse) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c发送消息时出现异常" + nse));
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&c不完整或过多的参数"));
        }
        return false;
    }
}
