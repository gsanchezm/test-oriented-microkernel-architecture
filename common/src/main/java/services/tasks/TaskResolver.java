package services.tasks;

import interfaces.tasks.ITask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.BaseResolver;
import utils.ExecutionContextRegistry;

import java.util.List;

public class TaskResolver {
    private static final Logger logger = LogManager.getLogger(TaskResolver.class);

    public static <T extends ITask<?>> T getTask(Class<T> taskClass) {
        @SuppressWarnings("unchecked")
        List<T> tasks = (List<T>) ExecutionContextRegistry.get().getTasks();
        return BaseResolver.resolve(tasks, taskClass);
    }

    public static <T extends ITask<?>> FluentTaskExecutor<T> of(Class<T> taskClass) {
        return new FluentTaskExecutor<>(getTask(taskClass));
    }

    public static FluentTaskExecutor<?> of(String simpleClassName) {
        ITask<?> task = BaseResolver.resolveByName(ExecutionContextRegistry.get().getTasks(), simpleClassName);
        return new FluentTaskExecutor<>(task);
    }
}