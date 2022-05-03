package moe.xmcn.catsero.event.listener.QBanPlayer;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.Config;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.NoSuchElementException;

public class onGroupMessage implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    @EventHandler
    public void OnGroupMessage(MiraiGroupMessageEvent event) {
        if (usesconfig.getBoolean("qbanplayer.enabled")) {
            String msg = event.getMessage();
            String[] args = msg.split(" ");
            if (args[0].equalsIgnoreCase("catsero") && args[1].equalsIgnoreCase("ban") && event.getGroupID() == Config.INSTANCE.getUse_Group()) {

                if (event.getSenderID() == plugin.getConfig().getLong("qbgset.qq-op")) {
                    System.out.println(event.getSenderID());
                    if (args.length == 5) {
                        Bukkit.getBanList(BanList.Type.NAME).addBan(args[2], args[3], null, null);
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "已封禁玩家" + args[2] + "\n理由:" + args[3]);
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    } else {
                        try {
                            MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "不正确的参数格式");
                        } catch (NoSuchElementException nse) {
                            System.out.println("发送消息时发生异常:\n" + nse);
                        }
                    }
                } else {
                    try {
                        MiraiBot.getBot(Config.INSTANCE.getUse_Bot()).getGroup(Config.INSTANCE.getUse_Group()).sendMessageMirai(Config.INSTANCE.getPrefix_QQ() + "你没有权限这样做");
                    } catch (NoSuchElementException nse) {
                        System.out.println("发送消息时发生异常:\n" + nse);
                    }
                }
            }
        }
    }
}
