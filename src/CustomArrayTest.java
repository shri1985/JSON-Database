import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;


public class CustomArrayTest {



    private CustomArray testArr;
    InMemoryDatabase dbTest = new InMemoryDatabase();
    String expectedString = "[1,2,4]";
    int value = 2;


    @Before
    public void setUp() throws Exception {
        testArr = new CustomArray();
        testArr.put(value);
        CustomArray testArray = CustomArray.fromString("[1,2,3]");
        testArr.put(testArray);


    }
    @Test
    public void testPutAndGet() throws Exception {
        setUp();

        try{
            int result = 12;
            testArr.put(result);
            int expectedResult = testArr.getInt(0);
            int elementNotFound = testArr.getInt(1);
            int bounds = testArr.getInt(10);
        }catch(NoSuchElementException e){
            //expected
        }catch(ArrayIndexOutOfBoundsException e){
            //out of bounds exception
        }

    }


    @Test
    public void testGetInt() throws Exception{
        setUp();
        int expectedValue = 2;
        int expectedResult = testArr.getInt(0);
        assertEquals("The values dont match",expectedValue,expectedResult);

    }


    @Test
    public void testGetString() throws Exception{

        CustomArray testArrString;
        testArrString =  new CustomArray();
        String expectedString = "IamString";
        testArrString.put(expectedString);
        String resultString = testArrString.getString(0);
        assertEquals("The strings are not same",expectedString,resultString);

    }



    @Test
    public void testGetDouble() throws Exception {


        Double value = 1.2;
        CustomArray testDouble;
        testDouble = new CustomArray();
        testDouble.put(value);
        Double expectedResult = testDouble.getDouble(0);
        assertEquals("The values dont match",value,expectedResult);
    }





    //It returns an CustomArray object.
    @Test
    public void testGetArray() throws Exception {
        CustomArray customArray1;
        CustomArray customArray2;


        customArray1 = new CustomArray();
        CustomArray resultArrayObject = customArray1.put("1").put("2").put("3");

        customArray2 = new CustomArray();
        customArray2.put(resultArrayObject);
        CustomArray finalArray = customArray2.getArray(0);
        Integer size = new Integer(3);
        assertEquals("The legth is non zero",size,resultArrayObject.length());
        assertEquals("The resultant arrays are not same","[\"1\",\"2\",\"3\"]",finalArray.toString());
    }

    @Test
    public void testGetArrayAndFromString() throws Exception {
        CustomArray testArray = new CustomArray();
        CustomArray customTestArray;
        String newData = "[2.3,\"at\",1.67e3,[1,\"me\",{\"a\":1}],\"bat\"]";
        customTestArray = new CustomArray().fromString(newData);

        testArray.put(customTestArray);
        CustomArray result = testArray.getArray(0);

        assertEquals("The strings did not match",testArray.getArray(0).toString(), customTestArray.toString());

    }


    @Test
    public void testLength() throws Exception {
        Integer expectedLength = new Integer(1);
        CustomArray testArray = new CustomArray();
        CustomArray customTestArray;
        String newData = "[1,2,3]";
        customTestArray = new CustomArray().fromString(newData);
        testArray.put(customTestArray);
        assertEquals("The length should be nonzero", expectedLength, testArray.length());
    }

    @Test
    public void testRemove() throws Exception {
        setUp();

        try{
            int result = 11;
            testArr.put(result);
            testArr.remove(0);
            int expectedResult = testArr.getInt(0);
            int bounds = testArr.getInt(10);


        }catch(NoSuchElementException e){
            //expected
        }catch(ArrayIndexOutOfBoundsException e){
            //out of bounds exception
        }

    }

    @Test
    public void testToString() throws Exception {
        CustomArray testArray = new CustomArray();
        CustomArray customTestArray;
        String newData = "[2.3,\"at\",1.67e3,[1,\"me\",{\"a\":1}],\"bat\"]";
        customTestArray = new CustomArray().fromString(newData);

        testArray.put(customTestArray);
        assertEquals("The strings did not match",testArray.getArray(0).toString(), customTestArray.toString());


    }


}