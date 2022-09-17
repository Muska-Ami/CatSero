package moe.xmcn.catsero.v2.utils;

public interface QPS {

    class getParser {

        /**
         * 解析QQ群命令
         *
         * @param message 消息
         * @return 数组或null
         */
        public static String[] parse(String message) {
            String pmhv = checkViaCommandHeader(message);
            String pmhc = checkCustomCommandHeader(message);
            if (pmhv != null) {
                return pmhv.split(" ");
            } else if (
                    Configs.JPConfig.config.getBoolean("custom-qq-command-head.enabled") &&
                            pmhc != null
            ) {
                return pmhc.split(" ");
            } else {
                return null;
            }
        }

        /**
         * 判断是否有命令头，并处理命令头
         *
         * @param message 消息
         * @return 处理后的消息或null
         */
        private static String checkViaCommandHeader(String message) {
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

        /**
         * 判断是否有命令头，并处理命令头
         *
         * @param message 消息
         * @return 处理后的消息或null
         */
        private static String checkCustomCommandHeader(String message) {
            String custom_head = Configs.JPConfig.config.getString("format-list.custom-command-head.prefix");
            if (message.startsWith("!" + custom_head)) {
                //解析命令头(!)
                message = message.replaceFirst("!" + custom_head + " ", "");
                return message;
            } else if (message.startsWith("/" + custom_head)) {
                //解析命令头(/)
                message = message.replaceFirst("/" + custom_head + " ", "");
                return message;
            }
            return null;
        }

    }

}
