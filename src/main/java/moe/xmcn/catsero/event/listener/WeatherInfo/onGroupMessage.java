package moe.xmcn.catsero.event.listener.WeatherInfo;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.HttpUtils;
import moe.xmcn.catsero.utils.WeatherUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    String prefixqq = plugin.getConfig().getString("format-list.prefix.to-qq");

    @EventHandler
    public void MiraiGroupMessage(MiraiGroupMessageEvent event) {
        if (usesconfig.getBoolean("weatherinfo.enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("weather")) {
                long bot = Long.parseLong(plugin.getConfig().getString("qbgset.bot"));
                long group = Long.parseLong(plugin.getConfig().getString("qbgset.group"));
                if (args.length == 3 && event.getGroupID() == group) {
                    try {
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "天气获取进行中，请耐心等待...");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                        String[] resvi = WeatherUtils.getWeather(args[2]);
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("天气信息:\n 类型:" + resvi[4] + "\n 温度:" + resvi[1] + "\n 风力:" + resvi[2] + "\n 风向:" + resvi[3] + "\n 日期:" + resvi[0]);
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    } catch (UnsupportedEncodingException uee) {
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "获取天气时出现错误");;
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    }

                } else {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "请输入城市");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                }
            }
        }
    }
}
