import java.io.File;
import java.util.ArrayList;
import java.util.NoSuchElementException;


public class Transaction {
    private DatabaseInterface dbImplementation;
    private boolean isActive;
    private File commands;
    private ArrayList<Command> commandsList;
    private ObserverList observerList;

    public Transaction(DatabaseInterface db, ObserverList observerList, File file) {
        dbImplementation = db;
        isActive = true;
        commands = file;
        commandsList = new ArrayList<Command>();
        this.observerList = observerList;
    }

    public void put(String key, Object value) {
        if (!isActive) {
            throw new IllegalStateException("Transaction closed!");
        }
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        if (value == null) {
            throw new NullPointerException("Value is null");
        }
        if (!PermittedObjects.validObjectType(value)) {
            throw new IllegalArgumentException("Invalid data type");
        }

        Object[] arrValue = {value};
        Command theCommand = new CommandPut(dbImplementation, commands, observerList, key, arrValue);
        commandsList.add(theCommand);
        theCommand.execute();
    }


    public Object get(String key) throws NullPointerException, NoSuchElementException {
        if (!isActive) {
            throw new IllegalStateException("Transaction closed!");
        }
        if (key == null) {
            throw new NullPointerException("Key is null");
        }
        Object[] arrValue = {null};
        Command theCommand = new CommandGet(dbImplementation, commands, key, arrValue);
        theCommand.execute();
        if (arrValue[0] == null) {
            throw new NoSuchElementException("Key not present");
        }
        return arrValue[0];
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

    public Object remove(String key) throws NullPointerException {
        if (!isActive) {
            throw new IllegalStateException("Transaction closed!");
        }
        if (key == null) {
            throw new NullPointerException("Key is null");
        }

        Object[] arrValue = {null};
        try {
            Command theCommand = new CommandRemove(dbImplementation, commands, observerList, key, arrValue);
            theCommand.execute();
            commandsList.add(theCommand);
        } catch (NoSuchElementException e) {
            // do nothing
        }
        return arrValue[0];
    }

    public void commit() {
        isActive = false;
    }

    public void abort() {
        isActive = false;
        for (Command cmd: commandsList) {
            cmd.undo();
        }
    }
}
