import java.io.*;


public class CommandPut implements Command {

    private DatabaseInterface receiver;
    private String key;
    private Object[] arrValue;
    private File cmds;
    private ObserverList observerList;

    public CommandPut(DatabaseInterface receiver, File commands, ObserverList observerList, String key, Object[] arrValue) {
        this.receiver = receiver;
        this.key = key;
        this.arrValue = arrValue;
        this.cmds = commands;
        this.observerList = observerList;
    }

    @Override
    public void execute() {
        if (cmds != null) {
            SaveCommands saver = new SaveCommands(cmds, receiver);
            saver.writeCommand(SaveCommands.ADD_CMD, key, arrValue[0]);
        }
        receiver.put(key, arrValue[0]);
        observerList.checkForNotify(key, arrValue[0]);
    }

    @Override
    public void undo() {
        if (cmds != null) {
            SaveCommands saver = new SaveCommands(cmds, receiver);
            saver.writeCommand(SaveCommands.DEL_CMD, key, null);
        }
        receiver.remove(key);
        observerList.checkForNotify(key, arrValue[0]);
    }
}
