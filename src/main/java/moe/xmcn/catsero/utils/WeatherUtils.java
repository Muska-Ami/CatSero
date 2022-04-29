package moe.xmcn.catsero.utils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

/**
 * 通过get请求向网站http://wthrcdn.etouch.cn/weather_mini获取某个 城市的天气状况数据，数据格式是Json
 * 
 * @author 22786
 * 
 */
public class WeatherUtils {
	/**
	 * 通过城市名称获取该城市的天气信息
	 * 
	 * @param cityname
	 * @return
	 */
	
	public static String GetWeatherData(String cityname) {
		StringBuilder sb=new StringBuilder();;
		try {
			cityname = URLEncoder.encode(cityname, "UTF-8");
			String weather_url = "http://wthrcdn.etouch.cn/weather_mini?city="+cityname;
			
 
			URL url = new URL(weather_url);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			GZIPInputStream gzin = new GZIPInputStream(is);
			InputStreamReader isr = new InputStreamReader(gzin, "utf-8"); // 设置读取流的编码格式，自定义编码
			BufferedReader reader = new BufferedReader(isr);
			String line = null;
			while((line=reader.readLine())!=null)
				sb.append(line+" ");
			reader.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(sb.toString());
		return sb.toString();
		
	}

}