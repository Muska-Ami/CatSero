package moe.xmcn.catsero.v2.qqlisteners.PlayerJoinQuitForward;

import moe.xmcn.catsero.v2.utils.Configs;

public interface Utils {

    String X_Bot = Configs.getConfig("uses-config.yml").getString("send-player-join-quit.var.bot");
    String X_Group = Configs.getConfig("uses-config.yml").getString("send-player-join-quit.var.group");

}
