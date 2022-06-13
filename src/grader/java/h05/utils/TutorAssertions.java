package h05.utils;

import org.opentest4j.AssertionFailedError;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TutorAssertions {

    public static void assertBigIntegerInRange(BigInteger actual, BigInteger lowerBound, BigInteger upperBound, String message) {
        if (actual.compareTo(lowerBound) < 0 || actual.compareTo(upperBound) > 0) {
            throw new AssertionFailedError(message, "A value between %s and %s".formatted(lowerBound, upperBound), actual);
        }
    }

    public static void assertBigIntegerInRange(BigInteger actual, BigInteger lowerBound, BigInteger upperBound) {
        assertBigIntegerInRange(actual, lowerBound, upperBound, "Value is outside of the allowed range");
    }

    public static void assertBigDecimalInRange(BigDecimal actual, BigDecimal lowerBound, BigDecimal upperBound, String message) {
        if (actual.compareTo(lowerBound) < 0 || actual.compareTo(upperBound) > 0) {
            throw new AssertionFailedError(message, "A value between %s and %s".formatted(lowerBound, upperBound), actual);
        }
    }

    public static void assertBigDecimalInRange(BigDecimal actual, BigDecimal lowerBound, BigDecimal upperBound) {
        assertBigDecimalInRange(actual, lowerBound, upperBound, "Value is outside of the allowed range");
    }

    public static void assertInRange(double actual, double lowerBound, double upperBound, String message) {
        if (actual < lowerBound || actual > upperBound) {
            throw new AssertionFailedError(message, "A value between %f and %f".formatted(lowerBound, upperBound), actual);
        }
    }

    public static void assertInRange(double actual, double lowerBound, double upperBound) {
        assertInRange(actual, lowerBound, upperBound, "Value is outside of the allowed range");
    }
}
