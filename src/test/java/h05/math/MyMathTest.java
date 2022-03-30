package h05.math;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class MyMathTest {

    @ParameterizedTest
    @CsvSource({
        " 100,  2",
        "  10,  1",
        "   1,  0",
        " 0.1, -1",
        "0.01, -2",

        "0.5, -0.301029995663981",
        "2.0,  0.301029995663981",
        "2.5,  0.397940008672038",
        "3.0,  0.477121254719662",
        "3.5,  0.544068044350276",
        "4.0,  0.602059991327962",
        "5.0,  0.698970004336019",
        "2.0,  0.301029995663981",
        "2.5,  0.397940008672038",
        "3.0,  0.477121254719662",
        "3.5,  0.544068044350276",
        "4.0,  0.602059991327962",
        "4.5,  0.653212513775344",

        "019283798127380712803.90123091823091283, 19.2851925764189604126350958756341158377",
        "8486567864889416521321534549846.123145648743513215488512315487513245478, 30.928732088511983863199535832868892611691489281991429502801134760",
        "0.0000000000008486567864889416521321534549846123145648743513215488512315487513245478, -12.07126791148801613680046416713110738830851071800857049719886523",
        "0.000000000000000000000000000000000008486567864889416521321534549846123145648743513215488512315487513245478, -35.07126791148801613680046416713110738830851071800857049719886523",
    })
    public void testLog10(String x, String y) {

        var value = MyMath.log10(new BigDecimal(x));
        assertIsCloseTo(new BigDecimal(y), value);
    }

    @Test
    public void testThat_logOfInvalidThrows() {
        assertThrows(ArithmeticException.class, () ->
            MyMath.log10(BigDecimal.valueOf(-1)));
        assertThrows(ArithmeticException.class, () ->
            MyMath.log10(BigDecimal.ZERO));
    }

    private static void assertIsCloseTo(BigDecimal expected, BigDecimal actual) {
        var d = expected.subtract(actual).abs();
        assertTrue(d.compareTo(BigDecimal.valueOf(1.0E-15)) < 0,
            String.format("<%s> is too far away from <%s>", actual, expected));
    }
}
