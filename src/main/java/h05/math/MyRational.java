package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;

import static h05.math.MyReal.ROUNDING_MODE;

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
    public BigDecimal toReal() {
        BigDecimal numerator = new BigDecimal(value.getNumerator())
            .setScale(MyReal.SCALE, ROUNDING_MODE);
        BigDecimal denominator = new BigDecimal(value.getDenominator())
            .setScale(MyReal.SCALE, ROUNDING_MODE);
        return numerator.divide(denominator, ROUNDING_MODE);
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
        return new MyRational(value.inverse());
    }

    @Override
    public MyNumber divide(MyNumber other) {
        if (other instanceof MyInteger) {
            return new MyRational(value.divide(other.toInteger()));
        }
        if (other instanceof MyReal) {
            return new MyReal(toReal().divide(other.toReal(), MyReal.ROUNDING_MODE));
        }
        return new MyRational(value.divide(other.toRational()));
    }

    @Override
    public MyNumber exp() {
        // TODO implement
        return null;
    }

    @Override
    public MyNumber ln() {
        // TODO implement
        return null;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
