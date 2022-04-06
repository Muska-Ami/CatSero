package moe.xmcn.catsero.event;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class QBanPlayer implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getString("general.ext-qbanplayer.enabled") == "true") {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {
                Long bot = Long.valueOf(plugin.getConfig().getString("general.bot"));
                Long group = Long.valueOf(plugin.getConfig().getString("general.group"));
                if (event.getSenderID() == Long.valueOf(plugin.getConfig().getString("general.qq-op"))) {
                    System.out.println(event.getSenderID());
                    if (args.length == 5) {
                            Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]已封禁玩家" + args[2] + "\n理由:" + args[3]);
                    } else {
                        MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]不正确的参数格式");
                    }
                } else {
                    MiraiBot.getBot(bot).getGroup(group).sendMessageMirai("[CatSero]你没有权限这样做");
                }
            }
        }
    }
}
