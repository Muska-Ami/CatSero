package moe.xmcn.catsero.event.listener.ChatForward;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static String removeColorCode(String string) {
        Set<String> s = new HashSet<>(Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "k", "l", "o", "r"));
        for (int i = 0; i < s.toArray().length; i++) {
            string = string.replace("§" + s.toArray()[i], "");
        }
        return string;
    }
}
