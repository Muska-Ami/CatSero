package moe.xmcn.catsero;

import com.google.gson.Gson;
import moe.xmcn.catsero.utils.Config;
import moe.xmcn.catsero.utils.HttpUtils;
import org.bukkit.ChatColor;

import java.util.Objects;

/**
 * 检查更新器
 */
public class Updater {

    public final String name;
    public final String durl;
    public final String devname;
    public final String devdurl;

    private Updater(String name, String durl, String devname, String devdurl) {
        this.name = name;
        this.durl = durl;
        this.devname = devname;
        this.devdurl = devdurl;
    }

    public static String startUpdateCheck(boolean color) {
        String nowversion = Config.PluginInfo.getString("version");
        String versiontype = Config.Config.getString("check-update.version");
        if (Config.plugin.getConfig().getBoolean("check-update.enabled")) {
            String datajson = HttpUtils.sendGet("https://csu.huahuo-cn.tk/api/updt.php", "UTF-8");
            Gson gson = new Gson();
            Updater updater = gson.fromJson(datajson, Updater.class);
            String[] upregex = String.valueOf(updater).split("╳");
            boolean msea = Objects.equals(nowversion, "dev");
            boolean mseb = !Objects.equals(upregex[0], nowversion) && Objects.equals(versiontype, "passed");
            boolean msec = !Objects.equals(versiontype, "passed") && !Objects.equals(versiontype, "dev");
            if (color) {
                if (msea) {
                    return ChatColor.GREEN + "最新构建ID：" + upregex[2] + ChatColor.GREEN + "下载地址：" + ChatColor.YELLOW + upregex[3];
                } else if (mseb) {
                    return ChatColor.GREEN + "已找到可用的更新：" + upregex[0] + ChatColor.GREEN + "下载地址：" + ChatColor.YELLOW + upregex[1];
                } else if (msec) {
                    return ChatColor.RED + "版本有误/请求更新失败！";
                } else {
                    return "已是最新版本";
                }
            } else {
                if (msea) {
                    return "最新构建ID：" + upregex[2] + "下载地址：" + upregex[3];
                } else if (mseb) {
                    return "已找到可用的更新：" + upregex[0] + "下载地址：" + upregex[1];
                } else if (msec) {
                    return "版本有误/请求更新失败！";
                } else {
                    return "已是最新版本";
                }
            }
        } else {
            return "跳过更新检查";
        }
    }

    @Override
    public String toString() {
        return name + "╳" + durl + "╳" + devname + "╳" + devdurl + "╳";
    }
}
