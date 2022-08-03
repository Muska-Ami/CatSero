package moe.xmcn.catsero.utils

import org.bukkit.ChatColor

class HelpList {
    companion object {
        fun getList(how: String): String? {
            return when (how) {
                "mc" -> {
                    helpListMC()
                }
                "qq" -> {
                    helpListQQ()
                }
                else -> {
                    null
                }
            }
        }

        private fun helpListMC(): String {
            val list = """
            &a----&b*&a-&b*&c---&e##==&r &b&nCatSero&r &l&9Help&r &e==##&c---&b*&a-&b*&a----&r
            &3/catsero help&r
                &d显示此帮助&r
            &3/catsero ping <地址>&r
                &dPing某一个地址&r
            &3/catsero weather <中国大陆城市>&r
                &d获取某个城市的天气&r
            &3/catsero punycode <文本> [urlmode]&r
                &dPunycode文本 [urlmode]:URL模式&r
            &3/catsero reload&r
                &d重载配置文件&r
            &3/catsero update&r
                &d检查更新&r
            &3/cms send <消息>&r
                &d发送群消息&r
            &3/cms sendcustom <机器人号> <群号> <消息>&r
                &d使用指定机器人向指定服务器发送消息&r
            &a----&b*&a-&b*&c---&e##==&r &l++++++++++++++++++++&r &e==##&c---&b*&a-&b*&a----&r
        """
            return ChatColor.translateAlternateColorCodes('&', list.trimIndent())
        }

        private fun helpListQQ(): String {
            val list = """
            ----##== CatSero Help ==##----
            catsero help
                显示此帮助
            catsero ping <地址>
                Ping某一个地址
            catsero weather <中国大陆城市>
                获取某个城市的天气
            catsero punycode <文本> [urlmode]
                Punycode文本 [urlmode]:URL模式
            catsero setop <玩家名>
                将玩家设为OP
            catsero removeop <玩家名>
                移除玩家OP
            catsero kick <玩家名>
                踢出一个玩家
            catsero ban <玩家名>
                封禁一个玩家
            catsero unban <玩家名>
                解封一个玩家
            catsero tps
                获取服务器TPS
            ----##== ++++++++++++ ==##----
        """
            return list.trimIndent()
        }
    }
}