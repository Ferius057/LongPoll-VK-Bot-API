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

    public Integer groupID;
    public Server srv;
    public String token;

    public String encoding = "windows-1251";
    public double version = 5.92;

    public VK(Integer groupID, String token) {
        this.groupID = groupID;
        this.token = token;

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

        JSONObject json, rec;
        JSONArray updates;

        while (true) {
            json = new JSONObject(
                    Objects.requireNonNull(convertEncoding(
                            Request.postLongPoll(this.srv.getServer()+ "?act=a_check&key=" + this.srv.getKey() + "&ts=" + this.srv.getTs() + "&wait=" + wait + "&mode=2&version=2"), encoding)
                    )
            );

            // может быть failed
            updates = json.getJSONArray("updates");

            if (updates.length() != 0) {
                this.srv.setTs(json.getString("ts"));

                for (int i = 0; i < updates.length(); ++i) {

                    rec = updates.getJSONObject(i);
                    this.parserType(rec.getJSONObject("object"), rec.getString("type"));
                }
            } else
            {
                return  "closed";
            }
        }
    }

    public void parserType(JSONObject object, String type) {
        System.out.println(type);
        switch (type) {
            case "message_new":
                break;
            case "message_reply":
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
        System.out.println(object);
    }
}
