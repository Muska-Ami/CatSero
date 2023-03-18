package moe.xmcn.catsero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xmcn.catsero.utils.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class I18n {

    private final CatSero INSTANCE = CatSero.INSTANCE;
    private JSONObject i18nJSON;

    public I18n() {
        try {
            String locale;
            locale = Objects.requireNonNull(Configuration.getPlugin().getString("locale"), "zh_CN");

            InputStreamReader isr = new InputStreamReader(Files.newInputStream(Paths.get(INSTANCE.getDataFolder() + "/locale/" + locale + ".json")), StandardCharsets.UTF_8);
            BufferedReader in = new BufferedReader(isr);
            String body;
            StringBuilder data = new StringBuilder();
            while ((body = in.readLine()) != null) {
                data.append(body);
            }

            i18nJSON = JSON.parseObject(data.toString());
            //i18nJSON = JSON.parseObject(Files.readAllLines(Paths.get(URI.create())).toString());
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    public String getI18n(ArrayList<String> arr) {

        JSONObject lastObject = i18nJSON;
        String result = "undefined";

        try {
            for (int i = 0; i < arr.size(); i++) {
                if (i != arr.size() - 1) {
                    lastObject = lastObject.getJSONObject(arr.get(i));
                    Logger.logDebug("I18n JSON对象: " + lastObject.toString());
                } else {
                    result = lastObject.getString(arr.get(i));
                    Logger.logDebug("I18n 返回结果: " + result);
                }
            }
        } catch (Exception e) {
            Logger.logWARN("读取I18n文件时发生错误，请检查JSON文件！");
            Logger.logCatch(e);
        }
        return result;
    }

}
