package moe.xmcn.catsero.uses.timers.infoPrint;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PrintTitle implements Runnable {

    private final static String ThisID = Configuration.USESID.PRINT_TITLE;

    private final String bot = Configuration.getUseMiraiBot(ThisID);
    private final List<String> groups = Configuration.getUseMiraiGroup(ThisID);

    @Override
    public void run() {
        groups.forEach(group -> {
            MiraiGroup originGroup = MiraiBot.getBot(new Configuration().getBotCode(bot)).getGroup(new Configuration().getGroupCode(group));
            if (originGroup.getBotPermission() == 1 || originGroup.getBotPermission() == 2) {
                String originText = originGroup.getName();
                String formatedText = originText
                        .replace("!!time", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                        .replace("!!player_limit", String.valueOf(Bukkit.getServer().getOfflinePlayers().length))
                        .replace("!!player_max", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                        .replace("!!server_name", Bukkit.getServer().getServerName())
                        .replace("!!server_version", Bukkit.getServer().getVersion());
                originGroup.setName(formatedText);
            } else
                Logger.logWARN("[" + ThisID + "] 权限不足，无法修改群名称！");
        });
    }

}
