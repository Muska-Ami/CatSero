package moe.xmcn.catsero;

import com.google.gson.Gson;
import moe.xmcn.catsero.utils.HttpUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class Updater {

    public final String name;
    public final String durl;
    public final String devname;
    public final String devdurl;

    public Updater(String name, String durl, String devname, String devdurl) {
        this.name = name;
        this.durl = durl;
        this.devname = devname;
        this.devdurl = devdurl;
    }

    public static void onEnable(String nowversion) {
        Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
        if (plugin.getConfig().getBoolean("check-update.enabled")) {
            String datajson = HttpUtils.sendGet("https://csu.huahuo-cn.tk/api/updt.php", "UTF-8");
            Gson gson = new Gson();
            Updater updater = gson.fromJson(datajson, Updater.class);
            String[] upregex = String.valueOf(updater).split("╳");
            if (Objects.equals(plugin.getConfig().getString("check-update.version"), "dev")) {
                System.out.println(ChatColor.GREEN + "最新构建ID：" + upregex[2]);
                System.out.println(ChatColor.GREEN + "下载地址：" + ChatColor.YELLOW + upregex[3]);
            } else if (!Objects.equals(upregex[0], nowversion) && !nowversion.contains("pre") && Objects.equals(plugin.getConfig().getString("check-update.version"), "passed")) {
                System.out.println(ChatColor.GREEN + "已找到可用的更新：" + upregex[0]);
                System.out.println(ChatColor.GREEN + "下载地址：" + ChatColor.YELLOW + upregex[1]);
            } else {
                System.out.println("跳过更新检查");
            }
        } else {
            System.out.println("跳过更新检查");
        }
    }

    @Override
    public String toString() {
        return name + "╳" +
                durl + "╳" +
                devname + "╳" +
                devdurl + "╳";
    }
}
