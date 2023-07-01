package moe.xmcn.catsero.v3.util

import me.clip.placeholderapi.PlaceholderAPI
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface PAPI {

    companion object {

        /**
         * 转换Placeholders
         *
         * @param s 需要转换的文本
         * @param player 玩家
         *
         * @return 已转换的文本
         */
        fun transPlaceholders(s: String, player: Player): String {
            return PlaceholderAPI.setBracketPlaceholders(player, s)
        }

        /**
         * 转换Placeholders
         *
         * @param s 需要转换的文本
         * @param player 玩家
         *
         * @return 已转换的文本
         */
        fun transPlaceholders(s: String, player: CommandSender): String {
            return PlaceholderAPI.setBracketPlaceholders(player as Player, s)
        }

    }

}