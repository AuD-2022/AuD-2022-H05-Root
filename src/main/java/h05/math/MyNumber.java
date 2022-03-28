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
}
