package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyInteger;
import h05.math.MyNumber;

import java.math.BigInteger;
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
        switch (size) {
            case 0:
                return evaluateNullaryExpressions(identifiers);
            case 1:
                return evaluateUnaryExpressions(identifiers);
            case 2:
                return evaluateBinaryExpressions(identifiers);
            default:
                return evaluateNaryExpressions(identifiers);
        }
    }

    /**
     * Evaluates the nullary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateNullaryExpressions(Map<String, MyNumber> identifiers) {
        switch (operator) {
            case ADD:
                return new MyInteger(BigInteger.ZERO);
            case MUL:
                return new MyInteger(BigInteger.ONE);
            default:
                throw new BadOperationException(operator.toString());
        }
    }

    /**
     * Evaluates the unary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateUnaryExpressions(Map<String, MyNumber> identifiers) {
        MyNumber operand = operands.key.evaluate(identifiers);
        switch (operator) {
            case ADD:
                return operand.plus();
            case SUB:
                return operand.minus();
            case MUL:
                return operand.times();
            case DIV:
                return operand.divide();
            case POW:
                return operand.exp();
            case LOG:
                return operand.ln();
            case SQRT:
                return operand.sqrt();
            default:
                throw new BadOperationException(operator.toString());
        }
    }

    /**
     * Evaluates the binary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateBinaryExpressions(Map<String, MyNumber> identifiers) {
        MyNumber operand1 = operands.key.evaluate(identifiers);
        MyNumber operand2 = operands.next.key.evaluate(identifiers);
        switch (operator) {
            case ADD:
                return operand1.plus(operand2);
            case SUB:
                return operand1.minus(operand2);
            case MUL:
                return operand1.times(operand2);
            case DIV:
                return operand1.divide(operand2);
            case POW:
                return operand1.expt(operand2);
            case LOG:
                return operand1.log(operand2);
            default:
                throw new BadOperationException(operator.toString());
        }
    }

    /**
     * Evaluates the nary expressions.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the evaluation
     */
    private MyNumber evaluateNaryExpressions(Map<String, MyNumber> identifiers) {
        MyNumber operand1 = operands.key.evaluate(identifiers);
        for (ListItem<ArithmeticExpressionNode> current = operands.next; current != null;
             current = current.next) {
            MyNumber operand2 = current.key.evaluate(identifiers);
            switch (operator) {
                case ADD:
                    operand1 = operand1.plus(operand2);
                    break;
                case SUB:
                    operand1 = operand1.minus(operand2);
                    break;
                case MUL:
                    operand1 = operand1.times(operand2);
                    break;
                case DIV:
                    operand1 = operand1.divide(operand2);
                    break;
                default:
                    throw new BadOperationException(operator.toString());
            }
        }
        return operand1;
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
