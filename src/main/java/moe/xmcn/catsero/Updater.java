package moe.xmcn.catsero;

import com.google.gson.Gson;
import moe.xmcn.catsero.utils.HttpUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;

public class Updater {

    public final String name;
    public final String durl;

    public Updater(String name, String durl) {
        this.name = name;
        this.durl = durl;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(name).append(" ")
                .append(durl)
                .toString();
    }

    public static void onEnable(String nowversion) {
        Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
        File usc = new File(plugin.getDataFolder(), "usesconfig.yml");
        FileConfiguration usesconfig = YamlConfiguration.loadConfiguration(usc);
        if (usesconfig.getBoolean("check-update.enabled") && !nowversion.contains("pre")) {
            String datajson = HttpUtils.sendGet("https://csu.huahuo-cn.tk/api/updt.php", "UTF-8");
            Gson gson = new Gson();
            Updater updater = gson.fromJson(datajson, Updater.class);
            String[] upregex = String.valueOf(updater).split(" ");
            if (!Objects.equals(upregex[0], nowversion)) {
                System.out.println(ChatColor.GREEN + "已找到可用的更新：" + upregex[0]);
                System.out.println(ChatColor.GREEN + "下载地址：" + ChatColor.YELLOW + upregex[1]);
            }
        } else {
            System.out.println("跳过更新检查");
        }
    }
}
