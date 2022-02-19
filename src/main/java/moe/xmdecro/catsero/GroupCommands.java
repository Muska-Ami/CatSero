package moe.xmdecro.catsero;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class GroupCommands extends JavaPlugin implements Listener {

    /*@EventHandler
    public void onFriendMessageReceive(MiraiFriendMessageEvent e){
        if(e.getMessage().equals("cs ban")) {
            //getBot(e.getBotID()).getFriend(e.getSenderID()));
            MiraiBot.getBot(e.getBotID()).getFriend(e.getSenderID()).sendMessage("待实现");
        }
    }*/

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){
        if (e.getMessage().equals("cs online")) {
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("当前在线人数：" + Bukkit.getServer().getOnlinePlayers().size()+"人");
        }
        if (e.getMessage().equals("cs pica")) {
            MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage("[CQ:image,file=https://skimino-trvs.herokuapp.com/print.php]");
        }
    }
}
