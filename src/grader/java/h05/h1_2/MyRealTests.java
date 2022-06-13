package h05.h1_2;

import h05.math.MyReal;
import h05.math.Rational;
import h05.provider.BigDecimalProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class MyRealTests {

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testToRational(BigDecimal value) {
        MyReal myReal = new MyReal(value);
        BigInteger numerator = value.multiply(BigDecimal.TEN.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE).pow(MyReal.SCALE))
            .toBigInteger();
        BigInteger denominator = BigInteger.TEN.pow(MyReal.SCALE);
        BigInteger divisor = numerator.gcd(denominator);
        Rational rational = new Rational(numerator, denominator);
        Rational actualRational = myReal.toRational();

        assertEquals(numerator.divide(divisor), actualRational.getNumerator(), "Numerator does not have correct value");
        assertEquals(denominator.divide(divisor), actualRational.getDenominator(), "Denominator does not have correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testToReal(BigDecimal value) {
        MyReal myReal = new MyReal(value);

        assertEquals(value, myReal.toReal(), "[[[toReal()]]] did not return the expected value");
    }
}
