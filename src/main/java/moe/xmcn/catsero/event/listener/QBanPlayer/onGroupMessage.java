package moe.xmcn.catsero.event.listener.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onGroupMessage(MiraiGroupMessageEvent event) {
        if (plugin.getConfig().getBoolean("general.ext-qbanplayer.enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {
                long bot = plugin.getConfig().getLong("general.bot");
                long group = plugin.getConfig().getLong("general.group");
                if (event.getSenderID() == plugin.getConfig().getLong("general.qq-op")) {
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
