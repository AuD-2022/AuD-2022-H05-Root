package h05.math;

import java.math.BigInteger;

/**
 * Represents a rational number (fraction). The fraction is stored as a numerator and denominator and the sign will be stored in
 * the numerator.
 *
 * @author Nhan Huynh
 */
public class Rational {

    /**
     * The constant 0 as a {@link Rational}.
     */
    public static final Rational ZERO = new Rational(BigInteger.ZERO, BigInteger.ONE);

    /**
     * The constant 1 as a {@link Rational}.
     */
    public static final Rational ONE = new Rational(BigInteger.ONE, BigInteger.ONE);

    /**
     * The numerator of this rational number.
     */
    private final BigInteger numerator;

    /**
     * The denominator of this rational number.
     */
    private final BigInteger denominator;

    /**
     * Constructs and initializes a rational number with the specified numerator and denominator.
     *
     * @param numerator   the numerator of the rational number
     * @param denominator the denominator of the rational number
     *
     * @throws ArithmeticException if the denominator is zero
     */
    public Rational(BigInteger numerator, BigInteger denominator) {
        if (denominator.signum() == 0) {
            throw new ArithmeticException("Division by zero");
        }

        BigInteger divisor = numerator.gcd(denominator);
        int signNumerator = numerator.signum();
        int signDenominator = denominator.signum();

        // The numerator contains the sign of the rational number
        if (signNumerator == -1 && signDenominator == -1 || signNumerator == 1 && signDenominator == -1) {
            this.numerator = numerator.negate().divide(divisor);
            this.denominator = denominator.negate().divide(divisor);
        } else {
            this.numerator = numerator.divide(divisor);
            this.denominator = denominator.divide(divisor);
        }
    }

    /**
     * Returns the numerator of this rational number.
     *
     * @return the numerator of this rational number
     */
    public BigInteger getNumerator() {
        return numerator;
    }

    /**
     * Returns the denominator of this rational number.
     *
     * @return the denominator of this rational number
     */
    public BigInteger getDenominator() {
        return denominator;
    }

    /**
     * Returns a rational whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public Rational negate() {
        return new Rational(numerator.negate(), denominator);
    }

    /**
     * Returns the inverse of this rational number ({@code denominator / numerator}.
     *
     * @return the inverse of this rational number
     */
    public Rational inverse() {
        return new Rational(denominator, numerator);
    }

    /**
     * Returns the sum of this rational number and the integer number.
     *
     * @param other the integer number to add
     *
     * @return the sum of this rational number and the integer number
     */
    public Rational plus(BigInteger other) {
        return new Rational(numerator.add(denominator.multiply(other)), denominator);
    }

    /**
     * Returns the sum of this rational number and the rational number.
     *
     * @param other the rational number to add
     *
     * @return the sum of this rational number and the rational number
     */
    public Rational plus(Rational other) {
        return new Rational(
            numerator.multiply(other.denominator).add(denominator.multiply(other.numerator)),
            denominator.multiply(other.denominator)
        );
    }

    /**
     * Returns the difference of this rational number and the integer number.
     *
     * @param other the integer number to subtract
     *
     * @return the difference of this rational number and the integer number
     */
    public Rational minus(BigInteger other) {
        return new Rational(numerator.subtract(denominator.multiply(other)), denominator);
    }

    /**
     * Returns the difference of this rational number and the rational number.
     *
     * @param other the rational number to subtract
     *
     * @return the difference of this rational number and the rational number
     */
    public Rational minus(Rational other) {
        return new Rational(
            numerator.multiply(other.denominator).subtract(denominator.multiply(other.numerator)),
            denominator.multiply(other.denominator)
        );
    }

    /**
     * Returns the product of this rational number and the integer number.
     *
     * @param other the integer number to multiply
     *
     * @return the product of this rational number and the integer number
     */
    public Rational times(BigInteger other) {
        return new Rational(numerator.multiply(other), denominator);
    }

    /**
     * Returns the product of this rational number and the rational number.
     *
     * @param other the rational number to multiply
     *
     * @return the product of this rational number and the rational number
     */
    public Rational times(Rational other) {
        return new Rational(
            numerator.multiply(other.numerator),
            denominator.multiply(other.denominator)
        );
    }

    /**
     * Returns the quotient of this rational number and the integer number.
     *
     * @param other the integer number to divide
     *
     * @return the quotient of this rational number and the integer number
     */
    public Rational divide(BigInteger other) {
        return new Rational(numerator, denominator.multiply(other));
    }

    /**
     * Returns the quotient of this rational number and the rational number.
     *
     * @param other the rational number to divide
     *
     * @return the quotient of this rational number and the rational number
     */
    public Rational divide(Rational other) {
        return new Rational(
            numerator.multiply(other.denominator),
            denominator.multiply(other.numerator)
        );
    }

    @Override
    public String toString() {
        if (numerator.signum() == -1) {
            return String.format("-(%s / %s)", numerator.negate(), denominator);
        }
        return String.format("(%s / %s)", numerator, denominator);
    }
}
