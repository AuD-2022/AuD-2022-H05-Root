package h05.math;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Utils for working with {@link BigDecimal}. We use base 10 because of {@link BigDecimal}s internal implementation.
 *
 * @author Jonas Renk
 */
public class MyMath {

    /**
     * The logarithm base 10 of the Euler's number.
     */
    private static final BigDecimal LOG_BASE_10_OF_E = new BigDecimal(
        "0.4342944819032518276511289189166050822943970058036665661144537831"
    );

    /**
     * Don't let anyone instantiate this class.
     */
    private MyMath() {
    }

    /**
     * Computes the natural logarithm of x (ln(x)) within 15 significant digits.
     *
     * @param x the number to compute the logarithm of
     *
     * @return the natural logarithm of x
     */
    public static BigDecimal ln(BigDecimal x) {
        return log10(x).divide(LOG_BASE_10_OF_E, MyReal.ROUNDING_MODE);
    }

    /**
     * Computes log base 10 of x (log_10(x)) within 15 significant digits.
     *
     * @param x the number to compute the logarithm of
     *
     * @return the log base 10 of x
     *
     * @throws ArithmeticException if x is not positive
     */
    public static BigDecimal log10(BigDecimal x) {

        if (x.signum() < 1) {
            throw new ArithmeticException("Only for positive numbers");
        }

        BigDecimal log10 = BigDecimal.ZERO.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        while (x.compareTo(BigDecimal.TEN) > 0) {
            log10 = log10.add(BigDecimal.ONE);
            x = x.divide(BigDecimal.TEN, MyReal.ROUNDING_MODE);
        }

        while (x.compareTo(BigDecimal.ONE) < 0) {
            log10 = log10.subtract(BigDecimal.ONE);
            x = x.multiply(BigDecimal.TEN);
        }

        BigDecimal rest = BigDecimal.valueOf(Math.log10(x.doubleValue()));
        return log10.add(rest);
    }

    /**
     * Returns x raised to the power of {@code 10} (x^10).
     *
     * @param x the exponent
     *
     * @return x raised to the power of {@code 10}
     *
     * @throws ArithmeticException if x is negative or if x is too large
     */
    public static BigDecimal pow10(BigDecimal x) {
        if (x.signum() == 0) {
            return BigDecimal.ONE;
        } else if (x.signum() < 0) {
            int intPart = x.toBigInteger().intValue() - 1;
            BigDecimal rest = x.subtract(BigDecimal.valueOf(intPart));

            BigDecimal a = BigDecimal.valueOf(Math.pow(10, rest.doubleValue()));
            BigInteger b = BigInteger.TEN.pow(-intPart);

            return a.divide(new BigDecimal(b), MyReal.ROUNDING_MODE);
        } else {
            int intPart = x.toBigInteger().intValueExact();
            BigDecimal rest = x.subtract(BigDecimal.valueOf(intPart));

            BigInteger a = BigInteger.TEN.pow(intPart);
            BigDecimal b = BigDecimal.valueOf(Math.pow(10, rest.doubleValue()));

            return new BigDecimal(a).multiply(b);
        }
    }

    /**
     * Returns Euler’s number raised to the power of x (exp(x)).
     *
     * @param x the exponent
     *
     * @return Euler’s number raised to the power of x
     */
    public static BigDecimal exp(BigDecimal x) {
        return pow10(x.multiply(LOG_BASE_10_OF_E));
    }
}
