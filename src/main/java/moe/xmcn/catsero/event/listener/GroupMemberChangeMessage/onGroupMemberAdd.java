package moe.xmcn.catsero.event.listener.GroupMemberChangeMessage;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMemberJoinEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.NoSuchElementException;

public class onGroupMemberAdd implements Listener {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
    FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);

    @EventHandler
    public void onGroupMemberAdd(MiraiGroupMemberJoinEvent event) {
        if (usesconfig.getBoolean("group-member-change-message.enabled")) {
            long bot = plugin.getConfig().getLong("qbgset.bot");
            long group = plugin.getConfig().getLong("qbgset.group");
            long code = event.getNewMemberID();
            String message = (String) (usesconfig.getString("group-member-change-message.format.join"));
            message = message
                    .replace("%code%", "%code%")
                    .replace("%at%", "[mirai:at:" + code + "]");
            try {
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
            } catch (NoSuchElementException nse) {
                System.out.println("发送消息时发生异常:\n" + nse);
            }
        }
    }
}
