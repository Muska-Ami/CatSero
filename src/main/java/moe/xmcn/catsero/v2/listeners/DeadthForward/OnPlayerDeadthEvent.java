package moe.xmcn.catsero.v2.listeners.DeadthForward;

import moe.xmcn.catsero.v2.utils.Configs;
import moe.xmcn.catsero.v2.utils.Env;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnPlayerDeadthEvent implements Listener {

    @EventHandler
    public void onPlayerDeadthEvent(PlayerDeathEvent e) {
        if (Configs.getConfig("uses-config.yml").getBoolean("send-player-death.enabled")) {
            Player player = e.getEntity();
            String death_message = e.getDeathMessage();

            String format = Configs.getConfig("uses-config.yml").getString("send-player-death.format");

            format = format.replace("%player%", player.getName());
            format = format.replace("%deathmes%", death_message);
            Env.AMiraiMC.sendMiraiGroupMessage(format, Utils.X_Bot, Utils.X_Group);
        }
    }

}
