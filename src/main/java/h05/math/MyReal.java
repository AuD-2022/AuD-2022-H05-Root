package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Represents a real number in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyReal extends MyNumber {

    /**
     * The scale of the real number for inexact numbers.
     */
    public static final int SCALE = 15;

    /**
     * The rounding mode of the real number for inexact numbers.
     */
    public static final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP;

    /**
     * The constant {@link MyNumber} 0 as a {@link MyReal}.
     */
    public static final MyNumber ZERO = new MyReal(BigDecimal.ZERO);

    /**
     * The constant {@link MyNumber} 1 as a {@link MyReal}.
     */
    public static final MyNumber ONE = new MyReal(BigDecimal.ONE);

    /**
     * The value of this real number.
     */
    private final BigDecimal value;

    /**
     * Constructs and initializes a real number with the specified value.
     *
     * @param value the value of the real number
     *
     * @throws NullPointerException if the value is null
     */
    public MyReal(BigDecimal value) {
        Objects.requireNonNull(value, "value null");
        this.value = value.setScale(SCALE, ROUNDING_MODE);
    }

    @Override
    public BigInteger toInteger() {
        return value.toBigInteger();
    }

    @Override
    public Rational toRational() {
        BigInteger numerator = value.multiply(BigDecimal.TEN.setScale(SCALE,ROUNDING_MODE).pow(SCALE)).toBigInteger();
        BigInteger denominator = BigInteger.TEN.pow(SCALE);
        return new Rational(numerator, denominator);
    }

    @Override
    public BigDecimal toReal() {
        return value;
    }

    @Override
    public boolean isZero() {
        return this.equals(ZERO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyReal number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyReal(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        return checkRealToInt(value.add(other.toReal()));
    }

    @Override
    public MyNumber minus(MyNumber other) {
        return checkRealToInt(value.subtract(other.toReal()));
    }

    @Override
    public MyNumber times(MyNumber other) {
        return checkRealToInt(value.multiply(other.toReal()));
    }

    @Override
    public MyNumber divide() {
        if (isZero()) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, zero());
        }
        return new MyReal(
            BigDecimal.ONE.setScale(SCALE, ROUNDING_MODE).divide(value, ROUNDING_MODE)
        );
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.isZero()) {
            throw new WrongOperandException(other, Comparison.GREATER_THAN, other.zero());
        }
        return checkRealToInt(value.divide(other.toReal(), ROUNDING_MODE));
    }

    @Override
    public MyNumber zero() {
        return ZERO;
    }

    @Override
    public String toString() {
        return value.stripTrailingZeros().toString();
    }
}
