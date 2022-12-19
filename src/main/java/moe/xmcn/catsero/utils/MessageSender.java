package moe.xmcn.catsero.utils;

import me.dreamvoid.miraimc.api.MiraiBot;
import moe.xmcn.catsero.Configuration;

public class MessageSender {

    public static void sendGroup(String message, String bot, String group) {
        MiraiBot.getBot(Configuration.Interface.getBotCode(bot)).getGroup(Configuration.Interface.getGroupCode(group)).sendMessageMirai(message);
    }

}
