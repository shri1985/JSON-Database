import org.json.simple.JSONObject;

import java.io.Serializable;


public class Memento implements Serializable {
    private JSONObject jsonObject;
    public Memento() {}

    protected void saveState(JSONObject save) {
        jsonObject = new JSONObject(save);
    }

    protected JSONObject getState() {
        return jsonObject;
    }
}

