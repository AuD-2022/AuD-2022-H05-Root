package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyNumber;

import java.util.Iterator;
import java.util.Map;
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
     * Contains all unary operators.
     */
    private static final Set<String> OPERATORS = Set.of("+", "-", "*", "/", "pow", "log", "sqrt");


    /**
     * The number operand of this node.
     */
    private MyNumber literal;

    /**
     * The identifier operand of this node.
     */
    private String identifier;

    /**
     * The operator of this node.
     */
    private String operator;

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
     * @param operator the operator of the node
     * @param operands the operands of the node
     *
     * @throws WrongNumberOfOperandsException if the number of operands is not valid for the given
     *                                        operator
     */
    public ArithmeticExpressionNode(
        String operator,
        Iterator<ArithmeticExpressionNode> operands
    ) {
        Objects.requireNonNull(operator);
        Objects.requireNonNull(operands);

        // Check if the operator is valid
        if (!OPERATORS.contains(operator)) {
            throw new IllegalArgumentException(operator);
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
        switch (operator) {
            case "pow":
            case "log":
                if (!(size == 1 || size == 2)) {
                    throw new WrongNumberOfOperandsException(size, 1, 2);
                }
                break;
            case "sqrt":
                if ((!(size == 1))) {
                    throw new WrongNumberOfOperandsException(size, 1, 1);
                }
                break;
            default:
                break;
        }

        this.operator = operator;
        this.operands = head;
    }
}
