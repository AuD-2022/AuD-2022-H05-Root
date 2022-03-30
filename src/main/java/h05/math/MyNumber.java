package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The abstract class Number represents the numbers of the programming language Racket in a very
 * simplified way.
 *
 * @author Nhan Huynh
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
    private static final Matcher RATIONAL_FORMAT = Pattern.compile("-?\\(\\d+ / \\d+\\)")
        .matcher("");

    /**
     * Parses the given token to a {@link MyNumber}.
     *
     * @param token the token to parse
     *
     * @return the parsed {@link MyNumber}
     */
    public static MyNumber parseNumber(String token) {
        if (INTEGER_FORMAT.reset(token).matches()) {
            return new MyReal(new BigDecimal(token));
        }
        if (REAL_FORMAT.reset(token).matches()) {
            return new MyReal(new BigDecimal(token));
        }
        if (RATIONAL_FORMAT.reset(token).matches()) {
            String[] data = token.substring(1, token.length() - 1).replace(" ", "").split("/");
            return new MyRational(
                new Rational(new BigInteger(data[0]), new BigInteger(data[1]))
            );
        }
        throw new NumberFormatException(String.format("Invalid number format: %s", token));
    }

    /**
     * Returns {@code true} if the given token represents a {@link MyNumber}.
     *
     * @param token the token to check
     *
     * @return {@code true} if the given token represents a {@link MyNumber}
     */
    public static boolean isNumber(String token) {
        return INTEGER_FORMAT.reset(token).matches() || REAL_FORMAT.reset(token).matches()
            || RATIONAL_FORMAT.reset(token).matches();
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
    public abstract MyNumber plus();

    /**
     * Returns the sum of this number and the given number ({@code this + other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise (both numbers are rational,) the result will be rational</li>
     * </ol>
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
    public abstract MyNumber minus();

    /**
     * Returns the difference of this number and the given number ({@code this - other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise (both numbers are rational,) the result will be rational</li>
     * </ol>
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
    public abstract MyNumber times();

    /**
     * Returns the product of this number and the given number ({@code this * other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an integer</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise (both numbers are rational,) the result will be rational</li>
     * </ol>
     *
     * @param other the number to multiply
     *
     * @return the product of this number and the given number
     */
    public abstract MyNumber times(MyNumber other);

    /**
     * Returns the quotient of this number and the neutral element 1 ({@code 1 / this}).
     * <ol>
     *     <li>If the number is an integer, the result will be rational</li>
     *      <li>If the number is an real, the result will be real</li>
     *     <li>Otherwise (the number rational,) the result will be rational</li>
     * </ol>
     *
     * @return the quotient of this number and the neutral element 1
     */
    public abstract MyNumber divide();

    /**
     * Returns the quotient of this number and the given number ({@code this / other}).
     *
     * <ol>
     *     <li>If both numbers are integers, the result will be an rational</li>
     *     <li>If one of the number is real, the result will be real</li>
     *     <li>Otherwise (both numbers are rational,) the result will be rational</li>
     * </ol>
     *
     * @param other the number to divide
     *
     * @return the quotient of this number and the given number
     */
    public abstract MyNumber divide(MyNumber other);
}
