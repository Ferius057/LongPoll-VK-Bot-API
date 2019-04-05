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
