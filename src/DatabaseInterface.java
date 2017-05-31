public interface DatabaseInterface {
    public void put(String key, Object value);
    public Object get(String key);
    public Object remove(String key);
    public Memento createMemento();
    public void restoreMemento(Memento oldState);
}
