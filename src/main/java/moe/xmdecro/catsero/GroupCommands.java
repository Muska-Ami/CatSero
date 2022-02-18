package moe.xmdecro.catsero;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

public class GroupCommands extends JavaPlugin implements Listener {

    @EventHandler
    public void onFriendMessageReceive(MiraiFriendMessageEvent e){
        if(e.getMessage().equals("ban")) {
            //getBot(e.getBotID()).getFriend(e.getSenderID()));
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("待实现");
        }
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){
        if(e.getMessage().equals("player online")) {
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("当前在线人数：" + Bukkit.getServer().getOnlinePlayers().size()+"人");
        }
    }
}
