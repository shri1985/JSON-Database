import java.io.File;


public class CommandGet implements Command {

    private DatabaseInterface receiver;
    private Object[] arrValue;
    private String key;

    public CommandGet(DatabaseInterface receiver, File commands, String key, Object[] arrValue) {
        this.key = key;
        this.arrValue = arrValue;
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        arrValue[0] = receiver.get(key);
        System.out.println("CommandGet value: " + arrValue[0]);
    }

    @Override
    public void undo() {

    }
}
