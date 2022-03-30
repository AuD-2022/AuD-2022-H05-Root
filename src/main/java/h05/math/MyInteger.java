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
        return new BigDecimal(value).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
    }

    @Override
    public MyNumber negate() {
        return new MyInteger(value.negate());
    }

    @Override
    public MyNumber plus() {
        return this;
    }

    @Override
    public MyNumber plus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.add((other.toInteger())));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().add(other.toReal()));
        }
        return new MyRational(other.toRational().plus(value));
    }

    @Override
    public MyNumber minus() {
        return new MyInteger(value.negate());
    }

    @Override
    public MyNumber minus(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.subtract(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().subtract(other.toReal()));
        }
        return new MyRational(other.negate().toRational().plus(value));
    }

    @Override
    public MyNumber times() {
        return this;
    }

    @Override
    public MyNumber times(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyInteger(value.multiply(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().multiply(other.toReal()));
        }
        return new MyRational(other.toRational().times(value));
    }

    @Override
    public MyNumber divide() {
        return new MyRational(new Rational(BigInteger.ONE, value));
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyRational(new Rational(value, other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
        return new MyRational(other.toRational().inverse().times(value));
    }

    @Override
    public MyNumber sqrt() {
        return new MyReal(toReal()).sqrt();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
