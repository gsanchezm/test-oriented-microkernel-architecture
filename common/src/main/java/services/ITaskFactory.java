package services;

import factories.ObjectFactory;

import java.util.HashMap;
import java.util.Map;

public interface ITaskFactory<T extends ITask<T>> {

    T create(); // Creates a new instance

    Class<T> getTaskClass(); // Returns the associated task class

    // 🔥 Register task factories dynamically
    Map<Class<?>, ITaskFactory<?>> TASK_FACTORY_REGISTRY = new HashMap<>();

    // ✅ Static method to register task factory
    static <T extends ITask<T>> void registerTask(ITaskFactory<T> factory) {
        TASK_FACTORY_REGISTRY.put(factory.getTaskClass(), factory);
        ObjectFactory.register(factory.getTaskClass(), factory::create); // Register supplier
    }

    // ✅ Static method to create task instance
    static ITask<?> createTask(Class<? extends ITask<?>> taskClass) {
        ITaskFactory<?> factory = TASK_FACTORY_REGISTRY.get(taskClass);
        if (factory == null) {
            throw new IllegalStateException("No factory registered for: " + taskClass.getName());
        }
        return factory.create();
    }
}