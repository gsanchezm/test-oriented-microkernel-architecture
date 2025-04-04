package services.tasks;

import intarfaces.tasks.ITask;

import java.util.Map;

public class TaskHelper {

    public static <T extends ITask<?>> boolean isTaskAvailable(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        return taskMap.containsKey(taskClass);
    }

    public static <T extends ITask<?>> void safelyExecute(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass, Object... args) {
        ITask<?> task = taskMap.get(taskClass);
        if (task != null) {
            task.execute(args);
        }
    }
}
