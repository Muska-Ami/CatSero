package moe.xmcn.catsero.event;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class WeatherInfo implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void MiraiGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getString("general.ext-weatherinfo.enabled") == "true") {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("weather")) {
                Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
                Long group = Long.valueOf(plugin.getConfig().getString("general.group"));
                if (args.length == 3) {
                    String res = WeatherUtils.GetWeatherData(args[2]);
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(res);
                } else {
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]请输入城市");
                }
            }
        }
    }
}
