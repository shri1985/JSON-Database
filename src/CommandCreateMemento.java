
public class CommandCreateMemento implements Command{
    private Memento[] mementos;
    private DatabaseInterface receiver;
    public CommandCreateMemento(DatabaseInterface receiver, Memento[] mementos) {
        this.mementos = mementos;
        this.receiver = receiver;
    }


    @Override
    public void execute() {
        mementos[0] = receiver.createMemento();
    }

    @Override
    public void undo() {

    }
}
