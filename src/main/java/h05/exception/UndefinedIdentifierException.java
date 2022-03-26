package h05.exception;

/**
 * Thrown to indicate that an identifier is undefined.
 *
 * @author Nhan Huynh
 */
public class UndefinedIdentifierException extends Exception {

    /**
     * Constructs and initializes a undefined identifier exception  with no detail message.
     */
    public UndefinedIdentifierException(String message) {
        super(message);
    }
}
