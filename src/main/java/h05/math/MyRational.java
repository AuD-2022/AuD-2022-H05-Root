package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

import static h05.math.MyReal.ROUNDING_MODE;

/**
 * Represents a rational number in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyRational extends MyNumber {

    /**
     * The {@link MyNumber} 0 as a {@link MyRational}.
     */
    public static final MyNumber ZERO = new MyRational(Rational.ZERO);

    /**
     * The {@link MyNumber} 1 as a {@link MyRational}.
     */
    public static final MyNumber ONE = new MyRational(Rational.ONE);

    /**
     * The value of this rational number.
     */
    private final Rational value;

    /**
     * Constructs and initializes a rational number with the specified value.
     *
     * @param value the value of the rational number
     *
     * @throws NullPointerException if the value is null
     */
    public MyRational(Rational value) {
        this.value = Objects.requireNonNull(value, "value null");
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
    public BigDecimal toReal() {
        BigDecimal numerator = new BigDecimal(value.getNumerator())
            .setScale(MyReal.SCALE, ROUNDING_MODE);
        BigDecimal denominator = new BigDecimal(value.getDenominator())
            .setScale(MyReal.SCALE, ROUNDING_MODE);
        return numerator.divide(denominator, ROUNDING_MODE);
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
        if (!(o instanceof MyRational number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyRational(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().add(other.toReal()));
        }
        return checkRationalToInt(value.plus(other.toRational()));
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().subtract(other.toReal()));
        }
        return checkRationalToInt(value.minus(other.toRational()));
    }

    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().multiply(other.toReal()));
        }
        return checkRationalToInt(value.times(other.toRational()));
    }

    @Override
    public MyNumber divide() {
        if (isZero()) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, zero());
        }
        return new MyRational(value.inverse());
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.isZero()) {
            throw new WrongOperandException(other, Comparison.GREATER_THAN, other.zero());
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
        return checkRationalToInt(value.divide(other.toRational()));
    }

    @Override
    public MyNumber zero() {
        return ZERO;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
