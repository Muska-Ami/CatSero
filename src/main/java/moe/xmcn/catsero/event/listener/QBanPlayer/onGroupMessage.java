package moe.xmcn.catsero.event.listener.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    Yaml yaml = new Yaml();
    InputStream in = Object.class.getClassLoader().getResourceAsStream("usesconfig.yml");//或者app.yaml
    Map<String, Object> map = yaml.loadAs(in, Map.class);

    String prefixqq = plugin.getConfig().getString("format-list.prefix.to-qq");
    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent event) {
        if ((Boolean) ((Map<String, Object>) map.get("qbanplayer")).get("enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            long bot = plugin.getConfig().getLong("qbgset.bot");
            long group = plugin.getConfig().getLong("qbgset.group");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban") && event.getGroupID() == group) {

                if (event.getSenderID() == plugin.getConfig().getLong("qbgset.qq-op")) {
                    System.out.println(event.getSenderID());
                    if (args.length == 5) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "已封禁玩家" + args[2] + "\n理由:" + args[3]);
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    } else {
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "不正确的参数格式");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    }
                } else {
                    try {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(prefixqq + "你没有权限这样做");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                }
            }
        }
    }
}
