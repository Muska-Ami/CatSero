package moe.xmdecro.catsero;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginLogger;

import java.util.logging.Logger;

public class Main extends JavaPlugin implements Listener {

    @Override // 启用插件
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.
    }

    @Override // 禁用插件
    public void onDisable() {

    }

    @EventHandler
    public void onFriendMessageReceive(MiraiFriendMessageEvent e) {
        if () {
            getLogger().info("接收到好友" + e.getSenderID() + "的消息: " + e.getMessage());
            MiraiBot.getBot(e.getBotID()).getFriend(e.getSenderID()).sendMessage("你发送了一条消息：" + e.getMessage());
        }
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){
        getLogger().info("接收到群聊"+e.getGroupID()+"的消息: "+e.getMessage());
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessageMirai("[mirai:at:"+e.getSenderID()+"] 你发送了一条消息："+e.getMessage());
    }
}
