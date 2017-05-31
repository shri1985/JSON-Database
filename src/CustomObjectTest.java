import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;


public class CustomObjectTest {
    private CustomObject myObject;
    @org.junit.Before
    public void setUp() throws Exception {
        myObject = new CustomObject();
        myObject.put("int", 10);
        myObject.put("string", "value");
        myObject.put("double", 1.2);
        CustomArray testArray = CustomArray.fromString("[1,2,3]");
        myObject.put("array", testArray);
        CustomObject testObject = CustomObject.fromString("{\"name\":\"Roger\", \"age\":21}");
        myObject.put("object", testObject);

    }

    @org.junit.Test
    public void testPut() throws Exception {
        myObject = new CustomObject();

        // validate for supported data types
        try {
            myObject.put("int", 10);
            myObject.put("string", "value");
            myObject.put("double", 1.2);
            CustomArray myArray = CustomArray.fromString("[1,2,3]");
            myObject.put("array", myArray);
            CustomObject testObject = CustomObject.fromString("{\"name\":\"Roger\", \"age\":21}");
            myObject.put("object", testObject);
        } catch (Exception e) {
            assertTrue(false);
        }

        // validate for non supported data types
        try {
            myObject.put("float", new Float(1.3));
        } catch (IllegalArgumentException e) {
            // test pass
        } catch (Exception e) {
            assertTrue(false);
        }

        try {
            myObject.put("null", null);
        } catch (NullPointerException e) {
            // test pass
        } catch (Exception e) {
            assertTrue(false);
        }

        CustomObject returnObject = myObject.put("return", 10);
        Integer returnValue = returnObject.getInt("return");
        assertEquals(10, returnValue.intValue());
    }

    @org.junit.Test
    public void testGet() throws Exception {
        setUp();
        myObject.put("testget","get");
        String expected = "get";
        String result = (String) myObject.get("testget");
        assertEquals("The strings are not same",expected,result);
    }

    @org.junit.Test
    public void testGetInt() throws Exception {
        setUp();

        Integer returnValue = myObject.getInt("int");
        assertEquals(10,returnValue.intValue());
        assertNotEquals("The types should not match",10,myObject.getDouble("double"));
    }

    @org.junit.Test
    public void testGetString() throws Exception {
        setUp();
        String result = "value";
        String wrongResult = "value1";
        String returnValue = myObject.getString("string");
        assertEquals("Strings mismatch",returnValue,result);
        assertNotEquals("The strings should not match",wrongResult,returnValue);

    }

    @org.junit.Test
    public void testGetDouble() throws Exception {
        setUp();
        Double returnValue = myObject.getDouble("double");
        assertNotEquals("The values do not match",1,returnValue.doubleValue());
    }

    @org.junit.Test
    public void testGetArray() throws Exception {
        setUp();
        CustomArray expectedArray = (CustomArray) myObject.get("array");
        String resultArray = "[1,2,3]";

    }

    @org.junit.Test
    public void testGetObject() throws Exception {
        setUp();
        CustomObject expectedObject = myObject.getObject("object");

        String result = "{\"name\":\"Roger\",\"age\":21}";
        System.out.println("result"+expectedObject.toString());
        assertEquals("the objects match",expectedObject.toString(),result);

    }

    @org.junit.Test
    public void testLength() throws Exception {
        setUp();
        Integer value = null;
        value = myObject.length();
        assertEquals("the length does not match",5,value.intValue());
    }

    @org.junit.Test
    public void testRemove() throws Exception {
        setUp();
        Object value =  myObject.remove("string");
        Object obj = null;
        System.out.println("result is"+ value.equals(obj));
    }

    @org.junit.Test
    public void testToString() throws Exception {
        setUp();
        CustomObject resultObj = myObject.getObject("object");
        String expectedString =  "{\"name\":\"Roger\",\"age\":21}";
        String resultString = resultObj.toString();
        assertEquals("The strings dont match",expectedString,resultString);

    }

    @org.junit.Test
    public void testFromString() throws Exception {
        CustomArray testArray = CustomArray.fromString("[1,2,3,4]");
        myObject.put("array", testArray);
        CustomArray resultObj = myObject.getArray("array");
        String expected = resultObj.toString();
        String result = "[1,2,3,4]";
        assertEquals("The strings should be same",expected,result);

    }
}