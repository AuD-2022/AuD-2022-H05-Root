package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * Represents a real number in Racket.
 *
 * @author Nhan Huynh
 */
public class MyReal extends MyNumber {

    /**
     * The scale of the real number for inexact numbers.
     */
    public static final int SCALE = 6;

    /**
     * The rounding mode of the real number for inexact numbers.
     */
    public static RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

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
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    private BigDecimal round() {
        int sign = value.signum();
        BigDecimal rounded = value.abs().setScale(SCALE, ROUNDING_MODE);
        return sign == -1 ? rounded.negate() : rounded;
    }

    @Override
    public BigInteger toInteger() {
        return round().toBigInteger();
    }

    @Override
    public Rational toRational() {
        return new Rational(round().toBigInteger(), BigInteger.ONE);
    }

    @Override
    public BigDecimal toReal() {
        return value;
    }

    @Override
    public MyNumber negate() {
        return new MyReal(value.negate());
    }

    @Override
    public MyNumber plus() {
        return this;
    }

    @Override
    public MyNumber plus(MyNumber other) {
        return new MyReal(value.add(other.toReal()));
    }

    @Override
    public MyNumber minus() {
        return new MyReal(value.negate());
    }

    @Override
    public MyNumber minus(MyNumber other) {
        return new MyReal(value.subtract(other.toReal()));
    }

    @Override
    public MyNumber times() {
        return this;
    }

    @Override
    public MyNumber times(MyNumber other) {
        return new MyReal(value.multiply(other.toReal()));
    }

    @Override
    public MyNumber divide() {
        return new MyReal(
            BigDecimal.ONE.setScale(SCALE, ROUNDING_MODE).divide(value, ROUNDING_MODE)
        );
    }

    @Override
    public MyNumber divide(MyNumber other) {
        return new MyReal(value.divide(other.toReal(), ROUNDING_MODE));
    }

    @Override
    public MyNumber sqrt() {
        return new MyReal(value.sqrt(MathContext.DECIMAL128));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
