package moe.xmcn.catsero;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public class bStats extends JavaPlugin {

    @Override
    public void onEnable() {
        // All you have to do is adding the following two lines in your onEnable method.
        // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 14767; // <-- Replace with the id of your plugin!
        new Metrics(this, pluginId);
    }

}