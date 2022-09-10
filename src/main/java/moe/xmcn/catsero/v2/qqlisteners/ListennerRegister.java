package moe.xmcn.catsero.v2.qqlisteners;

import moe.xmcn.catsero.v2.qqlisteners.PlayerJoinQuitForward.OnGamePlayerJoin;
import moe.xmcn.catsero.v2.qqlisteners.PlayerJoinQuitForward.OnGamePlayerQuit;
import moe.xmcn.catsero.v2.utils.Configs;

public interface ListennerRegister {

    static void register() {
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnGamePlayerJoin(), Configs.plugin);
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnGamePlayerQuit(), Configs.plugin);
    }

}
