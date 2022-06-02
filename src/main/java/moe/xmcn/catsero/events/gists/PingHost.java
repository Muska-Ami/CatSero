package moe.xmcn.catsero.events.gists;

import moe.xmcn.catsero.utils.Punycode;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class PingHost {
    /**
     * 向某个地址发包，并收取包
     *
     * @param ars 地址
     * @return 收回的包
     * @throws UnknownHostException 未知主机
     */
    public static String PingHostUtils(String ars) throws UnknownHostException {
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
