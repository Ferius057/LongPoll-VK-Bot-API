package VK;

import Entities.Server;
import Lib.Request;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import com.google.gson.Gson;
import java.util.Objects;
import static Lib.Converting.convertEncoding;

public class VK {

    Integer groupID;
    Server srv;

    public String token = "code here user scopes: groups,offline";
    public String encoding = "windows-1251";
    public double version = 5.92;

    public VK(Integer groupID) {
        this.groupID = groupID;

        Gson gson = new GsonBuilder().create();
        this.srv = gson.fromJson(this.getLongPollServer(), Server.class);
    }

    public String getLongPollServer() {
        return this.query("groups.getLongPollServer", "group_id=" + this.groupID);
    }

    public String query(String method, String params) {
        if (token != null) params = "access_token=" + token + "&" + "version=" + version + "&" + params;

        JSONObject object = new JSONObject(
                Objects.requireNonNull(convertEncoding(
                        Request.post("https://api.vk.com/method/" + method, params), encoding)
                )
        );

        return object.getJSONObject("response").toString();
    }

    public String getUpdates(Integer wait) {

        while (true) {
            JSONObject json = new JSONObject(
                    Objects.requireNonNull(convertEncoding(
                            Request.postLongPoll(this.srv.getServer()+ "?act=a_check&key=" + this.srv.getKey() + "&ts=" + this.srv.getTs() + "&wait=" + wait + "&mode=2&version=2"), encoding)
                    )
            );

            JSONArray updates = json.getJSONArray("updates");

            if (updates.length() != 0) {
                this.srv.setTs(json.getString("ts"));

                System.out.println(updates.toString());
                // do something with updates array
            }
        }
    }
}
