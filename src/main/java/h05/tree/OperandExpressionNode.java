package h05.tree;


/**
 * This class represents an abstract operand arithmetic expression node.
 *
 * @author Nhan Huynh
 */
public abstract class OperandExpressionNode implements ArithmeticExpressionNode {

    public abstract ArithmeticExpressionNode clone();

    @Override
    public boolean isOperand() {
        return true;
    }

    @Override
    public boolean isOperator() {
        return false;
    }
}
