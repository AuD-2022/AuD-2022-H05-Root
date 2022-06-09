package h05.math;

import h05.exception.Comparison;
import h05.exception.WrongOperandException;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The abstract class Number represents the numbers of the programming language Racket in a very simplified way.
 *
 * @author Nhan Huynh, Jonas Renk
 * @see <a href="https://docs.racket-lang.org/reference/number-types.html">
 * https://docs.racket-lang.org/reference/number-types.html</a>
 */
public abstract class MyNumber {

    /**
     * A matcher used to match a string to a {@link h05.math.MyInteger}.
     */
    private static final Matcher INTEGER_FORMAT = Pattern.compile("-?\\d+").matcher("");

    /**
     * A matcher used to match a string to a {@link MyReal}.
     */
    private static final Matcher REAL_FORMAT = Pattern.compile("-?\\d+\\.\\d+").matcher("");

    /**
     * A matcher used to match a string to a {@link MyRational}.
     */
    private static final Matcher RATIONAL_FORMAT = Pattern.compile("-?\\d+/\\d+").matcher("");

    /**
     * Parses the given token to a {@link MyNumber}.
     *
     * @param token the token to parse
     *
     * @return the parsed {@link MyNumber}
     */
    public static MyNumber parseNumber(String token) {
        if (INTEGER_FORMAT.reset(token).matches()) {
            return new MyInteger(new BigInteger(token));
        }
        if (REAL_FORMAT.reset(token).matches()) {
            return new MyReal(new BigDecimal(token));
        }
        if (RATIONAL_FORMAT.reset(token).matches()) {
            String[] data = token.split("/");
            return new MyRational(
                new Rational(new BigInteger(data[0]), new BigInteger(data[1]))
            );
        }
        throw new NumberFormatException(String.format("Invalid number format: %s", token));
    }

    /**
     * Returns {@code true} if the given value represents a {@link MyNumber}.
     *
     * @param value the value to check
     *
     * @return {@code true} if the given value represents a {@link MyNumber}
     */
    public static boolean isNumber(String value) {
        return INTEGER_FORMAT.reset(value).matches() || REAL_FORMAT.reset(value).matches()
            || RATIONAL_FORMAT.reset(value).matches();
    }

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
    public abstract BigDecimal toReal();

    /**
     * Returns {@code true} if this number is zero.
     *
     * @return {@code true} if this number is zero
     */
    public abstract boolean isZero();

    /**
     * Returns the hash code for this {@code MyNumber}. The hash code is computed by the value representation of this number.
     *
     * @return hash code for this {@code MyNumber}.
     *
     * @see #equals(Object)
     */
    @Override
    public abstract int hashCode();

    /**
     * Compares this {@code MyNumber} with the specified {@code Object} for equality. Two {@code MyNumber} objects equal only if
     * they are equal their class and their value representation. Therefore, 0.5 is not equal to 1/2 when compared by this.
     *
     * @param obj {@code Object} to which this {@code MyNumber} is to be compared.
     *
     * @return {@code true} if and only if the specified {@code Object} is a {@code MyNumber} whose value representation and class
     * are equal to this {@code MyNumber}'s.
     *
     * @see #hashCode
     */
    @Override
    public abstract boolean equals(@Nullable Object obj);

    /**
     * Returns the string representation of this {@code MyNumber}.
     *
     * @return the string representation of this {@code MyNumber}.
     */
    @Override
    public abstract String toString();

    /**
     * Returns a number whose value is {@code (-this)}.
     *
     * @return {@code -this}
     */
    public abstract MyNumber negate();

    /**
     * Returns the sum of this number and the neutral element 0 {@code 0 + this}.
     *
     * @return the sum of this number and the neutral element 0
     */
    public MyNumber plus() {
        return this;
    }

    /**
     * Returns the sum of this number and the given number ({@code this + other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise  the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to add
     *
     * @return the sum of this number and the given number
     */
    public abstract MyNumber plus(MyNumber other);

    /**
     * Returns the difference of this number and the neutral element 0 {@code 0 - this}.
     *
     * @return the difference of this number and the neutral element 0
     */
    public MyNumber minus() {
        return this.negate();
    }

    /**
     * Returns the difference of this number and the given number ({@code this - other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to subtract
     *
     * @return the difference of this number and the given number
     */
    public abstract MyNumber minus(MyNumber other);

