package moe.xmcn.catsero.uses.timers.infoPlaceholder;

import moe.xmcn.catsero.utils.timers.TPSCalculator;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReplacePlaceholders {

    public static String replace(String s) {
        return s.replace("%time%", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                .replace("%player_limit%", String.valueOf(Bukkit.getServer().getOfflinePlayers().length - 1))
                .replace("%player_max%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                .replace("%server_name%", Bukkit.getServer().getName())
                .replace("%server_version%", Bukkit.getServer().getVersion())
                .replace("%server_tps%", String.valueOf(BigDecimal.valueOf(TPSCalculator.getTPS()).setScale(1, RoundingMode.HALF_UP)));
    }

}
