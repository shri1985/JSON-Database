import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * Created by shiva on 4/9/2016.
 */
public class VerifyDB {
    public static void main(String[] args) {


/*
        String data10 = "[1, 2, 3]";
        String data20 = "{\"name\": \"Roger\",\"age\": 21 }";

        CustomObject customObject;
        CustomArray customArray;

        try {
            customObject = CustomObject.fromString(data20);
            customArray = CustomArray.fromString(data10);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        */
/*
        myDB.put("Shri", 21);
        myDB.put("test", "string");
        myDB.put("Obj", customObject);
        myDB.put("Arr", customArray);
        myDB.put("d", 1.23);
*/
        /*
        myDB.recover();

        Integer value = (Integer) myDB.get("Shri");
        System.out.println("get returns : " + value);

        //String ret = (String) myDB.remove("test");
        String ret = (String) myDB.get("test");
        System.out.println("removed: " + ret);

        CustomArray retArr = (CustomArray) myDB.get("Arr");
        System.out.println("arr is: " + retArr.toString());

        CustomObject retObj = myDB.getObject("Obj");
        System.out.println("obj is: " + retObj.toString());

        Transaction sample = myDB.transaction();

        sample.put("revert", 123);
        sample.commit();
        sample = myDB.transaction();
        sample.remove("revert");
        sample.abort();

        myDB.test();
        */
/*
        myDB.put("Shri", 21);
        myDB.put("test", "string");
        myDB.snapshot();
        myDB.put("d", 1.23);


        InMemoryDatabase myDB = InMemoryDatabase.recover();
        System.out.println("get d: " + myDB.getDouble("d"));
*/

        InMemoryDatabase myDB = new InMemoryDatabase();

        CustomObject customObject;
        CustomArray customArray;

        String data10 = "[1, 2, 4]";
        String data20 = "{\"name\": \"Roger\",\"age\": 22 }";
        try {
            customObject = CustomObject.fromString(data20);
            customArray = CustomArray.fromString(data10);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        myDB.put("Obj", customObject);

        File snap1 = new File("snap1.txt");
        File cmds1 = new File("cmds1.txt");



        myDB.put("d", 1.36);
        myDB.put("Arr", customArray);



        CustomArray retArr = (CustomArray) myDB.get("Arr");
        System.out.println("arr is: " + retArr.toString());

        CustomObject retObj = myDB.getObject("Obj");
        System.out.println("obj is: " + retObj.toString());

        Observer observer1 = new Observer() {
            @Override
            public void notfiy(Object o) {
                System.out.println("double changed: " + o);
            }
        };
        Cursor doubleCursor = myDB.getCursor("d");
        doubleCursor.addObserver(observer1);

        Cursor doubleCursor2 = myDB.getCursor("d");
        doubleCursor2.addObserver(new Observer() {
            @Override
            public void notfiy(Object o) {
                System.out.println("double changed 2: " + o);
            }
        });

        myDB.put("d", 1.45);
        doubleCursor.removeObserver(observer1);
        myDB.put("d", 1.23);
        //myDB.remove("d");
        doubleCursor.getDouble();

        try {
            doubleCursor.getInt();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }


        try {
            myDB.snapshot(cmds1, snap1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        InMemoryDatabase myDB2 = InMemoryDatabase.recover(cmds1, snap1);
        //myDB.recover(cmds1, snap1);
        myDB2.getDouble("d");


    }
}
