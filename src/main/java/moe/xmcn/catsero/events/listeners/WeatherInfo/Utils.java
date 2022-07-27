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
