package moe.xmcn.catsero.v2.listeners.WhiteList;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Loggers;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.Objects;

public class OnPlayerPreLoginEvent implements Listener {

    private boolean allow = false;

    @EventHandler
    public void onPlayerPreJoin(AsyncPlayerPreLoginEvent e) {
        try {
            if (Configs.JPConfig.uses_config.getBoolean("whitelist.enabled")) {
                String player_name = e.getName();
                Objects.requireNonNull(Utils.getWhiteList()).getStringList("list").forEach(it -> {
                    String[] strings = it.split(":\\|:");
                    if (strings[0].equals(player_name)) {
                        if (Configs.JPConfig.uses_config.getBoolean("whitelist.not-allow-is-not-group-member")) {
                            if (
                                    MiraiBot.getBot(Configs.getBotCode(Utils.X_Bot))
                                            .getGroup(Configs.getGroupCode(Utils.X_Group))
                                            .getMember(Long.parseLong(strings[1])) != null
                            ) {
                                allow = true;
                            }
                        } else {
                            allow = true;
                        }
                    }
                });

                if (!allow) {
                    e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, ChatColor.translateAlternateColorCodes('&', Configs.getMsgByMsID("mc.whitelist.not-whitelist")));
                }
            }
        } catch (Exception ex) {
            Loggers.CustomLevel.logCatch(ex);
        }
    }

}
