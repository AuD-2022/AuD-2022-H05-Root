package h05.h2_2;

import h05.math.MyInteger;
import h05.math.MyReal;
import h05.provider.DecimalProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class MyIntegerTests {

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testSqrt(BigDecimal value) {
        MyInteger myInteger = new MyInteger(value.abs().toBigInteger());

        assertEquals(ArithmeticOperations.sqrt(myInteger), myInteger.sqrt(),
            "Result differs from expected value for square root of " + myInteger);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testExp(BigDecimal value) {
        MyInteger myInteger = new MyInteger(value.toBigInteger());

        assertEquals(ArithmeticOperations.exp(myInteger), myInteger.exp(),
            "Result differs from expected value for e^x with x = " + myInteger);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testExpt(BigDecimal value, BigDecimal exponent) {
        MyInteger myInteger = new MyInteger(value.abs().toBigInteger());
        MyReal myExponent = new MyReal(exponent.abs());

        assertEquals(ArithmeticOperations.expt(myInteger, myExponent), myInteger.expt(myExponent));
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testLn(BigDecimal value) {
        MyInteger myInteger = new MyInteger(value.abs().toBigInteger());

        assertEquals(ArithmeticOperations.ln(myInteger), myInteger.ln());
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testLog(BigDecimal value, BigDecimal base) {
        MyInteger myInteger = new MyInteger(value.abs().toBigInteger());
        MyReal myBase = new MyReal(base.abs());

        assertEquals(ArithmeticOperations.log(myInteger, myBase), myInteger.log(myBase));
    }
}
