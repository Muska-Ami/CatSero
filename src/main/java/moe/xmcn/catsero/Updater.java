package moe.xmcn.catsero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xmcn.catsero.utils.Envrionment;
import moe.xmcn.catsero.utils.HttpClient;
import moe.xmcn.catsero.utils.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;

public class Updater implements Runnable, Listener {

    private final String now_version = Envrionment.plugin_version;
    private String latest_version;
    private String[] beta_version;

    @Override
    public void run() {
        HttpClient hc = new HttpClient();
        String data = hc.getRequest(Configuration.PLUGIN.CHECK_UPDATE.API_URL);
        JSONObject object = JSON.parseObject(data);

        latest_version = JSON.parseObject(object.get("latest").toString()).getString("tag");
        beta_version = Arrays.asList(
                JSON.parseObject(object.get("beta").toString()).getString("jar_zip"),
                JSON.parseObject(object.get("beta").toString()).getString("full_zip")
        ).toArray(new String[0]);
        if (Configuration.PLUGIN.CHECK_UPDATE.MODE.equalsIgnoreCase("latest")) {
            Logger.logINFO("CatSero latest version: " + latest_version);
        } else if (Configuration.PLUGIN.CHECK_UPDATE.MODE.equalsIgnoreCase("beta")) {
            Logger.logINFO(
                    "CatSero actions build:" +
                    "\nJar Artifact - " + beta_version[0] +
                    "\nFull Artifact - " + beta_version[1]
            );
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (player.isOp()) {
            if (Configuration.PLUGIN.CHECK_UPDATE.MODE.equalsIgnoreCase("latest") && !now_version.equals(latest_version)) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&bCatSero has new version: &e" +
                                        latest_version +
                                        "&b,your version: &e" + now_version
                        )
                );
            } else if (Configuration.PLUGIN.CHECK_UPDATE.MODE.equalsIgnoreCase("beta")) {
                player.sendMessage(
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                "&bCatSero actions build:" +
                                        "\nJar Artifact - &e" + beta_version[0] +
                                        "\n&bFull Artifact - &e" + beta_version[1] +
                                        "\n&byour version: &e" + now_version
                        )
                );
            }
        }
    }

}
