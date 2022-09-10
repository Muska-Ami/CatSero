package moe.xmcn.catsero.v2.executors;

import moe.xmcn.catsero.v2.utils.Configs;

public interface ExecutorRegister {
    static void register() {
        Configs.plugin.getServer().getPluginCommand("cms").setExecutor(new CMS());
        Configs.plugin.getServer().getPluginCommand("catsero").setExecutor(new CatSero());
    }
}
