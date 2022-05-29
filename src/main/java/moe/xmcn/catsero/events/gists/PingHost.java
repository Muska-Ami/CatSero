package moe.xmcn.catsero.events.gists;

import moe.xmcn.catsero.utils.Punycode;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingHost {
    public static String GameUtils(String ars) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(Punycode.encodeURL(ars));
        int flag = 0;
        for (int i = 0; i < 4; i++) {
            boolean b;
            try {
                assert address != null;
                b = address.isReachable(1000);
            } catch (IOException e) {
                return "Error";
            }
            if (b)
                flag++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return "Error";
            }
        }
        return String.valueOf(flag);
    }
}
