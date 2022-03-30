package h05.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MyMath {

    private MyMath() {}

    public static BigDecimal log10(BigDecimal y) {
        if (y.signum() < 1) {
            throw new ArithmeticException("Only for positive numbers");
        }

        var log10 = BigDecimal.ZERO;

        while (y.compareTo(BigDecimal.TEN) > 0) {
            log10 = log10.add(BigDecimal.ONE);
            y = y.divide(BigDecimal.TEN, RoundingMode.HALF_DOWN);
        }

        while (y.compareTo(BigDecimal.ONE) < 0) {
            log10 = log10.subtract(BigDecimal.ONE);
            y = y.multiply(BigDecimal.TEN);
        }

        var rest = BigDecimal.valueOf(Math.log10(y.doubleValue()));
        return log10.add(rest);
    }
}
