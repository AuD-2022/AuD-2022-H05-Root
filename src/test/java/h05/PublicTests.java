package h05;

import h05.math.*;
import h05.tree.Identifier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    private static final BigDecimal EPSILON = real("0.0000000000001");

    @Nested
    class RationalTest {

        @Test
        void testConstructor() {
            var minusTwoThirds = ratio(10, -15);
            assertEquals(BigInteger.valueOf(-2), minusTwoThirds.getNumerator());
            assertEquals(BigInteger.valueOf(3), minusTwoThirds.getDenominator());
        }
    }

    @Nested
    class MyIntegerTest {

        private static final MyInteger sixtyNine = mint(69);

        @Test
        void testToRational() {
            assertEquals(
                mratio(69, 1).toRational(),
                sixtyNine.toRational());
        }

        @Test
        void testToReal() {
            assertEquals(
                real("69"),
                sixtyNine.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mint(-69),
                sixtyNine.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mreal("55.63"),
                sixtyNine.minus(mreal("13.37")));
        }

        @Test
        void testDivide() {
            assertEquals(
                mratio(1, 69),
                sixtyNine.divide());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mint(23),
                sixtyNine.divide(mint(3)));
        }

        @Test
        void testSqrt() {
            assertEquals(
                mint(4),
                mint(16).sqrt());
        }

        @Test
        void testExpt() {
            assertEquals(
                mint(4761),
                sixtyNine.expt(mint(2)));
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                real("54.59815003314423"),
                mint(4).exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                real("4.23410650459726"),
                sixtyNine.ln().toReal());
        }

        @Test
        void testLog() {
            assertEquals(
                mint(2),
                mint(4761).log(sixtyNine));
        }
    }

    @Nested
    class MyRationalTest {

        private static final MyRational minusTwoThirds = mratio(-2, 3);
        private static final MyRational threeHalf = mratio(3, 2);

        @Test
        void testToRational() {
            var rational = minusTwoThirds.toRational();
            assertEquals(BigInteger.valueOf(-2), rational.getNumerator());
            assertEquals(BigInteger.valueOf(3), rational.getDenominator());
        }

        @Test
        void testToReal() {
            assertAlmostEquals(
                real("-0.666666666666667"),
                minusTwoThirds.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mratio(2, 3),
                minusTwoThirds.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mratio(5, 3),
                MyRational.ONE.minus(minusTwoThirds));
        }

        @Test
        void testDivide() {
            assertEquals(
                mratio(-3, 2).toRational(),
                minusTwoThirds.divide().toRational());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mratio(-8, 9),
                minusTwoThirds.divide(mratio(3, 4)));
        }

        @Test
        void testSqrt() {
            assertAlmostEquals(
                real("1.224744871391589"),
                threeHalf.sqrt().toReal());
        }

        @Test
        void testExpt() {
            assertEquals(
                mreal("2.25"),
                threeHalf.expt(mint(2)));
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                real("4.4816890703380645"),
                mratio(3, 2).exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                real("0.4054651081081644"),
                threeHalf.ln().toReal());
        }

        @Test
        void testLog() {
            assertEquals(
                mint(-2),
                mratio(1, 25).log(mint(5)));
        }
    }


    @Nested
    class MyRealTest {

        private final MyNumber pi = Identifier.PI.getValue();

        @Test
        void testToRational() {
            assertEquals(
                ratio(3141592653589793L, 1000000000000000L),
                pi.toRational());
        }

        @Test
        void testToReal() {
            assertEquals(
                real("3.14159265358979323846"),
                pi.toReal());
        }

        @Test
        void testMinus() {
            assertEquals(
                mreal("-3.14159265358979323846"),
                pi.minus());
        }

        @Test
        void testMinusWithOperand() {
            assertEquals(
                mreal("2.14159265358979323846"),
                pi.minus(MyReal.ONE));
        }

        @Test
        void testDivide() {
            assertEquals(
                mreal("4"),
                mreal("0.25").divide());
        }

        @Test
        void testDivideWithOperand() {
            assertEquals(
                mint(8),
                mreal("2").divide(mreal("0.25")));
        }

        @Test
        void testSqrt() {
            assertEquals(
                mreal("0.5"),
                mreal("0.25").sqrt());
        }

        @Test
        void testExpt() {
            assertAlmostEquals(
                mreal("0.8325532074018731").toReal(),
                mreal("0.4").expt(mreal("0.2")).toReal());
        }

        @Test
        void testExp() {
            assertAlmostEquals(
                mreal("23.140692632779267").toReal(),
                pi.exp().toReal());
        }

        @Test
        void testLn() {
            assertAlmostEquals(
                pi.toReal(),
                mreal("23.140692632779267").ln().toReal());
        }

        @Test
        void testLog() {
            assertAlmostEquals(
                mreal("-4").toReal(),
                mreal("16").log(mreal("0.5")).toReal());
        }
    }

    private static MyInteger mint(long value) {
        return new MyInteger(integer(value));
    }

    private static BigInteger integer(long value) {
        return BigInteger.valueOf(value);
    }

    private static MyRational mratio(long numerator, long denominator) {
        return new MyRational(ratio(numerator, denominator));
    }

    private static Rational ratio(long numerator, long denominator) {
        return new Rational(BigInteger.valueOf(numerator), BigInteger.valueOf(denominator));
    }

    private static MyReal mreal(String value) {
        return new MyReal(real(value));
    }

    private static BigDecimal real(String value) {
        return new BigDecimal(value).setScale(MyReal.SCALE, MyReal.ROUNDING_MODE);
    }

    private static void assertAlmostEquals(BigDecimal expected, BigDecimal actual) {
        var delta = expected.subtract(actual).abs();

        if (EPSILON.compareTo(delta) < 0) {
            assertEquals(expected, actual);
        }
    }
}
