package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * The abstract class Number represents the numbers of the programming language Racket in a very
 * simplified way.
 *
 * @author Nhan Huynh
 */
public abstract class MyNumber {

    /**
     * Returns the representation of this number as an integer.
     *
     * @return the representation of this number as an integer.
     */
    public abstract BigInteger toInteger();

    /**
     * Returns the representation of this number as a rational number.
     *
     * @return the representation of this number as a rational number.
     */
    public abstract Rational toRational();

    /**
     * Returns the representation of this number as a real number.
     *
     * @return the representation of this number as a real number.
     */
    public abstract BigDecimal toDecimal();
}
