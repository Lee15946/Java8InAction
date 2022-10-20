package code.chapter8.section3;

public abstract class ProcessingObject<T> {
    protected ProcessingObject<T> successor;

    public void setSuccessor(ProcessingObject<T> successor) {
        this.successor = successor;
    }

    public T handle(T input) {
        final var r = handleWork(input);
        if ((successor != null)) {
            return successor.handle(r);

        }
        return r;
    }

    protected abstract T handleWork(T input);
}
