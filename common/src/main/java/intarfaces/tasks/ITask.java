package intarfaces.tasks;

public interface ITask<T extends ITask<T>> {
    T execute(Object... args); // Every task should have an execute method
}
