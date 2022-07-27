package moe.xmcn.catsero.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;


/**
 * @author XiaMoHuaHuo_CN
 */
public class HttpUtils {


    /**
     * 使用Get方式获取数据
     *
     * @param url URL包括参数
     */
    public static String sendGet(String url, String charset) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(), charset));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            Config.plugin.getLogger().warning(Config.getMsgByMsID("general.http-utils.send-get-error").replace("%error%", e + Arrays.toString(e.getStackTrace())));
            result.append("undefined");
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                Config.plugin.getLogger().warning(Config.getMsgByMsID("general.http-utils.send-get-error").replace("%error%", e2 + Arrays.toString(e2.getStackTrace())));
            }
        }
        return result.toString();
    }

}