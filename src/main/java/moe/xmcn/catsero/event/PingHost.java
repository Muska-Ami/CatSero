package moe.xmcn.catsero.event;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingHost implements Listener, CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            if (args[0] == "ping") {
                InetAddress address = null;
                try {
                    address = InetAddress.getByName(args[1]);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                int flag=0;
                for (int i = 0; i < 4; i++) {
                    boolean b= false;
                    try {
                        b = address.isReachable(1000);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(b)
                        flag++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                sender.sendMessage(args[1] + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4-flag) + "(" + (4-flag)/4*100 + "% 丢失)");

            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    //@EventHandler
    //public void onGroupMessageReceive(MiraiGroupMessageEvent e) {

        //if (e.getBotID() == plugin.getConfig().getString("bot")) {

        //}
        //MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(address.getHostAddress()+" 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = "+flag+" ,丢失 = "+(4-flag)+"("+(4-flag)/4*100+"% 丢失)");
    //}

}