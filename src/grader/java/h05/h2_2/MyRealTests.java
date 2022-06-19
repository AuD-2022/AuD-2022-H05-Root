package h05.h2_2;

import h05.math.MyReal;
import h05.provider.DecimalProvider;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestForSubmission("h05")
public class MyRealTests {

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testSqrt(BigDecimal value) {
        MyReal myReal = new MyReal(value.abs());

        assertEquals(ArithmeticOperations.sqrt(myReal), myReal.sqrt(),
            "Result differs from expected value for square root of " + myReal);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testExp(BigDecimal value) {
        MyReal myReal = new MyReal(value.abs());

        assertEquals(ArithmeticOperations.exp(myReal), myReal.exp(),
            "Result differs from expected value for e^x with x = " + myReal);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testExpt(BigDecimal value, BigDecimal exponent) {
        MyReal myReal = new MyReal(value.abs());
        MyReal myExponent = new MyReal(exponent.abs());

        assertEquals(ArithmeticOperations.expt(myReal, myExponent), myReal.expt(myExponent));
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testLn(BigDecimal value) {
        MyReal myReal = new MyReal(value.abs());

        assertEquals(ArithmeticOperations.ln(myReal), myReal.ln());
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testLog(BigDecimal value, BigDecimal base) {
        MyReal myReal = new MyReal(value.abs());
        MyReal myBase = new MyReal(base.abs());

        assertEquals(ArithmeticOperations.log(myReal, myBase), myReal.log(myBase));
    }
}
