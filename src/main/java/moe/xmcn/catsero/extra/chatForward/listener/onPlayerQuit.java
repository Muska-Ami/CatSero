package moe.xmcn.catsero.extra.chatForward.listener;

import moe.xmcn.catsero.extra.chatForward.BukkitPlugin;
import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.util.NoSuchElementException;

public class onPlayerQuit implements Listener {
    private final BukkitPlugin plugin;
    public onPlayerQuit(BukkitPlugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent e){
        if(plugin.getConfig().getBoolean("bot.send-player-join-quit-message",false)&&!e.getPlayer().hasPermission("chat2qq.quit.silent")){
            new BukkitRunnable() {
                @Override
                public void run() {
                    MiraiBot.getBot(plugin.getConfig().getLong("bot.botaccount")).getGroup(plugin.getConfig().getLong("bot.groupid")).sendMessageMirai(plugin.getConfig().getString("bot.player-quit-message").replace("%player%",e.getPlayer().getName()));
                }
            }.runTaskAsynchronously(plugin);
        }
    }
}
