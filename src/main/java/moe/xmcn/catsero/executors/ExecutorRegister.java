package moe.xmcn.catsero.executors;

import moe.xmcn.catsero.Configuration;
import moe.xmcn.catsero.executors.catsero.Method;

public interface ExecutorRegister {

    static void register() {
        Configuration.plugin.getServer().getPluginCommand("cms").setExecutor(new moe.xmcn.catsero.executors.cms.Method());
        Configuration.plugin.getServer().getPluginCommand("catsero").setExecutor(new Method());
    }
}
