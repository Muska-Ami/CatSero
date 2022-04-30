package moe.xmcn.catsero.utils;

import com.google.gson.Gson;

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

    @Override
    public String toString() {
        return new StringBuilder()
                .append(date).append(" ")
                .append(high).append("~").append(low).append(" ")
                .append(fengli).append(" ")
                .append(fengxiang).append(" ")
                .append(type)
                .toString();
    }

    public static String[] getWeather(String cityname) throws UnsupportedEncodingException {
        cityname = URLEncoder.encode(cityname, "UTF-8");
        String weather_url = "https://csu.huahuo-cn.tk/api/wthr.php?city="+cityname;
        String datajson = HttpUtils.sendGet(weather_url, "UTF-8");
        Gson gson = new Gson();
        WeatherUtils weatherutils = gson.fromJson(datajson, WeatherUtils.class);
        String[] wturegex = String.valueOf(weatherutils).split(" ");
        return wturegex;
    }
}
