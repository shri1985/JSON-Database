import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertTrue;

/**
 * Created by shridevi on 4/14/16.
 */
public class TransactionTest {



    @Test
    public void testCommit() throws Exception {
        InMemoryDatabase db = new InMemoryDatabase();
        Transaction sample = db.transaction();

        try {
            sample.put("revert", 123);
            sample.put("revert","string");
            sample.remove("revert");
            sample.commit();
            sample.put("revert", "newValue");

        }catch(Exception e){
            //This is expected
        }
    }

    @Test
    public void testAbort() throws Exception {
        InMemoryDatabase db = new InMemoryDatabase();
        Transaction sample = db.transaction();

        String resultValue;
        try {
            sample.put("revert", 123);
            sample.put("revert","string");
            sample.abort();
        } catch (Exception e ) {
            assertTrue("testAbort no execptions expected " + e.getMessage(), false);
        }


        try {
            resultValue = sample.getString("revert");
        } catch (NoSuchElementException e) {
            //pass
        } catch (IllegalStateException e) {
            //pass
        } catch (Exception e) {
            assertTrue("testAbort no execptions expected " + e.getMessage(), false);
        }


    }

}