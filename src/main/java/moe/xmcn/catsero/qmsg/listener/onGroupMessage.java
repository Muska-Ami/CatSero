package moe.xmcn.catsero.qmsg.listener;
import me.clip.placeholderapi.PlaceholderAPI;
import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.MiraiMC;
import me.dreamvoid.miraimc.bukkit.event.MiraiGroupMessageEvent;
import moe.xmcn.catsero.qmsg.QChatMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class onGroupMessage extends QChatMessage {
    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);

    @EventHandler
    public void onGroupMessageReceive(MiraiGroupMessageEvent e){
        String name = e.getSenderNameCard();
        if(name.equalsIgnoreCase("")){
            name = MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).getMember(e.getSenderID()).getNick();
        }
        String formatText;
        if(plugin.getConfig().getBoolean("general.use-bind",true) && !MiraiMC.getBinding(e.getSenderID()).equals("")){
            formatText = plugin.getConfig().getString("general.forward-message.forward-message-format.to-minecraft.bind")
                    .replace("%groupname%",e.getGroupName())
                    .replace("%groupid%",String.valueOf(e.getGroupID()))
                    .replace("%nick%",name)
                    .replace("%qq%",String.valueOf(e.getSenderID()))
                    .replace("%message%",e.getMessage());
            if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
                formatText = PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(UUID.fromString(MiraiMC.getBinding(e.getSenderID()))),formatText);
            }
        } else formatText = plugin.getConfig().getString("general.forward-message.forward-message-format.to-minecraft.default")
                .replace("%groupname%",e.getGroupName())
                .replace("%groupid%",String.valueOf(e.getGroupID()))
                .replace("%nick%",name)
                .replace("%qq%",String.valueOf(e.getSenderID()))
                .replace("%message%",e.getMessage());

        // 判断消息是否带前缀
        boolean allowPrefix = false;
        if(plugin.getConfig().getBoolean("general.forward-message.forward-message-format.requite-special-word-prefix.enabled",false)){
            for(String prefix : plugin.getConfig().getStringList("general.forward-message.forward-message-format.requite-special-word-prefix.prefix")){
                if(e.getMessage().startsWith(prefix)){
                    allowPrefix = true;
                    formatText = formatText.substring(prefix.length());
                    break;
                }
            }
        } else allowPrefix = true;

        if(plugin.getConfig().getLongList("general.bots").contains(e.getBotID()) && plugin.getConfig().getLongList("general.groups").contains(e.getGroupID()) && allowPrefix){
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',formatText));
        }
    }

}