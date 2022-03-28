package h05.tree;

import h05.exception.BadOperationException;
import h05.math.MyNumber;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents an identifier operand arithmetic expression node. An identifier operand is
 * a variable name.
 *
 * @author Nhan Huynh
 */
public class IdentifierExpressionNode extends OperandExpressionNode {

    /**
     * A matcher used to validate the allowed identifier.
     */
    public static final Matcher IDENTIFIER_FORMAT =
        Pattern.compile("[a-zA-Z\\-][a-zA-Z\\-]*").matcher("");

    /**
     * The identifier name.
     */
    private final String value;

    /**
     * Constructs and initializes an identifier expression node with the given value.
     *
     * @param value the identifier name
     *
     * @throws IllegalArgumentException if the identifier name is not valid
     * @throws NullPointerException     if the identifier name is {@code null}
     */
    public IdentifierExpressionNode(String value) {
        Objects.requireNonNull(value, "value null");
        if (value.isEmpty()) {
            throw new IllegalArgumentException("empty string");
        }

        if (!IDENTIFIER_FORMAT.reset(value).matches()) {
            throw new BadOperationException("bad identifier");
        }

        this.value = value;
    }

    /**
     * Returns the identifier name.
     *
     * @return the identifier name
     */
    public String getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {
        return identifiers.get(value);
    }

    @Override
    public ArithmeticExpressionNode clone() {
        return new IdentifierExpressionNode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
