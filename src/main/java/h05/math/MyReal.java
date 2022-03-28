package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Represents a real number in Racket.
 *
 * @author Nhan Huynh
 */
public class MyReal extends MyNumber {

    /**
     * The value of this real number.
     */
    private final BigDecimal value;

    /**
     * Constructs and initializes a real number with the specified value.
     *
     * @param value the value of the real number
     */
    public MyReal(BigDecimal value) {
        this.value = value;
    }

    private BigDecimal roundDown() {
        int sign = value.signum();
        BigDecimal rounded = value.abs().setScale(0, RoundingMode.DOWN);
        return sign == -1 ? rounded.negate() : rounded;
    }

    @Override
    public BigInteger toInteger() {
        return roundDown().toBigInteger();
    }

    @Override
    public Rational toRational() {
        return new Rational(roundDown().toBigInteger(), BigInteger.ONE);
    }

    @Override
    public BigDecimal toReal() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
