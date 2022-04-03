package moe.xmcn.catsero.qmsg.listener;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.NoSuchElementException;

public class onPlayerJoin implements Listener {
    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        if(plugin.getConfig().getBoolean("general.ext-qmsg.send-play-quit-join-message.enabled",false)&&!e.getPlayer().hasPermission("catsero.qmsg.join.silent")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    String message = plugin.getConfig().getString("general.ext-qmsg.send-play-quit-join-message.format.join").replace("%player%", e.getPlayer().getName());
                    plugin.getConfig().getLongList("general.bots").forEach(bot -> plugin.getConfig().getLongList("general.groups").forEach(group -> {
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
                        } catch (NoSuchElementException e) {
                            plugin.getLogger().warning("指定的机器人" + bot + "不存在，是否已经登录了机器人？");
                        }
                    }));
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}