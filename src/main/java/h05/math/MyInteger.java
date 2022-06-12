package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * Represents an integer in Racket.
 *
 * @author Nhan Huynh
 */
public final class MyInteger extends MyNumber {

    /**
     * The constant {@link MyNumber} 0 as a {@link MyInteger}.
     */
    public static final MyNumber ZERO = new MyInteger(BigInteger.ZERO);

    /**
     * The constant {@link MyNumber} 1 as a {@link MyInteger}.
     */
    public static final MyNumber ONE = new MyInteger(BigInteger.ONE);

    /**
     * The value of the integer.
     */
    private final BigInteger value;

    /**
     * Constructs and initializes an integer with the specified value.
     *
     * @param value the value of the real number
     *
     * @throws NullPointerException if the value is null
     */
    public MyInteger(BigInteger value) {
        this.value = Objects.requireNonNull(value, "value null");
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
        return new BigDecimal(value).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
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
        if (!(o instanceof MyInteger number)) {
            return false;
        }
        return value.equals(number.value);
    }

    @Override
    public MyNumber negate() {
        return new MyInteger(value.negate());
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.add((other.toInteger())));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().add(other.toReal()));
        }
        return checkRationalToInt(other.toRational().plus(value));
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.subtract(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().subtract(other.toReal()));
        }
        return checkRationalToInt(other.toRational().minus(value));
    }

    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.multiply(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().multiply(other.toReal()));
        }
        return checkRationalToInt(other.toRational().times(value));
    }

    @Override
    public MyNumber divide() {
        if (isZero()) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, zero());
        }
        return new MyRational(new Rational(BigInteger.ONE, value));
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other.isZero()) {
            throw new WrongOperandException(other, Comparison.GREATER_THAN, other.zero());
        }
        if (other instanceof MyInteger) {
            return checkRationalToInt(new Rational(value, other.toInteger()));
        }
        if (other instanceof MyReal) {
            return checkRealToInt(toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
        return checkRationalToInt(other.toRational().divide(value));
    }

    @Override
    public MyNumber expt(MyNumber n) {
        if (n instanceof MyInteger i) {
            return new MyInteger(value.pow(i.value.intValueExact()));
        }
        return super.expt(n);
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
