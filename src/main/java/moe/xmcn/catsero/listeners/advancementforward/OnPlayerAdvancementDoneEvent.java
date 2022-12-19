package moe.xmcn.catsero.listeners.advancementforward;

import me.croabeast.advancementinfo.AdvancementInfo;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.MessageSender;
import moe.xmcn.catsero.utils.PAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class OnPlayerAdvancementDoneEvent implements Listener {

    @EventHandler
    public void onPlayerAchievement(PlayerAdvancementDoneEvent e) {
        try {
            if (Configuration.USES_CONFIG.SEND_ADVANCEMENT.ENABLE) {
                AdvancementInfo ai = new AdvancementInfo(e.getAdvancement());
                String adv_name = ai.getTitle();
                String adv_description = ai.getDescription();
                Player player = e.getPlayer();

                String format = Configuration.USES_CONFIG.SEND_ADVANCEMENT.FORMAT;
                assert adv_name != null;
                format = format.replace("%player%", player.getName())
                        .replace("%name%", adv_name)
                        .replace("%description%", adv_description);
                format = PAPI.toPAPI(player, format);
                MessageSender.sendGroup(format, Configuration.USES_CONFIG.SEND_ADVANCEMENT.MIRAI.BOT, Configuration.USES_CONFIG.SEND_ADVANCEMENT.MIRAI.GROUP);
            }
        } catch (Exception ex) {
            Logger.logCatch(ex);
        }
    }

}
