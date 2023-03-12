package moe.xmcn.catsero.uses.timers.infoPlaceholder;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import moe.xmcn.catsero.utils.timers.TPSCalculator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class TitlePlaceholder implements Runnable {

    public static final FileConfiguration origins = new YamlConfiguration();
    private final static String ThisID = Configuration.USESID.INFO_PLACEHOLDER;
    private final String bot = Configuration.getUseMiraiBot(ThisID);
    private final List<String> groups = Configuration.getUseMiraiGroup(ThisID);

    @Override
    public void run() {
        if (Configuration.getUses().getBoolean(Configuration.buildYaID(
                ThisID,
                new ArrayList<>(Collections.singletonList("enable"))
        ))) {
            groups.forEach(group -> {
                MiraiGroup originGroup = MiraiBot.getBot(new Configuration().getBotCode(bot)).getGroup(new Configuration().getGroupCode(group));
                if (origins.getString(group) == null) origins.set(group, originGroup.getName());
                // 检查权限
                if (originGroup.getBotPermission() == 1 || originGroup.getBotPermission() == 2) {
                    Logger.logDebug("开始处理Title占位符");
                    // 定义原文
                    String originText;
                    originText = Configuration.getUses().getString(Configuration.buildYaID(
                            ThisID,
                            new ArrayList<>(Arrays.asList(
                                    "format", "titles", group
                            ))
                    ));
                    Logger.logDebug("源格式:" + originText);
                    String formattedText;
                    formattedText = originText.replace("%origin%", origins.getString(group));
                    Logger.logDebug("新格式:" + formattedText);
                    // 处理占位符
                    // 欢迎pr请求添加更多占位符
                    formattedText = formattedText
                            .replace("%time%", new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()))
                            .replace("%player_limit%", String.valueOf(Bukkit.getServer().getOfflinePlayers().length - 1))
                            .replace("%player_max%", String.valueOf(Bukkit.getServer().getMaxPlayers()))
                            .replace("%server_name%", Bukkit.getServer().getName())
                            .replace("%server_version%", Bukkit.getServer().getVersion())
                            .replace("%server_tps%", String.valueOf(BigDecimal.valueOf(TPSCalculator.getTPS()).setScale(1, RoundingMode.HALF_UP)));
                    Logger.logDebug("新群名称" + formattedText);
                    originGroup.setName(formattedText);
                } else
                    Logger.logWARN("QQ占位符功能Bot权限不足，无法修改群名称！");
            });
        }
    }

}
