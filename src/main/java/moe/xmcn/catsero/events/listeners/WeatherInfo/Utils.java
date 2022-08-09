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
package moe.xmcn.catsero.events.listeners.WeatherInfo;

import com.google.gson.Gson;
import moe.xmcn.catsero.utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Utils {

    private final String date;
    private final String fengli;
    private final String high;
    private final String low;
    private final String fengxiang;
    private final String type;

    private Utils(String date, String fengli, String high, String low, String fengxiang, String type) {
        this.date = date;
        this.fengli = fengli;
        this.high = high;
        this.low = low;
        this.fengxiang = fengxiang;
        this.type = type;
    }

    public static String[] getWeather(String cn) throws UnsupportedEncodingException {
        String cityname = URLEncoder.encode(cn, "UTF-8");
        String weather_url = "https://csu.huahuo-cn.tk/api/wthr.php?city=" + cityname;
        String datajson = HttpUtils.sendGet(weather_url, "UTF-8");
        if (datajson.equals("undefined")) {
            return ("无法与服务器建立连接").split("-");
        } else {
            Gson gson = new Gson();
            Utils weatherutils = gson.fromJson(datajson, Utils.class);
            return String.valueOf(weatherutils).split("╳");
        }
    }

    @Override
    public String toString() {
        return date + "╳" +
                high + "~" + low + "╳" +
                fengli + "╳" +
                fengxiang + "╳" +
                type;
    }
}
