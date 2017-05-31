
public class PermittedObjects {
    public static boolean validObjectType(Object o) {
        if (o instanceof Integer ||
                o instanceof Double ||
                o instanceof String ||
                o instanceof CustomObject ||
                o instanceof CustomArray) {
            return true;
        }
        return false;
    }
}
