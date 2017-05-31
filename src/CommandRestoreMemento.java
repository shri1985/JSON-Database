
public class CommandRestoreMemento  implements Command{
    private DatabaseInterface receiver;
    private Memento memento;

    public CommandRestoreMemento (DatabaseInterface receiver, Memento[] mementos) {
        memento = mementos[0];
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.restoreMemento(memento);
    }

    @Override
    public void undo() {

    }
}
