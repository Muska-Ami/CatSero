package moe.xmcn.catsero.extra.chatForward.listener;

import moe.xmcn.catsero.extra.chatForward.BukkitPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.NoSuchElementException;

public class onPlayerJoin implements Listener {
    private final BukkitPlugin plugin;
    public onPlayerJoin(BukkitPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent e){
        if(plugin.getConfig().getBoolean("bot.send-player-join-quit-message",false)&&!e.getPlayer().hasPermission("chat2qq.join.silent")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    MiraiBot.getBot(plugin.getConfig().getLong("bot.botaccount")).getGroup(plugin.getConfig().getLong("bot.groupid")).sendMessageMirai(plugin.getConfig().getString("bot.player-join-message").replace("%player%",e.getPlayer().getName()));
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
