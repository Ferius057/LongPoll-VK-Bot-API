package Entities;

public class Server {
    String key, server;
    Integer ts;

    public Server(String server, String key, Integer ts) {
        this.server = server;
        this.key = key;
        this.ts = ts;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setTs(Integer ts) {
        this.ts = ts;
    }

    public String getKey() {
        return key;
    }

    public String getServer() {
        return server;
    }

    public Integer getTs() {
        return ts;
    }
}
