package moe.xmcn.catsero.villa;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PingHost extends JavaPlugin implements Listener {

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e) throws IOException, InterruptedException {
        Pattern host = Pattern.compile("cs (.*)");
        Matcher res = host.matcher(e.getMessage());
        InetAddress address = InetAddress.getByName(e.getMessage());
        int flag=0;
        for (int i = 0; i < 4; i++) {
            boolean b=address.isReachable(1000);
            if(b)
                flag++;
            Thread.sleep(1000);
        }
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(address.getHostAddress()+" 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = "+flag+" ,丢失 = "+(4-flag)+"("+(4-flag)/4*100+"% 丢失)");
    }

}