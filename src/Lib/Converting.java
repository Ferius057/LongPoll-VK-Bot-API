package Lib;

public class Converting {

    public static String convertEncoding(String s, String encoding) {

        String out = null;

        try {
            out = new String(s.getBytes(encoding), "UTF-8");
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }

        return out;
    }
}
