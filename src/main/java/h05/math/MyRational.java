package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyRational)) {
            return false;
        }
        MyRational number = (MyRational) o;
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyRational(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyRational(value.plus(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().add(other.toReal()));
        }
        return new MyRational(value.plus(other.toRational()));
    }

    @Override
    public MyNumber minus() {
        return new MyRational(value.negate());
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyRational(value.minus(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().subtract(other.toReal()));
        }
        return new MyRational(value.minus(other.toRational()));
    }

    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyRational(value.times(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().multiply(other.toReal()));
        }
        return new MyRational(value.times(other.toRational()));
    }

    @Override
    public MyNumber divide() {
        if(isZero()) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, ZERO);
        }
        return new MyRational(value.inverse());
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (isZero()) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, ZERO);
        }
        if (other.isZero()) {
            throw new WrongOperandException(other, Comparison.GREATER_THAN, ZERO);
        }
        if (other instanceof MyInteger) {
            return new MyRational(value.divide(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
        return new MyRational(value.divide(other.toRational()));
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
