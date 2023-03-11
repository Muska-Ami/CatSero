package moe.xmcn.catsero;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import moe.xmcn.catsero.utils.Logger;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class I18n {

    private JSONObject i18nJSON;
    private final CatSero INSTANCE = CatSero.INSTANCE;

    public I18n() {
        try {
            i18nJSON = JSON.parseObject(Files.readAllLines(Paths.get(URI.create(
                    INSTANCE.getDataFolder() + "/locale/" + Configuration.getPlugin().getString("locale")
            ))).toString());
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    public String getI18n(ArrayList<String> arr) {

        JSONObject lastObject = new JSONObject();
        String result = "undefined";

        try {
            for (int i = 0; i < arr.size(); i++) {
                if (i != arr.size() - 1) {
                    lastObject = i18nJSON.getJSONObject(arr.get(i));
                } else {
                    result = lastObject.getString(arr.get(i));
                }
            }
        } catch (Exception e) {
            Logger.logWARN("读取I18n文件时发生错误，请检查JSON文件！");
            Logger.logCatch(e);
        }
        return result;
    }

}
