package com.zf.weatherservice;
 
import com.zf.warther_ws.ArrayOfString;
import com.zf.warther_ws.WeatherWebService;
import com.zf.warther_ws.WeatherWebServiceSoap;
 
public class Test03 {
	
	public static void main(String[] args) {
		String cityname = e.getMessage();
		WeatherWebService wws = new WeatherWebService();
		WeatherWebServiceSoap wwsp = wws.getWeatherWebServiceSoap();
		
		ArrayOfString aos = wwsp.getWeatherbyCityName(cityname);
		
		for (String s : aos.getString()) {   
			//System.out.println(s);
			getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(s);
		}
		
	}
 
}
