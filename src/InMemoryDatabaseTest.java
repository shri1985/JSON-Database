import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class InMemoryDatabaseTest {

    private DatabaseInterface dbImpl;
    private final static String CMD_FILE_NAME = "commands1.txt";
    private final static String DB_FILE_NAME = "dbSnapshot1.txt";
    private File commands;
    private File dbSnapshot;
    InMemoryDatabase db = new InMemoryDatabase();



    @Test
    public void testPutAndGet() throws Exception {
        db.put("put","one");
        Object obj = db.get("put");
        String result = db.getString("put");
        assertEquals("The value did not match","one",result);

    }



    @Test
    public void testGetInt() throws Exception {
        db.put("inttest",4);
        int result = db.getInt("inttest");
        assertEquals("The values dont match",4,result);
    }

    @Test
    public void testGetString() throws Exception {
        db.put("stringtest","value");
        Object resultTo = db.get("stringtest");
        assertEquals("The string values done match","value",resultTo.toString());

    }

    @Test
    public void testGetDouble() throws Exception {
        db.put("doubletest",1.2);
        Double result = (Double) db.getDouble("doubletest");
        assertNotEquals("The double values dont match",1,result.doubleValue());
    }

    @Test
    public void testGetArray() throws Exception {
        CustomArray testArray = CustomArray.fromString("[1,2,3]");
        db.put("array", testArray);
        CustomArray result = (CustomArray) db.get("array");
        assertEquals("The values are not equal",result.toString(),"[1,2,3]");
    }

    @Test
    public void testGetObject() throws Exception {
        String expectedString =  "{\"name\":\"Roger\",\"age\":21}";
        CustomObject testObject = CustomObject.fromString(expectedString);
        db.put("object",testObject);
        CustomObject expectedObj = (CustomObject) db.get("object");
        assertEquals("The values dont match",expectedObj.toString(),expectedString);
    }

    @Test
    public void testRemove() throws Exception {
        db.put("removetest","removevalue");
        db.remove("removetest");
        Object value = db.remove("removetest");
        Object obj = null;
        assertEquals("The value was not null",null,value);

    }

    @Test
    public void testTransaction() throws Exception {
        Transaction transaction = db.transaction();
        assertNotNull(transaction);
    }

    @Test
    public void testRecoverAndSnapshotWithArgs() throws Exception {
        File commands = new File(CMD_FILE_NAME);
        File snapshot = new File(DB_FILE_NAME);
        double input = 1.23;
        db.put("recover", "valueRecoverTest");
        db.put("d", input);

        db.snapshot(commands, snapshot);
        InMemoryDatabase db2 = InMemoryDatabase.recover(commands, snapshot);
        String result = db2.getString("recover");
        assertEquals("The values dont match",result,"valueRecoverTest");


    }

    @Test
    public void testRecoverAndSnapshot() throws Exception {
        File commands = new File(CMD_FILE_NAME);
        File snapshot = new File(DB_FILE_NAME);
        db.put("recovernoargs", "newvalueRecoverTest");
        db.snapshot();
        InMemoryDatabase db2 = InMemoryDatabase.recover();
        String result = db2.getString("recovernoargs");
        assertEquals("The values dont match",result,"newvalueRecoverTest");

    }



    @Test
    public void testGetCursor() throws Exception {
        db.put("testcur","cur");
        Cursor data = db.getCursor("testcur");

        final boolean[] notified = {false};

        System.out.println("The cursor value is"+ data.getString());
            Observer observer1 = new Observer(){
                @Override
                public void notfiy(Object o) {
                System.out.println("value changed: " + o);
                notified[0] = true;
            }
        };
            Observer observer2 = new Observer() {
                @Override
                public void notfiy(Object o) {
                    System.out.println("value changed: " + o);
                    notified[0] = true;
                }
            };
        data.addObserver(observer1);
        data.addObserver(observer2);
        //When data changes both observers are notified
        db.put("testcur","curupdate");
        data.removeObserver(observer2);
        //After this only observer1 is notified
        db.put("testcur","newCurupdate1");
        assertTrue("Observer not called", notified[0]);

    }
}