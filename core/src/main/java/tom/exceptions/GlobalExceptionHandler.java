package tom.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Global Exception Handler for all unhandled exceptions in the framework.
 */
public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles uncaught exceptions.
     *
     * @param thread The thread where the exception occurred.
     * @param exception The uncaught exception.
     */
    @Override
    public void uncaughtException(Thread thread, Throwable exception) {
        logger.error("❌ Uncaught Exception in thread {}: {}", thread.getName(), exception.getMessage(), exception);
    }

    /**
     * Registers the global exception handler.
     */
    public static void register() {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        logger.info("✅ Global Exception Handler Registered.");
    }
}