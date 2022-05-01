package moe.xmcn.catsero.event.listener.QMsg.PlayerJoinQuitForward;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.NoSuchElementException;

public class onPlayerJoin implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    @EventHandler
    public void OnPlayerJoin(PlayerJoinEvent pljev) {
        if (usesconfig.getBoolean("qmsg.send-player-join-quit.enabled")) {
            long bot = plugin.getConfig().getLong("qbgset.bot");
            long group = plugin.getConfig().getLong("qbgset.group");

            String pljname = pljev.getPlayer().getName();
            String joinmsg = usesconfig.getString("qmsg.send-player-join-quit.format.join");
            joinmsg = joinmsg.replace("%player%", pljname);
            try {
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(joinmsg);
            } catch (NoSuchElementException nse) {
                System.out.println("发送消息时发生异常:\n" + nse);
            }
        }
    }

}