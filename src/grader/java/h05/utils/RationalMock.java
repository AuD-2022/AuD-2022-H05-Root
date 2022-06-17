package h05.utils;

import h05.math.Rational;
import org.mockito.Answers;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Objects;

public class RationalMock {

    public static Rational getInstance(BigInteger numerator, BigInteger denominator) {
        Rational rational = Mockito.mock(Rational.class, Answers.CALLS_REAL_METHODS);
        Field numeratorField = ReflectionUtils.getField(Rational.class, "numerator");
        Field denominatorField = ReflectionUtils.getField(Rational.class, "denominator");

        try {
            Objects.requireNonNull(numerator, "numerator null");
            Objects.requireNonNull(denominator, "denominator null");
            if (denominator.signum() == 0) {
                throw new ArithmeticException("Division by zero");
            }

            BigInteger divisor = numerator.gcd(denominator);
            int signNumerator = numerator.signum();
            int signDenominator = denominator.signum();

            // The numerator contains the sign of the rational number
            if (signNumerator == -1 && signDenominator == -1 || signNumerator == 1 && signDenominator == -1) {
                numeratorField.set(rational, numerator.negate().divide(divisor));
                denominatorField.set(rational, denominator.negate().divide(divisor));
            } else {
                numeratorField.set(rational, numerator.divide(divisor));
                denominatorField.set(rational, denominator.divide(divisor));
            }
        } catch (IllegalAccessException ignored) {}

        return rational;
    }
}
