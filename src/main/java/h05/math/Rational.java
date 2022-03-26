package h05.math;

import java.math.BigInteger;

/**
 * Represents a rational number (fraction). The fraction is stored as a numerator and denominator
 * and the sign will be stored in the numerator.
 *
 * @author Nhan Huynh
 */
public class Rational {

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
            throw new ArithmeticException("/ by zero");
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

    @Override
    public String toString() {
        if (numerator.signum() == -1) {
            return String.format("-(%s / %s)", numerator.negate(), denominator);
        }
        return String.format("(%s / %s)", numerator, denominator);
    }
}
