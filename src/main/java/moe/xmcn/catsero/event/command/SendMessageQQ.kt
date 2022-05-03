package moe.xmcn.catsero.event.command

import me.dreamvoid.miraimc.api.MiraiBot
import moe.xmcn.catsero.utils.Config
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SendMessageQQ : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.size == 3) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes(
                    '&',
                    Config.Prefix_MC + "&a由Bot[" + args[0] + "]发送消息[" + args[2] + "]" + "到群[" + args[1] + "]"
                )
            )
            try {
                try {
                    MiraiBot.getBot(java.lang.Long.valueOf(args[0])).getGroup(java.lang.Long.valueOf(args[1])).sendMessageMirai(args[2])
                } catch (nse: NoSuchElementException) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "Bot不存在"))
                } finally {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "发生错误，可能是不正确的指令格式"))
                }
            } catch (nse: NoSuchElementException) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c发送消息时出现异常$nse"))
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Config.Prefix_MC + "&c不完整或过多的参数"))
        }
        return false
    }
}