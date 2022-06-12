package h05.h2_2;

import h05.math.*;
import h05.utils.ReflectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

class ArithmeticOperations {

    static MyNumber sqrt(MyNumber value) {
        return tryConvertRealToInteger(myNumberToBigDecimal(value).sqrt(MathContext.DECIMAL128)
            .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
    }

    static MyNumber exp(MyNumber value) {
        return tryConvertRealToInteger(exp(myNumberToBigDecimal(value))
            .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE));
    }

    static MyNumber expt(MyNumber value, MyNumber exponent) {
        return tryConvertRealToInteger(pow10(log10(myNumberToBigDecimal(value)).multiply(myNumberToBigDecimal(exponent))));
    }

    static MyNumber ln(MyNumber value) {
        return tryConvertRealToInteger(ln(myNumberToBigDecimal(value)));
    }

    static MyNumber log(MyNumber value, MyNumber exponent) {
        return tryConvertRealToInteger(log10(myNumberToBigDecimal(value)).divide(log10(myNumberToBigDecimal(exponent)),
            MyReal.ROUNDING_MODE));
    }

    private static BigDecimal myNumberToBigDecimal(MyNumber myNumber) {
        BigDecimal bigDecimal;
        if (myNumber instanceof MyInteger myInteger) {
            bigDecimal = new BigDecimal(ReflectionUtils.<MyInteger, BigInteger>getFieldValue(MyInteger.class, "value", myInteger))
                .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
        } else if (myNumber instanceof MyRational myRational) {
            Rational rational = ReflectionUtils.getFieldValue(MyRational.class, "value", myRational);
            BigDecimal numerator = new BigDecimal(rational.getNumerator())
                .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
            BigDecimal denominator = new BigDecimal(rational.getDenominator())
                .setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
            bigDecimal = numerator.divide(denominator, MyReal.ROUNDING_MODE);
        } else {
            bigDecimal = ReflectionUtils.getFieldValue(MyReal.class, "value", (MyReal) myNumber);
        }
        return bigDecimal.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
    }

    private static MyNumber tryConvertRealToInteger(BigDecimal bigDecimal) {
        if (bigDecimal.stripTrailingZeros().scale() <= 0) {
            return new MyInteger(bigDecimal.toBigInteger());
        } else {
            return new MyReal(bigDecimal);
        }
    }

    /*
     * Copied members from h05.math.MyMath
     */


    /**
     * The logarithm base 10 of the Euler's number.
     */
    private static final BigDecimal LOG_BASE_10_OF_E = new BigDecimal(
        "0.4342944819032518276511289189166050822943970058036665661144537831"
    );

    /**
     * Computes the natural logarithm of x (ln(x)) within 15 significant digits.
     *
     * @param x the number to compute the logarithm of
     *
     * @return the natural logarithm of x
     */
    private static BigDecimal ln(BigDecimal x) {
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
    private static BigDecimal log10(BigDecimal x) {

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
    private static BigDecimal pow10(BigDecimal x) {
        if (x.signum() == 0) {
            return BigDecimal.ONE;
        } else if (x.signum() < 0) {
            int intPart = x.toBigInteger().intValue() - 1;
            BigDecimal rest = x.subtract(BigDecimal.valueOf(intPart));

            BigDecimal a = BigDecimal.valueOf(Math.pow(10, rest.doubleValue()));
            BigDecimal b = BigDecimal.TEN.pow(-intPart);

            return a.divide(b, MyReal.ROUNDING_MODE);
        } else {
            int intPart = x.toBigInteger().intValueExact();
            BigDecimal rest = x.subtract(BigDecimal.valueOf(intPart));

            BigDecimal a = BigDecimal.TEN.pow(intPart);
            BigDecimal b = BigDecimal.valueOf(Math.pow(10, rest.doubleValue()));

            return a.multiply(b);
        }
    }

    /**
     * Returns Euler’s number raised to the power of x (exp(x)).
     *
     * @param x the exponent
     *
     * @return Euler’s number raised to the power of x
     */
    private static BigDecimal exp(BigDecimal x) {
        return pow10(x.multiply(LOG_BASE_10_OF_E));
    }
}
