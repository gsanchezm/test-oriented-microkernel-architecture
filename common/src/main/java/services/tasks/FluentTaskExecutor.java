package services.tasks;

import interfaces.tasks.ITask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class FluentTaskExecutor<T extends ITask<?>> {
    private static final Logger logger = LogManager.getLogger(FluentTaskExecutor.class);
    private final T task;
    private final List<Object> arguments = new ArrayList<>();

    public FluentTaskExecutor(T task) {
        this.task = task;
    }

    public FluentTaskExecutor<T> with(Object arg) {
        arguments.add(arg);
        return this;
    }

    public T execute() {
        if (task == null) {
            logger.info("⚠️  Task was skipped due to plugin not being loaded.");
            return null;
        }
        return (T) task.execute(arguments.toArray());
    }
}
