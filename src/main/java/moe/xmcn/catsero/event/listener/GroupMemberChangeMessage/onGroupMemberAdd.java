package moe.xmcn.catsero.event.listener.GroupMemberChangeMessage;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;

public class onGroupMemberAdd implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    Yaml yaml = new Yaml();
    InputStream in = plugin.getResource("usesconfig.yml");
    Map<String, Object> map = yaml.load(in);

    @EventHandler
    public void onGroupMemberAdd(MiraiGroupMemberJoinEvent event) {
        if ((Boolean) ((Map<String, Object>) map.get("group-member-change-message")).get("enabled")) {
            long bot = plugin.getConfig().getLong("qbgset.bot");
            long group = plugin.getConfig().getLong("qbgset.group");
            long code = event.getNewMemberID();
            String message = (String) ((Map<String, Object>)((Map<String, Object>) map.get("group-member-change-message")).get("format")).get("join");
            message = message
                    .replace("%code%", "%code%")
                    .replace("%at%", "[mirai:at:" + code + "]");
            try {
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
            } catch (NoSuchElementException nse) {
                System.out.println("发送消息时发生异常:\n" + nse);
            }
        }
    }
}
