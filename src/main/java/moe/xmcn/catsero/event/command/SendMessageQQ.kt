package moe.xmcn.catsero.event.command

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.Main
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class SendMessageQQ : CommandExecutor {
    var plugin: Plugin = Main.getPlugin(Main::class.java)
    var prefixmc: String = plugin.config.getString("format-list.prefix.to-mc")
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.size == 3) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', prefixmc + "&a由Bot[" + args[0] + "]发送消息[" + args[2] + "]" + "到群[" + args[1] + "]"))
            try {
                MiraiBot.getBot(java.lang.Long.valueOf(args[0])).getGroup(java.lang.Long.valueOf(args[1])).sendMessageMirai(args[2])
            } catch (nse: NoSuchElementException) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "$prefixmc&c发送消息时出现异常$nse"))
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "$prefixmc&c不完整或过多的参数"))
        }
        return false
    }
}