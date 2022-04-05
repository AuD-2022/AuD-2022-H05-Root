package h05.tree;

import h05.math.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeHandlerTest {

    private ArithmeticExpressionNode root = null;

    private MyNumber result = null;

    @ParameterizedTest
    @CsvFileSource(resources = "/ExpressionTreeHandlerTest.csv", numLinesToSkip = 1)
    void testBuildIteratively(String expected, String expression) {
        givenIterativelyBuiltTree(expression);
        whenEvaluatingWithNoIdentifiers();
        itShouldBe(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ExpressionTreeHandlerTest.csv", numLinesToSkip = 1)
    void testBuildRecursively(String expected, String expression) {
        givenRecursivelyBuiltTree(expression);
        whenEvaluatingWithNoIdentifiers();
        itShouldBe(expected);
    }

    private void givenRecursivelyBuiltTree(String expression) {
        var tokens = expression.split("\\s+");
        var iterator = List.of(tokens).iterator();
        root = ExpressionTreeHandler.buildRecursively(iterator);
    }

    private void givenIterativelyBuiltTree(String expression) {
        var split = expression.split("\\s+");
        var tokens = List.of(split).iterator();
        root = ExpressionTreeHandler.buildIteratively(tokens);
    }

    private void whenEvaluatingWithNoIdentifiers() {
        result = root.evaluate(Map.of());
    }

    private void itShouldBe(String expected) {
        var split = expected.split("\\s+");

        switch (split[0]) {
            case "int":
                itShouldBeTheInteger(split[1]);
                return;
            case "ratio":
                itShouldBeTheRatio(split[1], split[2]);
                return;
            case "real":
                itShouldBeTheReal(split[1]);
                return;
        }

        throw new IllegalArgumentException(expected);
    }

    private void itShouldBeTheInteger(String expected) {
        assertTrue(result instanceof MyInteger, "Evaluated result was not a MyInteger");
        assertEquals(new BigInteger(expected), result.toInteger());
    }

    private void itShouldBeTheRatio(String top, String bottom) {
        assertTrue(result instanceof MyRational, "Evaluated result was not a MyRatio");
        var expected = new Rational(new BigInteger(top), new BigInteger(bottom));
        assertEquals(expected, result.toRational());
    }

    private void itShouldBeTheReal(String expected) {
        assertTrue(result instanceof MyReal, "Evaluated result was not a MyReal");
        MyMathTest.assertIsCloseTo(getExpectedReal(expected), result.toReal());
    }

    private BigDecimal getExpectedReal(String expected) {
        switch (expected) {
            case "pi":
                return new BigDecimal(Math.PI);
            case "e":
                return new BigDecimal(Math.E);
        }

        return new BigDecimal(expected);
    }
}
