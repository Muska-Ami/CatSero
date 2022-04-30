package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.HttpUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Updater {
    public static void onEnable(String nowversion) {
        String datajson = HttpUtils.sendGet("https://api.github.com/repos/XiaMoHuaHuo-CN/CatSero/releases", "UTF-8");
        String relcatchurlA = ".*\"browser_download_url\": \"(.*)\".*";
        Pattern relcatchurlB = Pattern.compile(relcatchurlA);
        Matcher url = relcatchurlB.matcher(datajson);
        String relcatchnameA = "\"name\": \"(.*)\"";
        Pattern relcatchnameB = Pattern.compile(relcatchnameA);
        Matcher name = relcatchnameB.matcher(datajson);
        //System.out.println(datajson);
        if (name.find()) {
            System.out.println(name.group(0));
        } else {
            System.out.println("Not Found Name");
        }
        if (url.find()) {
            System.out.println(url.group(0));
        } else {
            System.out.println("Not Found Download Url");
        }
    }
}
