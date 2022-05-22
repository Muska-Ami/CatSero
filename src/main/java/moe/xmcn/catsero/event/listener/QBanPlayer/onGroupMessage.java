package moe.xmcn.catsero.event.listener.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.utils.Config;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {


    @EventHandler
    public void OnGroupMessage(MiraiGroupMessageEvent event) {
        Config.INSTANCE.getUse_Bots().forEach(bot -> Config.INSTANCE.getUse_Groups().forEach(group -> Config.INSTANCE.getQQ_OPs().forEach(qqop -> {
            if (Config.INSTANCE.getUsesConfig().getBoolean("qban-player.enabled") && event.getGroupID() == bot && event.getBotID() == group) {
                String msg = event.getMessage();
                String[] args = msg.split(" ");
                if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban")) {

                    if (event.getSenderID() == qqop) {
                        System.out.println(event.getSenderID());
                        if (args.length == 5) {
                            Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "已封禁玩家" + args[2] + "\n理由:" + args[3]);
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        } else {
                            try {
                                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "不正确的参数格式");
                            } catch (NoSuchElementException nse) {
                                System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                            }
                        }
                    } else {
                        try {
                            MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "你没有权限这样做");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse + Arrays.toString(nse.getStackTrace()));
                        }
                    }
                }
            }
        })));
    }
}
