package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;
import java.util.NoSuchElementException;

public class onPlayerJoin implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    Yaml yaml = new Yaml();
    InputStream in = plugin.getResource("usesconfig.yml");
    Map<String, Object> map = yaml.load(in);

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent pljev) {
        if ((Boolean) ((Map<String, Object>) ((Map<String, Object>) map.get("qmsg")).get("send-player-join-quit")).get("enabled")) {
            Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
            Long group = Long.valueOf(plugin.getConfig().getString("general.group"));

            String pljname = pljev.getPlayer().getName();
            String joinmsg = (String) ((Map<String, Object>)((Map<String, Object>) ((Map<String, Object>) map.get("qmsg")).get("send-player-join-quit")).get("format")).get("join");
            joinmsg = joinmsg.replace("%player%", pljname);
            try {
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(joinmsg);
            } catch (NoSuchElementException nse) {
                System.out.println("发送消息时发生异常:\n" + nse);
            }
        }
    }

}