package moe.xmcn.catsero.event.listener.PingHost;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Punycode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getBoolean("general.ext-pinghost.enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ping")) {
                long bot = plugin.getConfig().getLong("general.bot");
                long group = plugin.getConfig().getLong("general.group");
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping进行中，请耐心等待...");
                try {
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(Punycode.encodeURL(args[2]));
                    } catch (UnknownHostException e) {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]无法解析主机名/IP");
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            assert address != null;
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping时发生错误");
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]Ping时发生错误");
                        }
                    }
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(args[2] + "(" + (Punycode.encodeURL(args[2])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) * 100 / 4 + "% 丢失)");
                } catch (NoSuchElementException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
