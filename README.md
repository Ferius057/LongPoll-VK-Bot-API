
# Создание и интегрирование бота VK в группу через LongPoll VK API
### Создание сообщества-бота
- Для это нужно зайти в **Группы** -> **Создать сообщество**
- Выберите любой тип сообщества и введите название, тематику группы.

### Получение пользовательского ключа доступа
#### Первый способ
- Перейти на сайт [vkhost.github.io/scope](http://vkhost.github.io/scope/ "vkhost.github.io/scope") 
- В поле *Введите права доступа через запятую:* вводим **groups,offline**
- Нажмите *Готово*. Появится окно **Приложение VK API запрашивает доступ к Вашему аккаунту** . Нажмите *Разрешить*
- Скопируйте с адресной строки **access_token**
`https://oauth.vk.com/blank.html#access_token=ЗДЕСЬНУЖНЫЙВАМТОКЕН&expires_in=0&user_id=385818590`

#### Второй способ
- Перейти по этой ссылке: [ссылка](https://oauth.vk.com/authorize?client_id=3116505&scope=groups,offline&redirect_uri=https://oauth.vk.com/blank.html&display=page&response_type=token&revoke=1 "ссылка")
- Разрешить доступ
- Скопировать с адресной строки **access_token**

### Получение ключей доступа от сообщества
- Открыть сообщество
- Нажать кнопку **Управление**
- На открывшейся странице настроек, выбрать **Работа с API**
- Нажать кнопку **Создать ключ**
- Выбрать необходимые права для нового ключа доступа (желательно все)
- Подтвердить действие при помощи push-уведомления или SMS-кода
- Скопировать полученный API-ключ сообщества
- Перейти в раздел LongPoll API. Включить. Версия API 5.92. Выбрать типы событий (сообщения). 

### Включение поддержки сообщений сообщества и клавиатуры
- Открыть сообщество
- Нажать кнопку **Управление**
- Перейти в  **Сообщения** и включить их.
- Также включить **Возможности ботов** в **Сообщения** -> **Настройки для бота**.

### Работа с кодом
`Integer groupID ; // ID группы `

`String adminToken;  // пользовательский ключ доступа`

`String botToken;  // ключ доступа сообщества `

`VK object = new VK(groupID, adminToken, botToken);`

`object.getUpdates(90); // получение обновлений с LongPoll с задержкой 90 секунд`


#### Класс работы с VK API

```java
package VK;

import Entities.Server;
import Lib.Request;
import Objects.Keyboard;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;

/**
 * Класс работы c VK API.
 * @autor daniilak
 * @version 1.1
 */
public class VK {

    /** Поле номер сообщества */
    private Integer groupID;

    /** Поле объект LongPoll сервера */
    private Server srv;

    /** Поле пользовательский ключ доступа */
    private String userToken;

    /** Поле ключ доступа сообщества */
    private String botToken;

    /** Поле версия API VK */
    private double version = 5.92;

    /**
     * Конструктор - создание нового объекта работы с VK API
     * @param groupID - номер сообщества
     * @param userToken - пользовательский ключ доступа
     * @param botToken - ключ доступа сообщества
     */
    public VK(Integer groupID, String userToken, String botToken) {

        this.groupID = groupID;
        this.userToken = userToken;
        this.botToken = botToken;

        JSONObject srvTemp = this.getLongPollServer();
        this.srv  = new Server(srvTemp.getString("server"), srvTemp.getString("key"), srvTemp.getInt("ts"));
    }

    /**
     * Функция получения данных LongPoll сервера VK
     * @return возвращает JSON строку с данными LongPoll сервера VK
     */
    private JSONObject getLongPollServer() {
        return this.queryFromUser("groups.getLongPollServer", "group_id=" + this.groupID)
                .getJSONObject("response");
    }

    /**
     * Функция получения данных API при помощи пользовательского ключа
     * @return возвращает JSONObject
     */
    public JSONObject queryFromUser(String method, String params) {
        params = "access_token=" + this.userToken + "&" + "version=" + version + "&" + params;
        return Request.query(method,params);
    }

    /**
     * Функция получения
     * @return возвращает JSONObject
     */
    public JSONObject queryFromBot(String method, String params) {
        params = "access_token=" + this.botToken+ "&" + "version=" + version + "&" + params;
        return Request.query(method,params);
    }

    /**
     * Функция получения обновлений при помощи LongPoll
     * @param wait - задержка в секундах
     */
    public void getUpdates(Integer wait) {

        JSONObject json, rec;
        JSONArray updates;

        while (true) {
            json = new JSONObject(
                    Objects.requireNonNull(
                            Request.postLongPoll(this.srv.getServer()+ "?act=a_check&key=" + this.srv.getKey() + "&ts=" + this.srv.getTs() + "&wait=" + wait + "&mode=2&version=2")
                    )
            );


            if (json.has("failed")) {
                System.out.println("Response from VK API: failed");
                return;
            }

            updates = json.getJSONArray("updates");

            if (updates.length() != 0) {
                this.srv.setTs(json.getInt("ts"));
                for (int i = 0; i < updates.length(); ++i) {
                    rec = updates.getJSONObject(i);
                    this.parserType(rec.getJSONObject("object"), rec.getString("type"));
                }
            }
        }
    }

    /**
     * Функция парсера разных типов уведомлений сообщества, см. https://vk.com/dev/groups_events
     * @param object - JSONObject уведомления
     * @param type - тип уведомления, см
     */
    public void parserType(JSONObject object, String type) {
        switch (type) {
            case "message_typing_state":
                System.out.println("id" + object.getInt("from_id") + " is " + object.getString("state"));
                break;
            case "message_new":
                System.out.println("Сообщение «" + object.getString("text") + "» от id" + object.getInt("from_id") + " дата " + new java.util.Date((long)object.getInt("date")*1000));

                if (object.has("reply_message")) {
                    System.out.println("Есть пересланные сообщения");
                }
                if (object.has("payload")){
                    System.out.println("Пользователь нажал кнопку «" + object.getString("text") + "», с параметром «" + object.getString("payload") + "»");
                }

                /** Добавление клавиатуры */
                Keyboard keyboard = new Keyboard(false);
                keyboard.addButton("text button", "negative", "Красная кнопка");
                keyboard.addButton("text button", "positive", "Зеленая кнопка");
                keyboard.addButton("text button", "default", "Белая кнопка");
                keyboard.addButton("text button", "primary", "Синяя кнопка");

                /** Отправление запроса "Отправить сообщение" с параметрами в ответ польователю
                 * вместе с параметром keyboard*/
                this.queryFromBot("messages.send","user_id=" + object.getInt("from_id") + "&message="+ object.getString("text") + " пикачу&keyboard=" + keyboard.getJSON() );
                break;
            case "message_reply":
                System.out.println("Ответное сообщение «" + object.getString("text") + "» для id" + object.getInt("peer_id"));
                break;
            case "message_edit":
                break;
            case "message_allow":
                break;
            case "message_deny":
                break;
            case "photo_new":
                break;
            case "photo_comment_new":
                break;
            case "photo_comment_edit":
                break;
            case "photo_comment_restore":
                break;
            case "photo_comment_delete":
                break;
            case "audio_new":
                break;
            case "video_new":
                break;
            case "video_comment_new":
                break;
            case "video_comment_edit":
                break;
            case "video_comment_restore":
                break;
            case "video_comment_delete":
                break;
            case "wall_post_new":
                System.out.println("Новая запись «" + object.getString("text") + "» дата в UnixTime" + object.getInt("date"));
                break;
            case "wall_repost":
                break;
            case "wall_reply_new":
                break;
            case "wall_reply_edit":
                break;
            case "wall_reply_restore":
                break;
            case "wall_reply_delete":
                break;
            case "board_post_new":
                break;
            case "board_post_edit":
                break;
            case "board_post_restore":
                break;
            case "board_post_delete":
                break;
            case "market_comment_new":
                break;
            case "market_comment_edit":
                break;
            case "market_comment_restore":
                break;
            case "market_comment_delete":
                break;
            case "group_leave":
                break;
            case "group_join":
                break;
            case "user_block":
                break;
            case "user_unblock":
                break;
            case "poll_vote_new":
                break;
            case "group_officers_edit":
                break;
            case "group_change_settings":
                break;
            case "group_change_photo":
                break;
        }
        // Вывод содержимого json, которое присылает VK API в ответ на различные действия (см выше)
//        System.out.println(object);
    }
}

```

#### Класс работы с запросами

```java
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
```

#### Класс работы с данными LongPoll сервера

```java
package Entities;

/**
 * Класс работы с данными LongPoll сервера, см. https://vk.com/dev/bots_longpoll.
 * @autor daniilak
 * @version 1.1
 */
public class Server {

    /** Поле секретный ключ сессии */
    String key;

    /** Поле адрес сервера */
    String server;

    /** Поле номер последнего события, начиная с которого нужно получать данные */
    Integer ts;

    /**
     * Конструктор - создание нового объекта LongPoll сервера
     * @param server - адрес сервера
     * @param key - секретный ключ сессии
     * @param ts - номер последнего события
     */
    public Server(String server, String key, Integer ts) {
        this.server = server;
        this.key = key;
        this.ts = ts;
    }

    /**
     * Сеттер секретного ключа сессии
     * @param key - секретный ключ сессии
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Сеттер секретного ключа сессии
     * @param server - адрес сервера
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * Сеттер секретного ключа сессии
     * @param ts - номер последнего события
     */
    public void setTs(Integer ts) {
        this.ts = ts;
    }

    /**
     * Геттер секретного ключа сессии
     * @return секретный ключ сессии
     */
    public String getKey() {
        return key;
    }

    /**
     * Геттер адреса сервера
     * @return адрес сервера
     */
    public String getServer() {
        return server;
    }

    /**
     * Геттер номера последнего события
     * @return номер последнего события
     */
    public Integer getTs() {
        return ts;
    }
}
``` 

#### Класс работы с объектом клавиатуры, см. https://vk.com/dev/bots_docs

```java
package Objects;

import Objects.Button;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Класс работы с объектом клавиатуры, см. https://vk.com/dev/bots_docs
 * @autor daniilak
 * @version 1.1
 */
public class Keyboard {

    Boolean oneTime;

    JSONArray buttons;

    JSONArray listButtons;

    public Keyboard(Boolean oneTime) {
        this.oneTime =  oneTime;
        this.buttons = new JSONArray();
        this.listButtons = new JSONArray();
    }

    public void addButton(String text, String color, String label) {
        Button btn = new Button(text,color,label);

        JSONObject btnObj = new JSONObject();
        btnObj.put("action", btn.getJSON());
        btnObj.put("color", color);
        this.buttons.put(btnObj);
    }

    public JSONObject getJSON() {

        JSONObject res = new JSONObject();
        res.put("one_time", this.oneTime);

        if (buttons.length() == 0) {
            res.put("buttons", "[]");
        } else {
            this.listButtons.put(buttons);
            res.put("buttons", this.listButtons);
        }
        return res;
    }

}
```


#### Класс работы с кнопками клавиатуры, см. https://vk.com/dev/bots_docs

```java
package Objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class Button {
    String type, label, buttonParams, color;

    public Button(String buttonParams, String color, String label) {
        this.buttonParams = buttonParams;
        this.type = "text";
        this.label = label;
        this.color = color;
    }
    public JSONObject getJSON() {

        JSONObject btn = new JSONObject();
        btn.put("button", this.buttonParams);

        JSONObject res = new JSONObject();
        res.put("type", this.type);
        String payload = "{\"button\":\"" + this.buttonParams + "\"}";
        res.put("payload", payload);
        res.put("label", this.label);
        return res;
    }
    private String getColorNegative() {
        return "negative";
    }

    private String getColorPositive() {
        return "positive";
    }

    private String getColorPrimary() {
        return "primary";
    }

    private String getColorDefault() {
        return "default";
    }

}

```
