import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Serializable;
import java.util.NoSuchElementException;


public class CustomArray extends Object implements Serializable {
    JSONArray jsonArray;

    private static int count = 0;

    public CustomArray() {
        System.out.println("CustomArray: " + ++count);
        jsonArray = new JSONArray();
    }

    public CustomArray put(Object value) throws IllegalArgumentException,
            NullPointerException {
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (!PermittedObjects.validObjectType(value)) {
            throw new IllegalArgumentException("Invalid data type");
        }

        System.out.println("CustomArray put: value: " + value);
        jsonArray.add(value);
        return this;
    }


    public Object get(int index) throws ArrayIndexOutOfBoundsException {
        if (index > jsonArray.size() || index < 0) {
            throw new ArrayIndexOutOfBoundsException("invalid index: " + index + " array size: " + jsonArray.size());
        }
        return jsonArray.get(index);
    }

    public Integer getInt(int index) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        Integer value = null;
        try {
            value = (Integer) get(index);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public String getString(int index) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        String value = null;
        try {
            value = (String) get(index);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public Double getDouble(int index) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        Double value = null;
        try {
            value = (Double) get(index);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public CustomArray getArray(int index) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        CustomArray value = null;
        try {
            value = (CustomArray) get(index);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public CustomObject getObject(int index) throws ArrayIndexOutOfBoundsException,
            NoSuchElementException {
        CustomObject value = null;
        try {
            value = (CustomObject) get(index);
        } catch (ClassCastException e) {
            throw new NoSuchElementException("Type mismatch");
        }
        return value;
    }

    public Integer length() {
        return jsonArray.size();
    }

    public Object remove(int index) throws ArrayIndexOutOfBoundsException {
        Object value = null;
        try {
            value = get(index);
            jsonArray.remove(index);
        } catch (NoSuchElementException e) {
            // do nothing
        }
        return value;
    }

    @Override
    public String toString() {
        return jsonArray.toString();
    }
    public static CustomArray fromString(String data) throws ParseException{
        CustomArray customArray = new CustomArray();
        JSONParser parser = new JSONParser();
        JSONArray arr;
        try {
            arr = (JSONArray) parser.parse(data);
        } catch (ParseException e) {
            //e.printStackTrace();
            System.out.println("CustomArray:fromString " + e);
            throw e;
        }

        for (Object value: arr) {
            if (value instanceof String ||
                    value instanceof Double) {
                customArray.put(value);
            } else if ( value instanceof CustomObject) {
                System.out.println("obj: " + value);
                customArray.put(value);
            } else if (value instanceof Long) {
                customArray.put(((Long) value).intValue());
            } else {
                CustomObject obj = CustomObject.fromString(value.toString());
                if (obj != null) {
                    customArray.put(obj);
                } else {
                    try {
                        customArray.put(CustomArray.fromString(value.toString()));
                    } catch (ParseException e1) {
                        System.out.println("Wrong input");
                        return null;
                    }
                }
            }
        }
        return customArray;


    }


}
