package h05.h1_2;

import h05.math.MyReal;
import h05.math.Rational;
import h05.provider.H1_2Provider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class MyRealTests {

    @ParameterizedTest
    @ArgumentsSource(H1_2Provider.class)
    public void testToRational(BigDecimal value) {
        MyReal myReal = new MyReal(value);
        BigInteger numerator = value.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE)
            .multiply(BigDecimal.TEN.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE).pow(MyReal.SCALE))
            .toBigInteger();
        BigInteger denominator = BigInteger.TEN.pow(MyReal.SCALE);
        BigInteger divisor = numerator.gcd(denominator);
        Rational rational = new Rational(numerator, denominator);
        Rational actualRational = myReal.toRational();

        assertEquals(numerator.divide(divisor), actualRational.getNumerator(), "Numerator does not have correct value");
        assertEquals(denominator.divide(divisor), actualRational.getDenominator(), "Denominator does not have correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(H1_2Provider.class)
    public void testToReal(BigDecimal value) {
        MyReal myReal = new MyReal(value);

        assertEquals(value.setScale(MyReal.SCALE, MyReal.ROUNDING_MODE), myReal.toReal(),
            "[[[toReal()]]] did not return the expected value");
    }
}
