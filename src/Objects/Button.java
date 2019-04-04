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
