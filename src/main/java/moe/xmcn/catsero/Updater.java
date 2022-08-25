/*
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright © 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero;

import com.google.gson.Gson;
import moe.xmcn.catsero.util.Config;
import moe.xmcn.catsero.util.HttpUtils;
import org.bukkit.ChatColor;

import java.util.Objects;
import java.util.logging.Level;

import static java.lang.Thread.sleep;

/**
 * 检查更新器
 */
public class Updater {

    private static boolean y = false;
    private final String name;
    private final String durl;
    private final String devname;
    private final String devdurl;

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
            if (datajson.equals("undefined")) {
                if (color) {
                    return ChatColor.RED + "无法与服务器建立连接";
                } else {
                    return "无法与服务器建立连接";
                }
            } else {
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
                        return "设定版本有误，请求更新失败！";
                    } else {
                        return "已是最新版本";
                    }
                }
            }
        } else {
            return "跳过更新检查";
        }
    }

    public static void startTimerUpdateCheck() throws InterruptedException {
        sleep(1000);
        String nowversion = Config.PluginInfo.getString("version");
        String versiontype = Config.Config.getString("check-update.version");
        String datajson = HttpUtils.sendGet("https://csu.huahuo-cn.tk/api/updt.php", "UTF-8");
        if (!datajson.equals("undefined")) {
            // 得到数据，解析
            Gson gson = new Gson();
            Updater updater = gson.fromJson(datajson, Updater.class);
            String[] upregex = String.valueOf(updater).split("╳");
            boolean msea = Objects.equals(nowversion, "dev");
            boolean mseb = !Objects.equals(upregex[0], nowversion) && Objects.equals(versiontype, "passed");

            if (!y) {
                if (msea) {
                    Config.plugin.getLogger().log(Level.INFO, "最新构建ID：" + upregex[2] + "下载地址：" + upregex[3]);
                    Updater.y = true;
                } else if (mseb) {
                    Config.plugin.getLogger().log(Level.INFO, "已找到可用的更新：" + upregex[0] + "下载地址：" + upregex[1]);
                    Updater.y = true;
                }
            }
        }
    }

    @Override
    public String toString() {
        return name + "╳" + durl + "╳" + devname + "╳" + devdurl + "╳";
    }

}
