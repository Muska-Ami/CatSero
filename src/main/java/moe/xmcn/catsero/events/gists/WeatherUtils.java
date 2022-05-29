package moe.xmcn.catsero.events.gists;

import com.google.gson.Gson;
import moe.xmcn.catsero.utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WeatherUtils {

    public final String date;
    public final String fengli;
    public final String high;
    public final String low;
    public final String fengxiang;
    public final String type;

    public WeatherUtils(String date, String fengli, String high, String low, String fengxiang, String type) {
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
        Gson gson = new Gson();
        WeatherUtils weatherutils = gson.fromJson(datajson, WeatherUtils.class);
        return String.valueOf(weatherutils).split("╳");
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
