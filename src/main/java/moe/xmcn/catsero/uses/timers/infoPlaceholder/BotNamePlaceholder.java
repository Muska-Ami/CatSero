package moe.xmcn.catsero.uses.timers.infoPlaceholder;

import me.dreamvoid.miraimc.api.MiraiBot;
import me.dreamvoid.miraimc.api.bot.MiraiGroup;
import me.dreamvoid.miraimc.api.bot.group.MiraiNormalMember;
import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.utils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotNamePlaceholder implements Runnable {

    private final static String ThisID = Configuration.USESID.INFO_PLACEHOLDER;
    private final String bot = Configuration.getUseMiraiBot(ThisID);
    private final List<String> groups = Configuration.getUseMiraiGroup(ThisID);
    static String originName = null;
    private boolean skipFirst = false;

    @Override
    public void run() {
        if (!skipFirst) {
            skipFirst = true;
        } else {
            groups.forEach(group -> {
                MiraiGroup originGroup = MiraiBot.getBot(new Configuration().getBotCode(bot)).getGroup(new Configuration().getGroupCode(group));
                MiraiNormalMember botobj = originGroup.getMember(new Configuration().getBotCode(bot));

                if (originName == null) originName = botobj.getNick();
                Logger.logDebug("Bot原始名称: " + originName);
                String originText = Configuration.getUses().getString(Configuration.buildYaID(
                        ThisID,
                        new ArrayList<>(Arrays.asList(
                                "format", "bot-name"
                        ))
                ));
                Logger.logDebug("格式: " + originText);
                String formattedText = originText.replace("%origin%", originName);
                formattedText = ReplacePlaceholders.replace(formattedText);
                Logger.logDebug("结果: " + formattedText);
                botobj.setNameCard(formattedText);
            });
        }
    }
}
