package h05.exception;

import h05.math.MyNumber;

/**
 * Thrown to indicate that the provided operand for an operation is incorrect.
 *
 * @author Nhan Huynh
 */
public class WrongOperandException extends RuntimeException {

    /**
     * Constructs and initializes a wrong number of operands exception with the given inputs as its
     * detail message.
     *
     * @param actual   the actual operand
     * @param cmp      the comparison between the actual and expected operand
     * @param expected the expected operand
     */
    public WrongOperandException(MyNumber actual, Comparison cmp, MyNumber expected) {
        super(
            String.format(
                "%s should be %s %s",
                actual,
                cmp.name().replace("_", " ").toLowerCase(),
                expected
            )
        );
    }
}
