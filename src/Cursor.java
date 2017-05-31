
public class Cursor {
    private String cursorKey;
    private InMemoryDatabase db;
    private ObserverList observerList;

    public Cursor(InMemoryDatabase db, ObserverList observerList, String key) {
        cursorKey = key;
        this.db = db;
        this.observerList = observerList;
    }

    public Object get() {
        return db.get(cursorKey);
    }

    public Integer getInt() {
        return db.getInt(cursorKey);
    }

    public String getString() {
        return db.getString(cursorKey);
    }

    public Double getDouble() {
        return db.getDouble(cursorKey);
    }

    public CustomArray getArray() {
        return db.getArray(cursorKey);
    }

    public CustomObject getObject() {
        return db.getObject(cursorKey);
    }

    public void addObserver(Observer observer) {
        observerList.addObserver(cursorKey, observer);
    }

    public void removeObserver(Observer observer) {
        observerList.removeObserver(cursorKey, observer);
    }
}
