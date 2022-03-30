package h05.math;

import java.math.BigDecimal;

import static h05.math.MyReal.ROUNDING_MODE;

public class MyMath {

    private static final BigDecimal LOG_BASE_10_OF_E = new BigDecimal("0.4342944819032518276511289189166050822943970058036665661144537831");

    private MyMath() {}

    public static BigDecimal ln(BigDecimal x) {
        return MyMath.log10(x).divide(MyMath.LOG_BASE_10_OF_E, ROUNDING_MODE);
    }

    public static BigDecimal log10(BigDecimal x) {
        if (x.signum() < 1) {
            throw new ArithmeticException("Only for positive numbers");
        }

        var log10 = BigDecimal.ZERO;

        while (x.compareTo(BigDecimal.TEN) > 0) {
            log10 = log10.add(BigDecimal.ONE);
            x = x.divide(BigDecimal.TEN, ROUNDING_MODE);
        }

        while (x.compareTo(BigDecimal.ONE) < 0) {
            log10 = log10.subtract(BigDecimal.ONE);
            x = x.multiply(BigDecimal.TEN);
        }

        var rest = BigDecimal.valueOf(Math.log10(x.doubleValue()));
        return log10.add(rest);
    }
}
