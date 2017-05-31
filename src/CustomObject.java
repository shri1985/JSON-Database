import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Set;


public class CustomObject extends Object implements Serializable {
    JSONObject jsonObject;

    private static int count = 0;

    public CustomObject() {
        System.out.println("CustomObject: " + ++count);
        jsonObject = new JSONObject();
    }

    public CustomObject put(String key, Object value) throws NullPointerException,
            IllegalArgumentException {
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (!PermittedObjects.validObjectType(value)) {
            throw new IllegalArgumentException("Invalid data type");
        }

        System.out.println("CustomObject put: key: " + key + ", value: " + value);
        jsonObject.put(key, value);
        return this;
    }

    public Object get(String key) throws NullPointerException, NoSuchElementException {
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        Object value = jsonObject.get(key);
        if (value == null) {
            throw new NoSuchElementException("Key not present");
        }
        return value;
    }

    public Integer getInt(String key) throws NullPointerException, NoSuchElementException {
        Integer value = null;
        try {
            value = (Integer) get(key);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public String getString(String key) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        String value = null;
        try {
            value = (String) get(key);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public Double getDouble(String key) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        Double value = null;
        try {
            value = (Double) get(key);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public CustomArray getArray(String key) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        CustomArray value = null;
        try {
            value = (CustomArray) get(key);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public CustomObject getObject(String key) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        CustomObject value = null;
        try {
            value = (CustomObject) get(key);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public Integer length() {
        return jsonObject.size();
    }

    public Object remove(String key) throws NullPointerException {
        Object value = null;
        try {
            value = get(key);
            jsonObject.remove(key);
        } catch (NoSuchElementException e) {
            // do nothing
        }
        return value;
    }

    @Override
    public String toString() {
        return jsonObject.toString();
    }

    public static CustomObject fromString(String data) throws ParseException {
        CustomObject myObj = new CustomObject();
        JSONParser parser = new JSONParser();
        JSONObject obj;
        try {
            obj = (JSONObject) parser.parse(data);
        } catch (ParseException e) {
            System.out.println("CustomObject:fromString " + e);
            e.printStackTrace();
            throw e ;
        } catch (ClassCastException e1) {
            System.out.println("CustomObject:fromString " + e1);
            return null;
        }
        Set<String> set = obj.keySet();

        for (String key: set) {
            Object value = obj.get(key);
            if (value instanceof String ||
                    value instanceof Double) {
                myObj.put(key, value);
            } else if(value instanceof Long) {
                myObj.put(key, ((Long) value).intValue());
            } else {
                myObj.put(key, fromString(value.toString()));
            }
        }
        return myObj;


    }
}
