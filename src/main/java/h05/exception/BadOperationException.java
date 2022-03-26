package h05.exception;

/**
 * Thrown to indicate that a bad operation was attempted.
 *
 * @author Nhan Huynh
 */
public class BadOperationException extends Exception {

    /**
     * Constructs and initializes a bad operation exception with the given detail message.
     *
     * @param message the detail message
     */
    public BadOperationException(String message) {
        super(message);
    }
}
