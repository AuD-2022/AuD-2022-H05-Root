package h05.tree;

import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyNumber;

import java.util.Map;
import java.util.Objects;

/**
 * This class represents an operator arithmetic expression node. An operator expression node
 * contains and operator followed n operands depending on its arity.
 *
 * @author Nhan Huynh
 */
public class OperatorExpressionNode implements ArithmeticExpressionNode {

    /**
     * The operator of this node.
     */
    private final Operator operator;

    /**
     * The operands of this node.
     */
    private final ListItem<ArithmeticExpressionNode> operands;

    /**
     * The number of operands of this node.
     */
    private final int size;

    /**
     * Contracts and initializes an operator expression node with the given operator and operands.
     *
     * @param operator the operator of this node
     * @param operands the operands of this node
     *
     * @throws NullPointerException if the operator is {@code null}
     */
    public OperatorExpressionNode(Operator operator, ListItem<ArithmeticExpressionNode> operands) {
        Objects.requireNonNull(operator, "operator null");

        int size = 0;
        for (ListItem<ArithmeticExpressionNode> node = operands; node != null; node = node.next) {
            size++;
        }

        // Validate operator arity
        validateOperator(operator, size);

        this.operator = operator;
        this.operands = operands;
        this.size = size;
    }

    /**
     * Validates the operator arity.
     *
     * @param operator the operator to validate
     * @param size     the number of operands
     *
     * @throws WrongNumberOfOperandsException if the operator arity is not valid
     */
    public static void validateOperator(Operator operator, int size) {
        switch (operator) {
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
    }

    /**
     * Returns the operator of this node.
     *
     * @return the operator of this node
     */
    public Operator getOperator() {
        return operator;
    }

    /**
     * Returns the operands of this node.
     *
     * @return the operands of this node
     */
    public ListItem<ArithmeticExpressionNode> getOperands() {
        return operands;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {
        // TODO evaluate
        return null;
    }

    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperator() {
        return true;
    }

    @Override
    public ArithmeticExpressionNode clone() {
        ListItem<ArithmeticExpressionNode> clone = null;
        ListItem<ArithmeticExpressionNode> tail = null;
        for (ListItem<ArithmeticExpressionNode> node = this.operands; node != null; node =
            node.next) {
            if (clone == null) {
                clone = tail = new ListItem<>();
            } else {
                tail = tail.next = new ListItem<>();
            }
            tail.key = node.key.clone();
        }
        return new OperatorExpressionNode(operator, clone);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size * 2);
        sb.append(LEFT_BRACKET);
        sb.append(operator);
        for (ListItem<ArithmeticExpressionNode> node = operands; node != null; node = node.next) {
            sb.append(" ").append(node.key);
        }
        sb.append(RIGHT_BRACKET);
        return sb.toString();
    }
}
