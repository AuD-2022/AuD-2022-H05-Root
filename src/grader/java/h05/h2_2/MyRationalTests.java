package h05.h2_2;

import h05.math.MyRational;
import h05.math.MyReal;
import h05.provider.DecimalProvider;
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
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testSqrt(BigDecimal value) {
        BigInteger numerator = new BigInteger(value.abs().toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8).add(BigInteger.ONE).abs();
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));

        assertEquals(ArithmeticOperations.sqrt(myRational), myRational.sqrt(),
            "Result differs from expected value for square root of " + myRational);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testExp(BigDecimal value) {
        BigInteger numerator = new BigInteger(value.abs().toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8).add(BigInteger.ONE).abs();
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));

        assertEquals(ArithmeticOperations.exp(myRational), myRational.exp(),
            "Result differs from expected value for e^x with x = " + myRational);
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testExpt(BigDecimal value, BigDecimal exponent) {
        BigInteger numerator = new BigInteger(value.abs().toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8).add(BigInteger.ONE).abs();
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));
        MyReal myExponent = new MyReal(exponent);

        assertEquals(ArithmeticOperations.expt(myRational, myExponent), myRational.expt(myExponent));
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Single.class)
    public void testLn(BigDecimal value) {
        BigInteger numerator = new BigInteger(value.abs().toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8).add(BigInteger.ONE).abs();
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));

        assertEquals(ArithmeticOperations.ln(myRational), myRational.ln());
    }

    @ParameterizedTest
    @ArgumentsSource(DecimalProvider.Double.class)
    public void testLog(BigDecimal value, BigDecimal base) {
        BigInteger numerator = new BigInteger(value.abs().toString().replaceAll("\\..*", ""));
        BigInteger denominator = numerator.shiftRight(8).add(BigInteger.ONE).abs();
        MyRational myRational = new MyRational(RationalMock.getInstance(numerator, denominator));
        MyReal myBase = new MyReal(base.abs());

        assertEquals(ArithmeticOperations.log(myRational, myBase), myRational.log(myBase));
    }
}
