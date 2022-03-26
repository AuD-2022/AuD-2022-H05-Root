package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Represents a rational number in Racket.
 *
 * @author Nhan Huynh
 */
public class MyRational extends MyNumber {

    /**
     * The value of this rational number.
     */
    private final Rational value;

    /**
     * Constructs and initializes a rational number with the specified value.
     *
     * @param value the value of the rational number
     */
    public MyRational(Rational value) {
        this.value = value;
    }

    @Override
    public BigInteger toInteger() {
        return value.getNumerator().divide(value.getDenominator());
    }

    @Override
    public Rational toRational() {
        return value;
    }

    @Override
    public BigDecimal toDecimal() {
        BigDecimal numerator = new BigDecimal(value.getNumerator());
        BigDecimal denominator = new BigDecimal(value.getDenominator());
        return numerator.divide(denominator);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
