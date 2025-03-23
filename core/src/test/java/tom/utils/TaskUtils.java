package tom.utils;

import services.ITask;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskUtils {

    public static Map<Class<?>, ITask<?>> toTaskMap(List<ITask<?>> tasks) {
        return tasks.stream()
                .collect(Collectors.toMap(task -> task.getClass(), Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITask<?>> T getTask(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        return (T) taskMap.get(taskClass);
    }
}
