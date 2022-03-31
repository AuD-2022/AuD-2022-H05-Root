package h05.tree;

import h05.math.MyInteger;
import h05.math.MyNumber;
import h05.math.MyRational;
import h05.math.MyReal;
import h05.math.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;
import java.util.Objects;

/**
 * This class represents a literal operand arithmetic expression node. A literal operand is a {@link MyNumber}.
 *
 * <p>Example:
 * <ul>
 *     <li>Literal node with the number 2.5/li>
 * </ul>
 *
 * <pre>{@code
 *    LiteralExpressionNode node = new LiteralExpressionNode(new MyReal(2.5));
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LiteralExpressionNode extends OperandExpressionNode {

    /**
     * The literal operand.
     */
    private MyNumber value;

    /**
     * Constructs and initializes a literal operand arithmetic expression node with the given value.
     *
     * @param value the literal operand
     *
     * @throws NullPointerException if the value is {@code null}
     */
    public LiteralExpressionNode(MyNumber value) {
        Objects.requireNonNull(value, "value null");
        this.value = value;
    }

    /**
     * Returns the literal operand.
     *
     * @return the literal operand
     */
    public MyNumber getValue() {
        return value;
    }

    @Override
    public MyNumber evaluate(Map<String, MyNumber> identifiers) {
        return value;
    }

    @Override
    public ArithmeticExpressionNode clone() {
        MyNumber number;
        if (value instanceof MyInteger) {
            MyInteger integer = (MyInteger) value;
            number = new MyInteger(new BigInteger(integer.toInteger().toString()));
        } else if (value instanceof MyRational) {
            MyRational rational = (MyRational) value;
            number = new MyRational(
                new Rational(
                    new BigInteger(rational.toRational().getNumerator().toString()),
                    new BigInteger(rational.toRational().getDenominator().toString())
                )
            );
        } else {
            MyReal real = (MyReal) value;
            number = new MyReal(new BigDecimal(real.toReal().toString()));
        }
        return new LiteralExpressionNode(number);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
