package moe.xmcn.catsero.utils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
 
import org.yaml.snakeyaml.Yaml;
 
import lombok.extern.slf4j.Slf4j;
 
@Slf4j
public class YamlReader {
 
	private static Map<String, Map<String, Object>> properties = new HashMap<>();
 
	/**
	 * 单例
	 */
	public static final YamlReader instance = new YamlReader();
 
	static {
		Yaml yaml = new Yaml();
		try (InputStream in = YamlReader.class.getClassLoader().getResourceAsStream("bstats.yml");) {
			properties = yaml.loadAs(in, HashMap.class);
		} catch (Exception e) {
			log.error("Init yaml failed !", e);
		}
	}
 
	/**
	 * get yaml property
	 * 
	 * @param key
	 * @return
	 */
	public Object getValueByKey(String key) {
		String separator = ".";
		String[] separatorKeys = null;
		if (key.contains(separator)) {
			separatorKeys = key.split("\\.");
		} else {
			return properties.get(key);
		}
		Map<String, Map<String, Object>> finalValue = new HashMap<>();
		for (int i = 0; i < separatorKeys.length - 1; i++) {
			if (i == 0) {
				finalValue = (Map) properties.get(separatorKeys[i]);
				continue;
			}
			if (finalValue == null) {
				break;
			}
			finalValue = (Map) finalValue.get(separatorKeys[i]);
		}
		return finalValue == null ? null : finalValue.get(separatorKeys[separatorKeys.length - 1]);
	}
}
