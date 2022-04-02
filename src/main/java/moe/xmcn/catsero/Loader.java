package moe.xmcn.catsero;

import moe.xmcn.catsero.utils.Metrics;

import moe.xmcn.catsero.bStats;
import moe.xmcn.catsero.villa.Main;
import moe.xmcn.catsero.extra.chatForward.BukkitPlugin;

public class Loader extends JavaPlugin implements Listener {

    @Override // 加载插件
    public void onLoad() {

        public static boolean isExcite() {
        File file = new File("CatSero/bstats.yml");
        // 如果文件夹不存在则创建
        if (!file.exists()) {
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', "[CatSero] &e初始化bStats设置"));
            InputStream is = Loader.class.getResourceAsStream("../../../../resources/Conf/bstats.yml");
            File f = new File("CatSero/bstats.yml");
            File fp = new File(f.getParent());
	    f.createNewFile();
            OutputStream os = new FileOutputStream(f);
            int index = 0;
            byte[] bytes = new byte[1024];
            while ((index = is.read(bytes)) != -1) {
	        os.write(bytes, 0, index);
            }

           os.flush();
           os.close();
           is.close();
        }
        
    }

}
