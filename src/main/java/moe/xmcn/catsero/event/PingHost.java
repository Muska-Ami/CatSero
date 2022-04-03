package moe.xmcn.catsero.event;

import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;

public class PingHost implements Listener, CommandExecutor {

    public String PingTool(String pingaddress) throws IOException, InterruptedException {
        InetAddress address = InetAddress.getByName(pingaddress);
        int flag=0;
        for (int i = 0; i < 4; i++) {
            boolean b=address.isReachable(1000);
            if(b)
                flag++;
            Thread.sleep(1000);
        }
        return String.valueOf(flag);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 2) {
            if (args[0] == "ping") {
                try {
                    int res = Integer.parseInt(PingTool(args[1]));
                    sender.sendMessage(args[1]+" 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = "+res+" ,丢失 = "+(4-res)+"("+(4-res)/4*100+"% 丢失)");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e) {

        if (e.getBotID() == e.getBotID()) {

        }
        //MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(address.getHostAddress()+" 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = "+flag+" ,丢失 = "+(4-flag)+"("+(4-flag)/4*100+"% 丢失)");
    }

}