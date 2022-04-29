package moe.xmcn.catsero.event.listener.QMsg.ChatForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class onPlayerJoin implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    private static HashMap<Player,Boolean> cache = new HashMap<>();

    Yaml yaml = new Yaml();
    InputStream in = Object.class.getClassLoader().getResourceAsStream("usesconfig.yml");//或者app.yaml
    Map<String, Object> map = yaml.loadAs(in, Map.class);

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        if ((Boolean) ((Map<String, Object>) ((Map<String, Object>) map.get("qmsg")).get("forward-chat")).get("enabled")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    String message = (String) ((Map<String, Object>)((Map<String, Object>) ((Map<String, Object>) map.get("qmsg")).get("forward-chat")).get("format")).get("to-qq");
                    message = message.replace("%player%", e.getPlayer().getName());
                    long bot = plugin.getConfig().getLong("general.bot");
                    long group = plugin.getConfig().getLong("general.group");
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}