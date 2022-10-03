package moe.xmcn.catsero.v2.utils;

import moe.xmcn.catsero.v2.CatSero;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;

public interface Configs {
    Plugin plugin = getPlugin();

    File config = new File(plugin.getDataFolder(), "config.yml");

    static Plugin getPlugin() {
        try {
            return CatSero.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
