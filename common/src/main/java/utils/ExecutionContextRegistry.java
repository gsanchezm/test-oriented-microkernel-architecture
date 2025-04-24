package utils;

import interfaces.init.ExecutionContextProvider;

public class ExecutionContextRegistry {
    private static ExecutionContextProvider provider;

    public static void set(ExecutionContextProvider contextProvider) {
        provider = contextProvider;
    }

    public static ExecutionContextProvider get() {
        if (provider == null) {
            throw new IllegalStateException("ExecutionContextProvider has not been set.");
        }
        return provider;
    }
}

