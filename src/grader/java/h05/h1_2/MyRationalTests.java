package h05.h1_2;

import h05.math.*;
import h05.provider.BigDecimalProvider;
import h05.utils.RationalMock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class MyRationalTests {

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testToRational(BigDecimal value) {
        BigInteger numerator = new BigInteger(value.toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.abs().shiftRight(8).add(BigInteger.ONE);
        BigInteger divisor = numerator.gcd(denominator);
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));
        Rational rational = myRational.toRational();

        assertEquals(numerator.divide(divisor), rational.getNumerator(), "Numerator does not have correct value");
        assertEquals(denominator.divide(divisor), rational.getDenominator(), "Denominator does not have correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testToReal(BigDecimal value) {
        BigInteger numerator = new BigInteger(value.toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8);
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));
        BigDecimal actual = myRational.toReal();
        BigDecimal numeratorDecimal = new BigDecimal(numerator).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
        BigDecimal denominatorDecimal = new BigDecimal(denominator).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);

        assertEquals(numeratorDecimal.divide(denominatorDecimal, MyReal.ROUNDING_MODE), actual,
            "[[[toReal()]]] did not return the expected value");
    }
}
