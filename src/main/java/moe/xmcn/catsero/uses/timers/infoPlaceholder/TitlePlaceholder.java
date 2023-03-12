package moe.xmcn.catsero.uses.timers.infoPlaceholder;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitlePlaceholder implements Runnable {

    static final FileConfiguration origins = new YamlConfiguration();
    private final static String ThisID = Configuration.USESID.INFO_PLACEHOLDER;
    private final String bot = Configuration.getUseMiraiBot(ThisID);
    private final List<String> groups = Configuration.getUseMiraiGroup(ThisID);
    private boolean skipFirst = false;

    @Override
    public void run() {
        if (!skipFirst) {
            skipFirst = true;
        } else {
            groups.forEach(group -> {
                MiraiGroup originGroup = MiraiBot.getBot(new Configuration().getBotCode(bot)).getGroup(new Configuration().getGroupCode(group));
                if (origins.getString(group) == null) origins.set(group, originGroup.getName());
                // 检查权限
                if (originGroup.getBotPermission() == 1 || originGroup.getBotPermission() == 2) {
                    // 定义原文
                    String originText = Configuration.getUses().getString(Configuration.buildYaID(
                            ThisID,
                            new ArrayList<>(Arrays.asList(
                                    "format", "titles", group
                            ))
                    ));
                    Logger.logDebug("源格式: " + originText);
                    String formattedText = originText.replace("%origin%", origins.getString(group));
                    Logger.logDebug("新格式: " + formattedText);
                    // 处理占位符
                    // 欢迎pr请求添加更多占位符
                    formattedText = ReplacePlaceholders.replace(formattedText);
                    Logger.logDebug("新群名称: " + formattedText);
                    originGroup.setName(formattedText);
                } else
                    Logger.logWARN("QQ占位符功能Bot权限不足，无法修改群名称！");
            });
        }
    }
}
