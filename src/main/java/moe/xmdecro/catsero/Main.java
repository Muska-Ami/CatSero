package moe.xmdecro.catsero;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiFriendMessageEvent;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    @Override // 加载插件
    public void onLoad() {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "[&bCatSero&r] CatSero加载中..."));
    }

    @Override // 启用插件
    public void onEnable() {
        this.getCommand("catsero").setExecutor(new CatSero());
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "[&bCatSero&r] 插件加载完毕！"));
    }

    @Override // 禁用插件
    public void onDisable() {
        Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "[&bCatSero&r] &c关闭CatSero"));
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){

        //getLogger().info("接收到群聊"+e.getGroupID()+"的消息: "+e.getMessage());
        //MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessageMirai("[mirai:at:"+e.getSenderID()+"] 你发送了一条消息："+e.getMessage());
    }
    public class CatSero implements CommandExecutor{
        // This method is called, when somebody uses our command
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e------CatSero菜单------"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "/catsero reload") + " 重载插件");
            return true;
        }
    }
}
