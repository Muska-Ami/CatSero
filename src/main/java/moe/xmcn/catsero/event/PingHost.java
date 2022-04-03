package moe.xmcn.catsero.event;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingHost implements Listener, CommandExecutor {

    Plugin plugin = moe.xmcn.catsero.Main.getPlugin(moe.xmcn.catsero.Main.class);
    public class Punycode {
        private static int TMIN = 1;
        private static int TMAX = 26;
        private static int BASE = 36;
        private static int INITIAL_N = 128;
        private static int INITIAL_BIAS = 72;
        private static int DAMP = 700;
        private static int SKEW = 38;
        private static char DELIMITER = '-';

        public static String encodeURL(String url) {
            if (!url.contains("."))
                return url;
            String mainContent = url.substring(0, url.lastIndexOf("."));
            String prefix = mainContent.contains(".") ? mainContent.substring(0, mainContent.lastIndexOf(".") + 1) : "";
            if (mainContent.contains("."))
                mainContent = mainContent.substring(mainContent.lastIndexOf(".") + 1);
            mainContent = Punycode.encode(mainContent, "xn--");
            String suffix = url.substring(url.lastIndexOf("."));
            return prefix + mainContent + suffix;
        }

        /**
         *
         * Punycodes a unicode string. THIS IS NOT SUITABLE FOR UNICODE AND LETTER
         * MIXING
         *
         * @param input Unicode string.
         *
         * @return Punycoded string, but original text for throw an exception
         *
         */
        public static String encode(String input) {
            return Punycode.encode(input, "");
        }

        /**
         *
         * Punycodes a unicode string. THIS IS NOT SUITABLE FOR UNICODE AND LETTER
         * MIXING
         *
         * @param input Unicode string.
         *
         * @return Punycoded string, but original text for throw an exception
         *
         */
        public static String encode(String input, String successPrefix) {
            int n = INITIAL_N;
            int delta = 0;
            int bias = INITIAL_BIAS;
            StringBuilder output = new StringBuilder();
            int b = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (isBasic(c)) {
                    output.append(c);
                    b++;
                }
            }
            if(b >= input.length()) return output.toString();
            if (b > 0) {
                output.append(DELIMITER);
            }
            int h = b;
            while (h < input.length()) {
                int m = Integer.MAX_VALUE;
                for (int i = 0; i < input.length(); i++) {
                    int c = input.charAt(i);
                    if (c >= n && c < m) {
                        m = c;
                    }
                }
                if (m - n > (Integer.MAX_VALUE - delta) / (h + 1)) {
                    return input;
                }
                delta = delta + (m - n) * (h + 1);
                n = m;
                for (int j = 0; j < input.length(); j++) {
                    int c = input.charAt(j);
                    if (c < n) {
                        delta++;
                        if (0 == delta) {
                            return input;
                        }
                    }
                    if (c == n) {
                        int q = delta;
                        for (int k = BASE;; k += BASE) {
                            int t;

                            if (k <= bias) {
                                t = TMIN;
                            } else if (k >= bias + TMAX) {
                                t = TMAX;
                            } else {
                                t = k - bias;
                            }
                            if (q < t) {
                                break;
                            }
                            output.append((char) digit2codepoint(t + (q - t) % (BASE - t)));
                            q = (q - t) / (BASE - t);
                        }
                        output.append((char) digit2codepoint(q));
                        bias = adapt(delta, h + 1, h == b);
                        delta = 0;
                        h++;
                    }
                }
                delta++;
                n++;
            }
            output.insert(0, successPrefix);
            return output.toString();
        }

        private static int adapt(int delta, int numpoints, boolean first) {
            if (first) {
                delta = delta / DAMP;
            } else {
                delta = delta / 2;
            }
            delta = delta + (delta / numpoints);
            int k = 0;
            while (delta > ((BASE - TMIN) * TMAX) / 2) {
                delta = delta / (BASE - TMIN);
                k = k + BASE;
            }
            return k + ((BASE - TMIN + 1) * delta) / (delta + SKEW);
        }

        private static boolean isBasic(char c) {
            return c < 0x80;
        }

        private static int digit2codepoint(int d) {
            if (d < 26) {
                return d + 'a';
            } else if (d < 36) {
                return d - 26 + '0';
            } else {
                return d;
            }
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (plugin.getConfig().getString("general.ext-pinghost.enabled") == "true") {
            if (args.length > 0 && args[0].equalsIgnoreCase("ping")) {
                if (args.length == 2) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&aPing进行中，请耐心等待..."));
                    InetAddress address = null;
                    try {
                        address = InetAddress.getByName(new Punycode().encodeURL(args[1]));
                    } catch (UnknownHostException e) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c无法解析主机名/IP"));
                    }
                    int flag = 0;
                    for (int i = 0; i < 4; i++) {
                        boolean b = false;
                        try {
                            b = address.isReachable(1000);
                        } catch (IOException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                        }
                        if (b)
                            flag++;
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&cPing时发生错误"));
                        }
                    }
                    sender.sendMessage(args[1] + "(" + (new Punycode().encodeURL(args[1])) + ")" + " 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = " + flag + " ,丢失 = " + (4 - flag) + "(" + (4 - flag) / 4 * 100 + "% 丢失)");
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e[&bCatSero&e]&c请键入正确的地址"));
                }
            }
        }

        /**
        @EventHandler
        public void onGroupMessageReceive(MiraiGroupMessageEvent e) {
        String Colors="Red,Black,White,Yellow,Blue";
        String[] arr1=Colors.split(",");    //不限制元素个数
        String[] arr2=Colors.split(",",3);    //限制元素个数为3
        if (e.getBotID() == plugin.getConfig().getString("bot")) {

        }
        MiraiBot.getBot(e.getBotID()).getGroup(e.getGroupID()).sendMessage(address.getHostAddress()+" 的  Ping 统计信息：\n   数据包：已发送 = 4， 已接收 = "+flag+" ,丢失 = "+(4-flag)+"("+(4-flag)/4*100+"% 丢失)");
        }
        */
        return false;
    }
}