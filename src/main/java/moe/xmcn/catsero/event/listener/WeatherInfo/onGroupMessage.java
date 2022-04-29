package moe.xmcn.catsero.event.listener.WeatherInfo;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void MiraiGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getBoolean("general.ext-weatherinfo.enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("weather")) {
                long bot = Long.parseLong(plugin.getConfig().getString("general.bot"));
                long group = Long.parseLong(plugin.getConfig().getString("general.group"));
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
