package Lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class Request {
    public static String post(String req_url, String params) {
        try {
            URL url = new URL(req_url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(params);
            writer.flush();
            String inputLine;
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((inputLine = reader.readLine()) != null) {
                result += inputLine;
            }
            writer.close();
            reader.close();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fail";
    }

    public static String postLongPoll(String req_url) {
        try {
            URL url = new URL(req_url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
//            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

//            writer.write(params);
//            writer.flush();
            String inputLine;
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            while ((inputLine = reader.readLine()) != null) {
                result += inputLine;
            }
//            writer.close();
            reader.close();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fail";
    }

    public static String get(String req_url) {
        try {
            URL url = new URL(req_url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);

            String inputLine;
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((inputLine = reader.readLine()) != null) {
                result += inputLine;
            }
            reader.close();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "fail";
    }
}
