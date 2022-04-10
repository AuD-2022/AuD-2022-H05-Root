package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyInteger;
import h05.math.MyNumber;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents an operation arithmetic expression node. An operation expression node contains and operator followed by
 * <it>n</it> operands depending on its arity.
 *
 * <p>Example:
 * <ul>
 *     <li>Operator node with the operation "+" and the operands 2, 3, 4/li>
 *     <li>Racket notation: (+ 2 3 4)</li>
 * </ul>
 *
 * <pre>{@code
 *    ListItem<ArithmeticExpressionNode> operands = new ListItem<>();
 *    operands.key = new LiteralExpressionNode(new MyInteger(2));
 *    operands.next = new ListItem<>();
 *    operands.next.key = new LiteralExpressionNode(new MyInteger(3));
 *    operands.next.next = new ListItem<>();
 *    operands.next.next.key = new LiteralExpressionNode(new MyInteger(4));
 *    OperationExpressionNode node = new OperationExpressionNode(Operator.ADD, operands);
 * }</pre>
 *
 * @author Nhan Huynh
 * @see Operator
 * @see ListItem
 */
public class OperationExpressionNode implements ArithmeticExpressionNode {

    /**
     * The operator of this node.
     */
    private final Operator operator;

    /**
     * The operands of this node.
     */
    private final @Nullable ListItem<ArithmeticExpressionNode> operands;

    /**
     * The number of operands of this node.
     */
    private final int size;

    /**
     * Contracts and initializes an operation expression node with the given operator and operands.
     *
     * @param operator the operator of this node
     * @param operands the operands of this node
     *
     * @throws NullPointerException if the operator is {@code null}
     */
    public OperationExpressionNode(Operator operator, @Nullable ListItem<ArithmeticExpressionNode> operands) {
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
            case SUB:
            case DIV:
                if(size ==0) {
                    throw new WrongNumberOfOperandsException(size, 1, 1);
                }
                break;
            case EXP:
            case LN:
                if (size != 1) {
                    throw new WrongNumberOfOperandsException(size, 1, 1);
                }
                break;
            case EXPT:
            case LOG:
                if (size != 2) {
                    throw new WrongNumberOfOperandsException(size, 2, 2);
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
        return switch (size) {
            case 0 -> evaluateNullaryExpressions(identifiers);
            case 1 -> evaluateUnaryExpressions(identifiers);
            case 2 -> evaluateBinaryExpressions(identifiers);
            default -> evaluateNaryExpressions(identifiers);
        };
    }

    /**
     * Evaluates the nullary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateNullaryExpressions(Map<String, MyNumber> identifiers) {
        return switch (operator) {
            case ADD -> new MyInteger(BigInteger.ZERO);
            case MUL -> new MyInteger(BigInteger.ONE);
            default -> throw new BadOperationException(operator.toString());
        };
    }

    /**
     * Evaluates the unary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateUnaryExpressions(Map<String, MyNumber> identifiers) {
        // Cannot be null, since we checked the arity
        assert operands != null;
        MyNumber operand = operands.key.evaluate(identifiers);
        return switch (operator) {
            case ADD -> operand.plus();
            case SUB -> operand.minus();
            case MUL -> operand.times();
            case DIV -> operand.divide();
            case EXP -> operand.exp();
            case LN -> operand.ln();
            case SQRT -> operand.sqrt();
            default -> throw new BadOperationException(operator.toString());
        };
    }

    /**
     * Evaluates the binary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateBinaryExpressions(Map<String, MyNumber> identifiers) {
        // Cannot be null, since we checked the arity
        assert operands != null;
        MyNumber operand1 = operands.key.evaluate(identifiers);
        assert operands.next != null;
        MyNumber operand2 = operands.next.key.evaluate(identifiers);
        return switch (operator) {
            case ADD -> operand1.plus(operand2);
            case SUB -> operand1.minus(operand2);
            case MUL -> operand1.times(operand2);
            case DIV -> operand1.divide(operand2);
            case EXPT -> operand1.expt(operand2);
            case LOG -> operand1.log(operand2);
            default -> throw new BadOperationException(operator.toString());
        };
    }

    /**
     * Evaluates the nary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateNaryExpressions(Map<String, MyNumber> identifiers) {
        // Cannot be null, since we checked the arity
        assert operands != null;
        MyNumber operand1 = operands.key.evaluate(identifiers);
        for (ListItem<ArithmeticExpressionNode> current = operands.next; current != null;
             current = current.next) {
            MyNumber operand2 = current.key.evaluate(identifiers);
            operand1 = switch (operator) {
                case ADD -> operand1.plus(operand2);
                case SUB -> operand1.minus(operand2);
                case MUL -> operand1.times(operand2);
                case DIV -> operand1.divide(operand2);
                default -> throw new BadOperationException(operator.toString());
            };
        }
        return operand1;
    }

    @Override
    public boolean isOperand() {
        return false;
    }

    @Override
    public boolean isOperation() {
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
        return new OperationExpressionNode(operator, clone);
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
