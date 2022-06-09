package h05;

import h05.math.MyInteger;
import h05.math.MyRational;
import h05.math.MyReal;
import h05.math.Rational;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicTests {

    private static final BigDecimal EPSILON = new BigDecimal("0.0000000000001");

    @Nested
    class RationalTest {

        @Test
        void testConstructor() {
            var minusTwoThirds = new Rational(
                BigInteger.valueOf(10),
                BigInteger.valueOf(-15));
            assertEquals(BigInteger.valueOf(-2), minusTwoThirds.getNumerator());
            assertEquals(BigInteger.valueOf(3), minusTwoThirds.getDenominator());
        }
    }

    @Nested
    class MyRationalTest {

        private static final MyRational minusTwoThirds = ratio(-2, 3);
        private static final MyRational four = ratio(4, 1);

        @Test
        void testToRational() {
            var rational = minusTwoThirds.toRational();
            assertEquals(BigInteger.valueOf(-2), rational.getNumerator());
            assertEquals(BigInteger.valueOf(3), rational.getDenominator());
        }

        @Test
        void testToReal() {
            assertAlmostEquals(
                new BigDecimal("-0.666666666666667"),
                minusTwoThirds.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                ratio(2, 3),
                minusTwoThirds.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                ratio(5, 3),
                MyRational.ONE.minus(minusTwoThirds));
        }

        @Test
        void testDivide() {
            assertEquals(
                ratio(-3, 2).toRational(),
                minusTwoThirds.divide().toRational());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                ratio(-8, 9),
                minusTwoThirds.divide(ratio(3, 4)));
        }

        @Test
        void testSqrt() {
            assertEquals(
                integer(2),
                four.sqrt());
        }

        @Test
        void testExpt() {
            assertEquals(
                integer(2),
                four.expt(ratio(1, 2)));
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                real("54.59815003314423").toReal(),
                four.exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                real("1.3862943611198906").toReal(),
                four.ln().toReal());
        }

        @Test
        void testLog() {
            assertAlmostEquals(
                integer(-2).toReal(),
                four.log(ratio(1, 2)).toReal());
        }
    }


    private static MyInteger integer(int value) {
        return new MyInteger(BigInteger.valueOf(value));
    }

    private static MyRational ratio(int numerator, int denominator) {
        var ratio = new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
        return new MyRational(ratio);
    }

    private static MyReal real(String value) {
        return new MyReal(new BigDecimal(value));
    }

    private static void assertAlmostEquals(BigDecimal expected, BigDecimal actual) {
        var delta = expected.subtract(actual).abs();

        if (EPSILON.compareTo(delta) < 0) {
            throw new AssertionFailedError("Given numbers are too far apart", expected, actual);
        }
    }
}
