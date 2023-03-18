/*
 * CatSero v2
 * 此文件为 Minecraft服务器 插件 CatSero 的一部分
 * 请在符合开源许可证的情况下修改/发布
 * This file is part of the Minecraft Server plugin CatSero
 * Please modify/publish subject to open source license
 *
 * Copyright 2022 XiaMoHuaHuo_CN.
 *
 * GitHub: https://github.com/XiaMoHuaHuo-CN/CatSero
 * License: GNU Affero General Public License v3.0
 * https://github.com/XiaMoHuaHuo-CN/CatSero/blob/main/LICENSE
 *
 * Permissions of this strongest copyleft license are
 * conditioned on making available complete source code
 * of licensed works and modifications, which include
 * larger works using a licensed work, under the same
 * license. Copyright and license notices must be preserved.
 * Contributors provide an express grant of patent rights.
 * When a modified version is used to provide a service over
 * a network, the complete source code of the modified
 * version must be made available.
 */
package moe.xmcn.catsero.utils;

import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.Set;

public class CommandSender implements ConsoleCommandSender {
    private static String bot;
    private static long friend;
    private final CommandSender instance;

    public CommandSender() {
        this.instance = this;
    }

    /**
     * 设置Bot
     *
     * @param bot bot
     */
    public static void setBot(String bot) {
        CommandSender.bot = bot;
    }

    /**
     * 设置friend
     *
     * @param friend friend
     */
    public static void setFriend(long friend) {
        CommandSender.friend = friend;
    }

    public Optional<org.bukkit.command.ConsoleCommandSender> get() {
        return Optional.of(Bukkit.getServer().getConsoleSender());
    }

    @Override
    public void sendMessage(String message) {
        try {
            MessageSender.sendFriend(ChatColor.stripColor(message), bot, friend);
        } catch (Exception e) {
            Logger.logCatch(e);
        }
    }

    @Override
    public void sendMessage(String[] messages) {
        for (String msg : messages) {
            sendMessage(msg);
        }
    }

    @Override
    public Server getServer() {
        return Bukkit.getServer();
    }

    @Override
    public String getName() {
        return "CONSOLE";
    }

    @Override
    public Spigot spigot() {
        return new Spigot() {
            public void sendMessage(BaseComponent component) {
                instance.sendMessage(component.toPlainText());
            }

            public void sendMessage(BaseComponent... components) {
                for (BaseComponent baseComponent : components) {
                    sendMessage(baseComponent);
                }
            }
        };
    }

    @Deprecated
    @Override
    public boolean isConversing() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void acceptConversationInput(String input) {
    }

    @Deprecated
    @Override
    public boolean beginConversation(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void abandonConversation(Conversation conversation) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void abandonConversation(Conversation conversation, ConversationAbandonedEvent details) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void sendRawMessage(String message) {
    }

    @Override
    public boolean isPermissionSet(String name) {
        return get().map(c -> c.isPermissionSet(name)).orElse(true);
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return get().map(c -> c.isPermissionSet(perm)).orElse(true);
    }

    @Override
    public boolean hasPermission(String s) {
        return get().map(c -> c.hasPermission(s)).orElse(true);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return get().map(c -> c.hasPermission(permission)).orElse(true);
    }

    @Deprecated
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value, int ticks) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void recalculatePermissions() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean isOp() {
        return true;
    }

    @Deprecated
    @Override
    public void setOp(boolean value) {
        throw new UnsupportedOperationException();
    }
}
