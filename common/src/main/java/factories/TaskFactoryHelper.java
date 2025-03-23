package factories;

import services.ITask;
import services.ITaskFactory;

public class TaskFactoryHelper {
    public static <T extends ITask<T>> ITaskFactory<T> of(Class<T> taskClass) {
        return new ITaskFactory<>() {
            @Override
            public T create() {
                try {
                    return taskClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException("Failed to instantiate task: " + taskClass.getName(), e);
                }
            }

            @Override
            public Class<T> getTaskClass() {
                return taskClass;
            }
        };
    }
}