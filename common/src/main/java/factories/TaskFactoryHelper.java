package factories;

import config.FrameworkException;
import intarfaces.tasks.ITask;
import intarfaces.tasks.ITaskFactory;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TaskFactoryHelper {
    public static <T extends ITask<T>> ITaskFactory<T> taskOf(Class<T> taskClass) {
        return new ITaskFactory<T>() {
            @Override
            public T create() {
                try {
                    return taskClass.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new FrameworkException("Failed to create task instance: " + taskClass.getSimpleName(), e);
                }
            }

            @Override
            public Class<T> getTaskClass() {
                return taskClass;
            }
        };
    }

    public static List<ITaskFactory<? extends ITask<?>>> loadTasks(List<String> basePackages) {
        List<ITaskFactory<? extends ITask<?>>> factories = new ArrayList<>();

        for (String basePackage : basePackages) {
            Reflections reflections = new Reflections(basePackage);
            Set<Class<? extends ITask<?>>> taskClasses =
                    reflections.getSubTypesOf((Class<ITask<?>>)(Class<?>) ITask.class);

            for (Class<? extends ITask<?>> cls : taskClasses) {
                if (!cls.isInterface() && !Modifier.isAbstract(cls.getModifiers())) {
                    factories.add(taskOf((Class) cls));
                }
            }
        }
        return factories;
    }

}