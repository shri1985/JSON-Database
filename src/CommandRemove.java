import java.io.File;


public class CommandRemove implements Command {
    private DatabaseInterface receiver;
    private String key;
    private File cmds;
    private Object[] arrValue;
    private ObserverList observerList;


    public CommandRemove(DatabaseInterface receiver, File commands, ObserverList observerList, String key, Object[] arrValue) {
        this.receiver = receiver;
        this.key = key;
        this.cmds = commands;
        this.arrValue = arrValue;
        this.observerList = observerList;
    }

    @Override
    public void execute() {
        if (cmds != null) {
            SaveCommands saver = new SaveCommands(cmds, receiver);
            saver.writeCommand(SaveCommands.DEL_CMD, key, null);
        }
        arrValue[0] = receiver.remove(key);
        observerList.checkForNotify(key, arrValue[0]);
    }

    @Override
    public void undo() {
        if (cmds != null) {
            SaveCommands saver = new SaveCommands(cmds, receiver);
            saver.writeCommand(SaveCommands.ADD_CMD, key, arrValue[0]);
        }
        receiver.put(key, arrValue[0]);
        observerList.checkForNotify(key, arrValue[0]);
    }
}
