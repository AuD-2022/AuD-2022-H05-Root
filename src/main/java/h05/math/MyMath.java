package h05.math;

import java.math.BigDecimal;

import static h05.math.MyReal.ROUNDING_MODE;

/**
 * Utils for working with {@link BigDecimal}.
 * We use base 10 because of {@link BigDecimal}s internal implementation.
 */
public class MyMath {

    private static final BigDecimal LOG_BASE_10_OF_E = new BigDecimal("0.4342944819032518276511289189166050822943970058036665661144537831");

    private MyMath() {}

    public static BigDecimal ln(BigDecimal x) {
        return MyMath.log10(x).divide(MyMath.LOG_BASE_10_OF_E, ROUNDING_MODE);
    }

    /**
     * Compute log base 10 of x within 15 significant digits.
     *
     * @param x number to log
     * @return log_10(x)
     * @throws ArithmeticException if x is not positive
     */
    public static BigDecimal log10(BigDecimal x) {
        if (x.signum() < 1) {
            throw new ArithmeticException("Only for positive numbers");
        }

        var log10 = BigDecimal.ZERO;

        while (x.compareTo(BigDecimal.TEN) > 0) {
            log10 = log10.add(BigDecimal.ONE);
            x = x.divide(BigDecimal.TEN, ROUNDING_MODE);
        }

        while (x.compareTo(BigDecimal.ONE) < 0) {
            log10 = log10.subtract(BigDecimal.ONE);
            x = x.multiply(BigDecimal.TEN);
        }

        var rest = BigDecimal.valueOf(Math.log10(x.doubleValue()));
        return log10.add(rest);
    }

    /**
     * Computes 10 to the x.
     *
     * @param x the exponent
     * @return 10**x
     * @throws ArithmeticException if x is negative or if x is to large
     */
    public static BigDecimal pow10(BigDecimal x) {
        if (x.signum() == 0) {
            return BigDecimal.ONE;
        }

        if (x.signum() < 0) {
            throw new ArithmeticException("Only for non negative numbers");
        }

        var intPart = x.toBigInteger().intValueExact();
        var rest = x.subtract(BigDecimal.valueOf(intPart));

        var a = BigDecimal.TEN.pow(intPart);
        var b = BigDecimal.valueOf(Math.pow(10, rest.doubleValue()));

        return a.multiply(b);
    }

    public static BigDecimal exp(BigDecimal x) {
        return pow10(x.multiply(LOG_BASE_10_OF_E));
    }
}
