package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serial;

public class TOMException extends RuntimeException {

    private static final Logger logger = LogManager.getLogger(TOMException.class);

    @Serial
    private static final long serialVersionUID = 700L;

    private final String errorCode;

    /**
     * Constructor with message only.
     *
     * @param message Detailed error message.
     */
    public TOMException(String message) {
        super(message);
        this.errorCode = "UNKNOWN_ERROR"; // Default error code
        logException(message, null);
    }

    /**
     * Constructor with message and cause.
     *
     * @param message Detailed error message.
     * @param cause   The root cause of the exception.
     */
    public TOMException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNKNOWN_ERROR"; // Default error code
        logException(message, cause);
    }

    /**
     * Constructor with error code, message, and cause.
     *
     * @param errorCode Custom error code for debugging.
     * @param message   Detailed error message.
     * @param cause     The root cause of the exception.
     */
    public TOMException(String errorCode, String message, Throwable cause) {
        super(String.format("[%s] %s", errorCode, message), cause);
        this.errorCode = errorCode;
        logException(message, cause);
    }

    /**
     * Constructor with error code and message only.
     *
     * @param errorCode Custom error code.
     * @param message   Detailed error message.
     */
    public TOMException(String errorCode, String message) {
        super(String.format("[%s] %s", errorCode, message));
        this.errorCode = errorCode;
        logException(message, null);
    }

    /**
     * Logs exceptions based on severity.
     *
     * @param message Error message.
     * @param cause   Exception cause (optional).
     */
    private void logException(String message, Throwable cause) {
        String formattedMessage = String.format("⚠️ [FrameworkException] [%s] %s", errorCode, message);

        if (cause != null) {
            logger.error(formattedMessage, cause);
            return;
        }

        logger.error(formattedMessage);

    }

    /**
     * Returns the error code.
     *
     * @return Error code associated with the exception.
     */
    public String getErrorCode() {
        return errorCode;
    }
}