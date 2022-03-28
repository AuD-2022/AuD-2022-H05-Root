package h05.tree;

import h05.math.MyNumber;

import java.util.Map;

/**
 * Represents an arithmetic expression node.
 *
 * @author Nhan Huynh
 */
public interface ArithmeticExpressionNode extends Cloneable {

    /**
     * THe left bracket to group a term.
     */
    String LEFT_BRACKET = "(";

    /**
     * The right bracket to group a term.
     */
    String RIGHT_BRACKET = ")";


    /**
     * Evaluates the arithmetic expression.
     *
     * @param identifiers a map of identifiers and their values
     *
     * @return the result of the arithmetic expression
     */
    MyNumber evaluate(Map<String, MyNumber> identifiers);

    /**
     * Returns {@code true} if this node is an operand.
     *
     * @return {@code true} if this node is an operand
     */
    boolean isOperand();

    /**
     * Returns {@code true} if this node is an operator.
     *
     * @return {@code true} if this node is an operator
     */
    boolean isOperator();

    /**
     * Returns a clone of this node.
     *
     * @return a clone of this node
     */
    ArithmeticExpressionNode clone();
}
