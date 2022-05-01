package moe.xmcn.catsero.event.listener.NewGroupMember;

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
    public void OnGroupMemberAdd(MiraiGroupMemberJoinEvent event) {
        if (usesconfig.getBoolean("new-group-member-message.enabled")) {
            long bot = plugin.getConfig().getLong("qbgset.bot");
            long group = plugin.getConfig().getLong("qbgset.group");
            long code = event.getNewMemberID();
            String message = usesconfig.getString("new-group-member-message.format");
            message = message
                    .replace("%code%", String.valueOf(code))
                    .replace("%at%", "[mirai:at:" + code + "]");
            try {
                MiraiBot.getBot(bot).getGroup(group).sendMessageMirai(message);
            } catch (NoSuchElementException nse) {
                System.out.println("发送消息时发生异常:\n" + nse);
            }
        }
    }
}
