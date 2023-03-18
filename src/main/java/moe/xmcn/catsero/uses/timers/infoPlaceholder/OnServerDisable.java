package moe.xmcn.catsero.uses.timers.infoPlaceholder;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OnServerDisable {

    private final static String ThisID = Configuration.USESID.INFO_PLACEHOLDER;

    private final String bot = Configuration.getUseMiraiBot(ThisID);
    private final List<String> groups = Configuration.getUseMiraiGroup(ThisID);

    public void run() {
        if (Configuration.getUses().getBoolean(Configuration.buildYaID(
                ThisID,
                new ArrayList<>(Collections.singletonList("enable"))
        ))) {
            groups.forEach(group -> {
                MiraiGroup originGroup = MiraiBot.getBot(new Configuration().getBotCode(bot)).getGroup(new Configuration().getGroupCode(group));
                if (originGroup.getBotPermission() == 1 || originGroup.getBotPermission() == 2) {
                    originGroup.setName(TitlePlaceholder.origins.getString(group));
                } else
                    Logger.logWARN("QQ占位符功能Bot权限不足，无法修改群名称！");
                originGroup.getMember(new Configuration().getBotCode(bot)).setNameCard(BotNamePlaceholder.originName);
            });
        }
    }
}
