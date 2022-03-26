package h05.exception;

/**
 * Thrown to indicate that the number of operands provided for an operation is incorrect.
 *
 * @author Nhan Huynh
 */
public class WrongNumberOfOperandsException extends RuntimeException {

    /**
     * Constructs and initializes a wrong number of operands exception with the given inputs as its
     * detail message.
     *
     * @param actual         the actual operand count
     * @param startInclusive the left interval (inclusive) of the expected operand count
     * @param endExclusive   the right interval (exclusive) of the expected operand count
     */
    public WrongNumberOfOperandsException(int actual, int startInclusive, int endExclusive) {
        super(
            String.format(
                "%d is not in [%s, %s]",
                actual,
                startInclusive,
                endExclusive)
        );
    }

    /**
     * TODO: Removal?
     * Constructs and initializes a wrong number of operands exception with the given inputs as its
     * detail message.
     *
     * @param actual   the actual operand count
     * @param cmp      the comparison between the actual and expected operand count
     * @param expected the expected operand count
     */
    public WrongNumberOfOperandsException(int actual, Comparison cmp, int expected) {
        super(
            String.format("%s should be %s %s",
                actual,
                cmp.name().replace("_", " ").toLowerCase(),
                expected)
        );
    }
}
