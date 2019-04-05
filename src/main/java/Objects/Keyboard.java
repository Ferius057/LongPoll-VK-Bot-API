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
