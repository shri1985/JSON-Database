import java.io.*;
import java.util.NoSuchElementException;



public class InMemoryDatabase {

    private final static String CMD_FILE_NAME = "commands.txt";
    private final static String DB_FILE_NAME = "dbSnapshot.txt";
    private File commands;
    private File dbSnapshot;
    private DatabaseInterface dbImplementation;
    private ObserverList observerList;

    private void setBackupFiles(File commands, File snapshot) {
        this.commands = commands;
        this.dbSnapshot = snapshot;
        this.dbImplementation = Database.getDB();
        observerList = new ObserverList();
    }

    public InMemoryDatabase() {
        setBackupFiles(new File(CMD_FILE_NAME), new File(DB_FILE_NAME));

    }

    private InMemoryDatabase(File cmds, File snapshot) {
        setBackupFiles(cmds, snapshot);
    }

    public InMemoryDatabase put(String key, Object value) throws NullPointerException,
            IllegalArgumentException {
        Transaction transaction = new Transaction(dbImplementation, observerList, commands);
        try {
            transaction.put(key, value);
            transaction.commit();
        } catch (NullPointerException e) {
            transaction.abort();
            throw new NullPointerException(e.getMessage());
        } catch (IllegalArgumentException e) {
            transaction.abort();
            throw new IllegalArgumentException(e.getMessage());
        }
        return this;
    }

    public Object get(String key) throws NullPointerException, NoSuchElementException {
        Transaction transaction = new Transaction(dbImplementation, observerList, commands);
        Object value = transaction.get(key);
        transaction.commit();
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

    public Object remove(String key) throws NullPointerException {
        Object value = null;
        Transaction transaction = new Transaction(dbImplementation, observerList, commands);
        try {
            value = transaction.remove(key);
            transaction.commit();
        } catch (NullPointerException e) {
            transaction.abort();
        }
        return value;
    }

    public Transaction transaction() {
        return new Transaction(dbImplementation, observerList, commands);
    }

    private void restore(File cmds, File snapshot) {
        // first restore db
        Memento restoreState = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(snapshot);
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            restoreState = (Memento) in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }
        Memento[] mementos = {restoreState};
        Command theCommand = new CommandRestoreMemento(dbImplementation, mementos);
        theCommand.execute();

        if (cmds.exists()) {
            RestoreCommands recover = new RestoreCommands(cmds);
            RestoreCommands.CommandInfo nextCommand = recover.nextCommand();
            while (nextCommand != null) {
                if (nextCommand.cmd.equals(SaveCommands.ADD_CMD)) {
                    put(nextCommand.key, nextCommand.value);
                } else if (nextCommand.cmd.equals(SaveCommands.DEL_CMD)) {
                    remove(nextCommand.key);
                }
                nextCommand = recover.nextCommand();
            }

            setBackupFiles(cmds, snapshot);
        }
        return;
    }

    public static InMemoryDatabase recover(File commands, File snapshot) {
        InMemoryDatabase inMemoryDatabase = new InMemoryDatabase(null, null);
        inMemoryDatabase.restore(commands, snapshot);
        return inMemoryDatabase;
    }

    public static InMemoryDatabase recover() {
        File commands = new File(InMemoryDatabase.CMD_FILE_NAME);
        File snapshot = new File(InMemoryDatabase.DB_FILE_NAME);
        return recover(commands, snapshot);
    }



    public void snapshot(File cmds, File snapshot) throws IOException {
        Memento[] mementos = {null};
        Command theCommand = new CommandCreateMemento(dbImplementation, mementos);
        theCommand.execute();
        Memento currentState = mementos[0];

        FileOutputStream fileOutputStream;
        ObjectOutputStream out;
        try {
            snapshot.delete();
            fileOutputStream = new FileOutputStream(snapshot);
            out = new ObjectOutputStream(fileOutputStream);
            out.writeObject(currentState);
            out.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }catch (IOException e) {
            e.printStackTrace();
            return;
        }
        cmds.delete();
    }

    public void snapshot() throws IOException {
        File cmds = new File(InMemoryDatabase.CMD_FILE_NAME);
        File snapshot = new File(InMemoryDatabase.DB_FILE_NAME);
        snapshot(cmds, snapshot);
    }

    public Cursor getCursor(String key) {
        Cursor cursor = null;
        try {
            get(key);
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(e.getMessage());
        }
        cursor = new Cursor(this, observerList, key);
        return cursor;
    }
}
