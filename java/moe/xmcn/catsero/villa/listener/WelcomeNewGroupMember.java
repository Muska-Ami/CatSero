package moe.xmcn.catsero.villa;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class WelcomeNewGroupMember extends JavaPlugin implements Listener {

    @Override // 启用插件
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMemberJoinEvent e){
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(e.getNewMemberID()+"欢迎加入本服务器");
    }
}
