package moe.xmcn.catsero.qmsg;

import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class QChatMessage implements Listener, CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    String console = plugin.getConfig().getString("general.console-name", "控制台");

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e) {
        if (plugin.getConfig().getString("genal.ext-qmsg.print-group-message-console") == "true") {
            System.out.println("[" + e.getGroupName() + "/" + e.getGroupID() + "]:" + e.getMessage());
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("catsero") && args[0] == "qmsg") {
            if (plugin.getConfig().getString("genal.ext-qmsg.forward-message.enabled") == "true") {
                String playerName;
                boolean allowWorld = false;
                boolean isPlayer = false;
                boolean allowConsole = plugin.getConfig().getBoolean("general.ext-qmsg.forward-message.allow-console-chat", false);

                if (sender instanceof Player) {
                    isPlayer = true;
                    Player player = (Player) sender;
                    playerName = player.getDisplayName();
                    // 判断玩家所处世界
                    for (String world : plugin.getConfig().getStringList("general.ext-msg.forward-message.available-worlds")) {
                        if (player.getWorld().getName().equalsIgnoreCase(world)) {
                            allowWorld = true;
                            break;
                        }
                    }
                    if (plugin.getConfig().getBoolean("general.ext-qmsg.forward-message.available-worlds.use-as-blacklist"))
                        allowWorld = !allowWorld;

                } else {
                    if (allowConsole) {
                        playerName = console;
                        allowWorld = true;
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + console + "不能执行此命令！"));
                        return true;
                    }
                }

                if (allowWorld) {
                    StringBuilder message = new StringBuilder();
                    for (String arg : args) {
                        message.append(arg).append(" ");
                    }
                    String formatText = plugin.getConfig().getString("general.ext-qmsg.forward-message.forward-message-format")
                            .replace("%player%", playerName)
                            .replace("%message%", message);
                    if (isPlayer && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
                        formatText = PlaceholderAPI.setPlaceholders((Player) sender, formatText);
                    }
                    String finalFormatText = formatText;
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            plugin.getConfig().getLongList("general.ext-qmsg.bots").forEach(bot -> plugin.getConfig().getLongList("bot.group-ids").forEach(group -> {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(finalFormatText);
                            }));
                        }
                    }.runTaskAsynchronously((Plugin) this);
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a已发送QQ群聊天消息！"));
                    if (plugin.getConfig().getBoolean("general.ext-qmsg.forward-message.command-also-broadcast-to-chat") && sender instanceof Player) {
                        Player player = (Player) sender;
                        player.chat(message.toString());
                    }
                }
            } else {
                if (command.getName().equalsIgnoreCase("catsero") && args[0] == "qmsg") {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e] &c你没有启用QMsg中的Forward-Message功能"));
                }
            }
        }
        return false;
    }
}
