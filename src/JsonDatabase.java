import org.json.simple.JSONObject;


public class JsonDatabase implements DatabaseInterface {

    JSONObject jsonObject;

    public JsonDatabase() {
        jsonObject = new JSONObject();
    }

    @Override
    public void put(String key, Object value) {
        jsonObject.put(key, value);
    }

    @Override
    public Object get(String key) {
        return jsonObject.get(key);
    }

    @Override
    public Object remove(String key) {
        return jsonObject.remove(key);
    }

    public Memento createMemento() {
        Memento currentState = new Memento();
        currentState.saveState(jsonObject);
        return currentState;
    }

    public void restoreMemento(Memento oldState) {
        jsonObject = oldState.getState();
    }

}
