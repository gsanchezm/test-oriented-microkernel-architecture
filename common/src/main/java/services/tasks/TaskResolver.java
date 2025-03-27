package services.tasks;

import intarfaces.tasks.ITask;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskResolver {
    private static final Logger logger = LogManager.getLogger(TaskResolver.class);

    public static Map<Class<?>, ITask<?>> toTaskMap(List<ITask<?>> taskList) {
        return taskList.stream()
                .collect(Collectors.toMap(
                        ITask::getClass,
                        task -> task,
                        (existing, duplicate) -> existing
                ));
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITask<?>> T getTask(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        return (T) taskMap.get(taskClass);
    }

    public static <T extends ITask<?>> FluentTaskExecutor<T> of(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        T task = getTask(taskMap, taskClass);
        if (task == null) {
            logger.warn("Task not found: {}. It may be due to plugin deactivation.", taskClass.getSimpleName());
            return new FluentTaskExecutor<>(null); // Will skip execution
        }
        return new FluentTaskExecutor<>(task);
    }
}