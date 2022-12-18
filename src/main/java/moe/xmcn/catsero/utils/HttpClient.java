package moe.xmcn.catsero.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpClient {

    public String getRequest(String url) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlName = url;
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //建立实际的连接
            conn.connect();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in .readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        //使用finally块来关闭输入流
        finally {
            try {
                if ( in != null) { in .close();
                }
            } catch (Exception ex) {
                Logger.logCatch(ex);
            }
        }
        return result.toString();
    }
    public String getRequest(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlName = url + "?" + param;
            URL realUrl = new URL(urlName);
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            conn.setRequestProperty("accept", "charset=UTF-8");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            //建立实际的连接
            conn.connect();
            //定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in .readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Logger.logCatch(e);
        }
        //使用finally块来关闭输入流
        finally {
            try {
                if ( in != null) { in .close();
                }
            } catch (Exception ex) {
                Logger.logCatch(ex);
            }
        }
        return result.toString();
    }

}
