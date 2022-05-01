package moe.xmcn.catsero.utils;


public class Punycode {
    private static final int TMIN = 1;
    private static final int TMAX = 26;
    private static final int BASE = 36;
    private static final int INITIAL_N = 128;
    private static final int INITIAL_BIAS = 72;
    private static final int DAMP = 700;
    private static final int SKEW = 38;
    private static final char DELIMITER = '-';

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