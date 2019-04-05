package Lib;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;
import org.json.JSONObject;

/**
 * Класс работы c запросами.
 * @autor daniilak
 * @version 1.1
 */
public class Request {

    /**
     * Функция отправки запросов метотов VK API, см vk.com/dev/methods
     * @param method - название метода
     * @param params - параметры запроса
     * @return JSONObject - объект JSON формата
     */
    public static JSONObject query(String method, String params) {
        JSONObject object = new JSONObject(
                Objects.requireNonNull(Request.post("https://api.vk.com/method/" + method, params) ) );

        if (object.has("error")) {
            System.out.println(object);
        }

        return object;
    }

    /**
     * Функция отправки запроса LongPoll серверу
     * @param req_url - URL запроса
     * @return String - JSON строка
     */
    public static String postLongPoll(String req_url) {
        try {
            URL url = new URL(req_url);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            String inputLine;
            String result = "";
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));

            while ((inputLine = reader.readLine()) != null) {
                result += inputLine;
            }
            reader.close();
            return result;
        } catch (UnsupportedEncodingException e)  {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "fail";
    }

    /**
     * Функция отправки запроса серверу VK API
     * @param req_url - URL запроса
     * @param params - параметры запроса
     * @return String - JSON строка
     */
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));

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
}
