package moe.xmcn.catsero.v2.listeners;

import moe.xmcn.catsero.v2.listeners.PlayerJoinQuitForward.OnGamePlayerJoin;
import moe.xmcn.catsero.v2.listeners.PlayerJoinQuitForward.OnGamePlayerQuit;
import moe.xmcn.catsero.v2.utils.Configs;

public interface ListenerRegister {

    static void register() {
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnGamePlayerJoin(), Configs.plugin);
        Configs.plugin.getServer().getPluginManager().registerEvents(new OnGamePlayerQuit(), Configs.plugin);
    }

}
