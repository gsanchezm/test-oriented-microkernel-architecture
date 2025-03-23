package services.tasks;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import intarfaces.tasks.ITask;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TaskResolver {

    public static Map<Class<?>, ITask<?>> toTaskMap(List<ITask<?>> tasks) {
        return tasks.stream()
                .collect(Collectors.toMap(task -> task.getClass(), Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public static <T extends ITask<?>> T getTask(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        return (T) taskMap.get(taskClass);
    }

    public static <T extends ITask<?>> boolean isTaskAvailable(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass) {
        return taskMap.containsKey(taskClass);
    }

    public static <T extends ITask<?>> void safelyExecute(Map<Class<?>, ITask<?>> taskMap, Class<T> taskClass, Object... args) {
        ITask<?> task = taskMap.get(taskClass);
        if (task != null) {
            task.execute(args);
        } else {
            Allure.step("Task " + taskClass.getSimpleName() + " not found. Step skipped.", Status.SKIPPED);
            System.out.printf("⚠️ Task %s not available. Plugin might be disabled.%n", taskClass.getSimpleName());
        }
    }
}