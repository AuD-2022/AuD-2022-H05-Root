package h05.h1_2;

import h05.math.MyInteger;
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
public class MyIntegerTests {

    @ParameterizedTest
    @ArgumentsSource(H1_2Provider.class)
    public void testToRational(BigDecimal value) {
        BigInteger bigInteger = new BigInteger(value.toString().replaceAll("\\..*", ""));
        MyInteger myInteger = new MyInteger(bigInteger);
        Rational rational = myInteger.toRational();

        assertEquals(bigInteger, rational.getNumerator(), "Numerator does not have correct value");
        assertEquals(BigInteger.ONE, rational.getDenominator(), "Denominator does not have correct value");
    }

    @ParameterizedTest
    @ArgumentsSource(H1_2Provider.class)
    public void testToReal(BigDecimal value) {
        BigInteger bigInteger = new BigInteger(value.toString().replaceAll("\\..*", ""));
        MyInteger myInteger = new MyInteger(bigInteger);
        BigDecimal actual = myInteger.toReal();

        assertEquals(new BigDecimal(bigInteger).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE), actual,
            "[[[toReal()]]] did not return the expected value");
    }
}
