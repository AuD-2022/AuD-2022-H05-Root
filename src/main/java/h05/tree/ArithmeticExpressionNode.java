package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyNumber;

import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents an arithmetic expression as a node in an arithmetic expression tree.
 *
 * @author Nhan Huynh
 */
public class ArithmeticExpressionNode {

    /**
     * A matcher used to check for valid identifiers.
     */
    private static final Matcher IDENTIFIER_MATCHER =
        Pattern.compile("[a-zA-Z\\-][a-zA-Z\\-]*").matcher("");


    /**
     * The predefined identifier for Pi.
     */
    private static final String PI = "pi";

    /**
     * The predefined identifier for the euler's number.
     */
    private static final String E = "e";

    /**
     * The operator for addition. (nary operation)
     */
    private static final String ADDITION = "+";

    /**
     * The operator for subtraction. (nary operation)
     */
    private static final String SUBSTRACTION = "-";

    /**
     * The operator for multiplication. (nary operation)
     */
    private static final String MULTIPLICATION = "*";

    /**
     * The operator for division. (nary operation)
     */
    private static final String DIVISION = "/";

    /**
     * The operator for power to a base. (unary or binary operation)
     */
    private static final String POW = "pow";
    /**
     * The operator for logarithm. (unary or binary operation)
     */
    private static final String LOG = "log";
    /**
     * The operator for square root. (binary operation=
     */
    private static final String SQRT = "sqrt";

    /**
     * Contains all operations (operators).
     */
    private static final Set<String> OPERATIONS = Set.of(
        ADDITION, SUBSTRACTION, MULTIPLICATION, DIVISION, POW, LOG, SQRT
    );


    /**
     * The number operand of this node.
     */
    private MyNumber literal;

    /**
     * The identifier operand of this node.
     */
    private String identifier;

    /**
     * The operation (operator) of this node.
     */
    private String operation;

    /**
     * The operands of this node.
     */
    private ListItem<ArithmeticExpressionNode> operands;

    /**
     * Constructs and initializes an arithmetic expression node with the given number operand.
     *
     * @param operand the number operand of the node
     */
    public ArithmeticExpressionNode(MyNumber operand) {
        this.literal = Objects.requireNonNull(operand);
    }

    /**
     * Constructs and initializes an arithmetic expression node with the given identifier operand.
     * An identifier is a string consisting of letters (a-z, A-Z) and/or hyphens with a length of at
     * least one.
     *
     * @param operand the identifier operand of the node
     *
     * @throws BadOperationException if the identifier is not valid
     */
    public ArithmeticExpressionNode(String operand) {
        Objects.requireNonNull(operand);
        if (operand.isEmpty()) {
            throw new IllegalArgumentException("empty string");
        }

        IDENTIFIER_MATCHER.reset(operand);
        if (!IDENTIFIER_MATCHER.matches()) {
            throw new BadOperationException("bad identifier");
        }

        this.identifier = operand;
    }

    /**
     * Constructs and initializes an arithmetic expression node with the given operator and
     * operands.
     *
     * @param operation the operation (operator) of the node
     * @param operands  the operands of the node
     *
     * @throws WrongNumberOfOperandsException if the number of operands is not valid for the given
     *                                        operator
     */
    public ArithmeticExpressionNode(
        String operation,
        Iterator<ArithmeticExpressionNode> operands
    ) {
        Objects.requireNonNull(operation);
        Objects.requireNonNull(operands);

        // Check if the operator is valid
        if (!OPERATIONS.contains(operation)) {
            throw new IllegalArgumentException(operation);
        }

        // Traverse operands
        ListItem<ArithmeticExpressionNode> head = null;
        ListItem<ArithmeticExpressionNode> tail = null;

        int size = 0;
        while (operands.hasNext()) {
            ArithmeticExpressionNode operand = operands.next();
            ListItem<ArithmeticExpressionNode> node = new ListItem<>();
            node.key = operand;

            if (head == null) {
                head = tail = node;
            } else {
                tail = tail.next = node;
            }
            size++;
        }

        // Check if the number of operands is valid for the given operator
        switch (operation) {
            case POW:
            case LOG:
                if (!(size == 1 || size == 2)) {
                    throw new WrongNumberOfOperandsException(size, 1, 2);
                }
                break;
            case SQRT:
                if ((!(size == 1))) {
                    throw new WrongNumberOfOperandsException(size, 1, 1);
                }
                break;
            default:
                break;
        }

        this.operation = operation;
        this.operands = head;
    }
}