    /**
     * Returns the product of this number and the neutral element 1 {@code 1 * this}.
     *
     * @return the product of this number and the neutral element 1
     */
    public MyNumber times() {
        return this;
    }

    /**
     * Returns the product of this number and the given number ({@code this * other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to multiply
     *
     * @return the product of this number and the given number
     */
    public abstract MyNumber times(MyNumber other);

    /**
     * Returns the quotient of this number and the neutral element 1 ({@code 1 / this}).
     *
     * <ol>
     *     <li>If the number is an integer, the result will be rational</li>
     *     <li>If the number is an real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * @return the quotient of this number and the neutral element 1
     *
     * @throws WrongOperandException if the number is 0
     */
    public abstract MyNumber divide();

    /**
     * Returns the quotient of this number and the given number ({@code this / other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an rational</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise the result will be rational</li>
     * </ol>
     *
     * <p>Notice if the result can be represented as an integer, it will be an integer.
     *
     * @param other the number to divide
     *
     * @return the quotient of this number and the given number
     *
     * @throws WrongOperandException if the given number is 0
     */
    public abstract MyNumber divide(MyNumber other);

    /**
     * Returns the square root of this number. The result will always be real or an integer.
     *
     * @return the square root of this number
     */
    public MyNumber sqrt() {
        return checkRealToInt(toReal().sqrt(MathContext.DECIMAL128));
    }

    /**
     * Returns {@code this} number raised to the power of {@code n} (x^n). The result will always be real or an integer.
     *
     * @param n the exponent
     *
     * @return {@code this} number raised to the power of {@code n}
     */
    public MyNumber expt(MyNumber n) {
        // pow(x, n) = x^n = exp(n * ln(x))
        var exponent = MyMath.log10(toReal()).multiply(n.toReal());
        return checkRealToInt(MyMath.pow10(exponent));
    }

    /**
     * Returns Euler’s number raised to the power of {@code this} number (exp(x)). The result will always be real or an integer.
     *
     * @return Euler’s number raised to the power of {@code this}
     *
     * @throws WrongOperandException if this number is not positive or the large
     */
    public MyNumber exp() {
        return checkRealToInt(MyMath.exp(toReal()));
    }

    /**
     * Returns the natural logarithm of this number (ln(x)). The result will always be real or an integer.
     *
     * @return the natural logarithm of this number
     *
     * @throws WrongOperandException if this number is not positive
     */
    public MyNumber ln() {
        BigDecimal real = toReal();

        if (real.signum() < 1) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyReal.ZERO);
        }

        return checkRealToInt(MyMath.ln(real));
    }

    /**
     * Returns the logarithm of this number with base {@code base} (log_x(y)). The result will always be real or an integer.
     *
     * @param base the base of the logarithm
     *
     * @return the logarithm of this number with base {@code base}
     *
     * @throws WrongOperandException if this number is not positive or the base is not positive
     */
    public MyNumber log(MyNumber base) {
        // log_x(y) = ln(y) / ln(x)

        var thisReal = toReal();
        var baseReal = base.toReal();

        if (baseReal.signum() < 1) {
            throw new WrongOperandException(base, Comparison.GREATER_THAN, MyInteger.ZERO);
        }

        if (thisReal.signum() < 1) {
            throw new WrongOperandException(this, Comparison.GREATER_THAN, MyInteger.ZERO);
        }

        var top = MyMath.log10(thisReal);
        var bottom = MyMath.log10(baseReal);
        return checkRealToInt(top.divide(bottom, MyReal.ROUNDING_MODE));
    }

    /**
     * Checks if the given real number can be represented as an integer.
     *
     * @param real the real number to check
     *
     * @return an integer if the real number can be represented as an integer, otherwise the real number
     */
    protected MyNumber checkRealToInt(BigDecimal real) {
        BigDecimal stripped = real
            .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE)
            .stripTrailingZeros();

        if (stripped.scale() <= 0) {
            return new MyInteger(stripped.toBigIntegerExact());
        }
        return new MyReal(real);
    }

    /**
     * Checks if the given rational number can be represented as an integer.
     *
     * @param rational the real number to check
     *
     * @return an integer if the rational number can be represented as an integer, otherwise the rational number
     */
    protected MyNumber checkRationalToInt(Rational rational) {
        if (rational.getDenominator().equals(BigInteger.ONE)) {
            return new MyInteger(rational.getNumerator());
        }
        return new MyRational(rational);
    }
}
