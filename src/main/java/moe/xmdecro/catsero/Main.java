package moe.xmdecro.catsero;

import me.dreamvoid.miraimc.api.MiraiBot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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

    /*public void onGroupMessageReceive(MiraiGroupMessageEvent e){

        //getLogger().info("接收到群聊"+e.getGroupID()+"的消息: "+e.getMessage());
        //MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessageMirai("[mirai:at:"+e.getSenderID()+"] 你发送了一条消息："+e.getMessage());
    }*/
    public class CatSero implements CommandExecutor{
        // This method is called, when somebody uses our command
        @Override
        public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
            if (args == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e------&bCatSero菜单&e------"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/catsero sendmsg   &b发送群消息"));
            } else if (args[0] == "sendmsg") {
                MiraiBot.getBot(Long.parseLong(args[1])).getGroup(Long.parseLong(args[2])).sendMessage(args[3]);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "[&bCatSero&r] &e已发送群消息"));
            }
            return true;
        }
    }
}
