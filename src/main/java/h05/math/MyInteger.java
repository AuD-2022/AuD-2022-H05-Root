package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Represents an integer in Racket.
 *
 * @author Nhan Huynh
 */
public class MyInteger extends MyNumber {

    /**
     * The value of the integer.
     */
    private final BigInteger value;

    /**
     * Constructs and initializes an integer with the specified value.
     *
     * @param value the value of the real number
     */
    public MyInteger(BigInteger value) {
        this.value = value;
    }

    @Override
    public BigInteger toInteger() {
        return value;
    }

    @Override
    public Rational toRational() {
        return new Rational(value, BigInteger.ONE);
    }

    @Override
    public BigDecimal toReal() {
        return new BigDecimal(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
