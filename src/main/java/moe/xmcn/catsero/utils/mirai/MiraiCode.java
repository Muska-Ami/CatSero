package moe.xmcn.catsero.utils.mirai;

public interface MiraiCode {
    /**
     * 普通文本相关
     */
    class Text {
        /**
         * Mirai:At
         * @param code QQ号
         */
        public String mAt(long code) {
            return "[mirai:at:" + code + "]";
        }
        
        /**
         * Mirai:AtAll
         */
        public String mAtAll() {
            return "[mirai:atall]";
        }
        
        /**
         * Mirai:Face
         */
        public String mFace(String id) {
            return "[mirai:face:" + id + "]"
        }
        
        /**
    }
    
    class Media {
        /**
         * Mirai:Image
         */
        public String mImage(String id) {
            return "[mirai:image:{" + id + "}]";
        }
        /**
         * Mirai:Flash
         */
        public String mFlash(String id) {
            return "[mirai:flash:" + id + "]";
        }
        /**
         * Mirai:PokeMessage
         */
        public String mPokeMessage(String type) {
            String msn = null;
            switch (type) {
                case 1:
                    msn = "戳一戳";
                    break;
                case 2:
                    msn = "比心";
                    break;
                case 3:
                    msn = "点赞";
                    break;
                case 4:
                    msn = "心碎";
                    break;
                case 5:
                    msn = "666";
                    break;
            }
            return "[mirai:poke:" + msn + "," +  type + ",-1]";
        }
    }
}
