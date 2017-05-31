
public class Database {
    public static DatabaseInterface getDB() {
        return new JsonDatabase();
    }
}
