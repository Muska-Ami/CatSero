package moe.xmcn.catsero.utils;

public interface QCommandParser {

    class getParser {

        /**
         * 解析QQ群命令
         * @param message   消息
         * @return  数组或null
         */
        public static String[] parse(String message) {
            String pmh = checkCommandHeader(message);
            if (pmh != null) {
                return pmh.split(" ");
            } else {
                return null;
            }
        }

        /**
         * 判断是否有命令头，并处理命令头
         * @param message   消息
         * @return  处理后的消息或null
         */
        private static String checkCommandHeader(String message) {
            if (message.startsWith("!catsero")) {
                //解析命令头(!)
                message = message.replaceFirst("!catsero ", "");
                return message;
            } else if (message.startsWith("/catsero")) {
                //解析命令头(/)
                message = message.replaceFirst("/catsero ", "");
                return message;
            }
            return null;
        }

    }

}
