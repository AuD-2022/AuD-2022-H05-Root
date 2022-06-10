package h05.h1_1;

import h05.math.Rational;
import h05.provider.BigIntegerProvider;
import kotlin.Pair;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigInteger;

import static h05.utils.ReflectionUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class RationalTests {

    @ParameterizedTest
    @ArgumentsSource(BigIntegerProvider.class)
    public void testConstructorPositive(BigInteger numerator, BigInteger denominator) {
        if (numerator.signum() == -1) numerator = numerator.negate();
        if (denominator.signum() == -1) denominator = denominator.negate();
        Rational instance = new Rational(numerator, denominator);
        Pair<BigInteger, BigInteger> expected = calculateExpected(numerator, denominator);

        assertEquals(expected.getFirst(), getFieldValue(getField(Rational.class, "numerator"), instance),
            "Field [[[numerator]]] does not have correct value");
        assertEquals(expected.getSecond(), getFieldValue(getField(Rational.class, "denominator"), instance),
            "Field [[[denominator]]] does not have correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(BigIntegerProvider.class)
    public void testConstructorNegative(BigInteger numerator, BigInteger denominator) {
        if (numerator.signum() == 1) numerator = numerator.negate();
        if (denominator.signum() == -1) denominator = denominator.negate();
        Rational instance = new Rational(numerator, denominator);
        Pair<BigInteger, BigInteger> expected = calculateExpected(numerator, denominator);

        assertEquals(expected.getFirst(), getFieldValue(getField(Rational.class, "numerator"), instance),
            "Field [[[numerator]]] does not have correct value");
        assertEquals(expected.getSecond(), getFieldValue(getField(Rational.class, "denominator"), instance),
            "Field [[[denominator]]] does not have correct value");
    }

    private static Pair<BigInteger, BigInteger> calculateExpected(BigInteger numerator, BigInteger denominator) {
        BigInteger divisor = numerator.gcd(denominator);
        int signNumerator = numerator.signum();
        int signDenominator = denominator.signum();

        // The numerator contains the sign of the rational number
        if (signNumerator == -1 && signDenominator == -1 || signNumerator == 1 && signDenominator == -1) {
            return new Pair<>(numerator.negate().divide(divisor), denominator.negate().divide(divisor));
        } else {
            return new Pair<>(numerator.divide(divisor), denominator.divide(divisor));
        }
    }
}
