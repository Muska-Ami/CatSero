package moe.xmcn.catsero;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatExample implements Listener {

    /**
     * 事件监听
     * AsyncPlayerChatEvent
     */
    @EventHandler
    public void onChat(AsyncPlayerChatEvent apce) {
        // 取消事件
        apce.setCancelled(true);
    }

}
