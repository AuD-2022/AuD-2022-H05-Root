package h05.tree;

import h05.math.*;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeHandlerTest {

    private ArithmeticExpressionNode root = null;

    private MyNumber result = null;

    @ParameterizedTest
    @CsvFileSource(resources = "/ExpressionTreeHandlerTest/evaluate_expressions.csv", numLinesToSkip = 1)
    void testThat_buildIterativelyAndEvaluateWorks(String expected, String expression) {
        givenIterativelyBuiltTree(expression);
        whenEvaluatingWithNoIdentifiers();
        thenItShouldBe(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ExpressionTreeHandlerTest/evaluate_expressions.csv", numLinesToSkip = 1)
    void testThat_buildRecursivelyAndEvaluateWorks(String expected, String expression) {
        givenRecursivelyBuiltTree(expression);
        whenEvaluatingWithNoIdentifiers();
        thenItShouldBe(expected);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/ExpressionTreeHandlerTest/bad_expressions.csv", numLinesToSkip = 1)
    void testThat_buildRecursivelyThrowsOnBadExpressions(String exception, String message, String expression) throws ClassNotFoundException {
        var tokens = tokenize(expression);
        var e = assertThrows(getException(exception), () ->
            ExpressionTreeHandler.buildRecursively(tokens));
        assertEquals(message, e.getMessage());
    }

    private Class<? extends Throwable> getException(String exception) throws ClassNotFoundException {
        return Class
            .forName("h05.exception." + exception)
            .asSubclass(Throwable.class);
    }

    private void givenRecursivelyBuiltTree(String expression) {
        var tokens = tokenize(expression);
        root = ExpressionTreeHandler.buildRecursively(tokens);
    }

    private void givenIterativelyBuiltTree(String expression) {
        var tokens = tokenize(expression);
        root = ExpressionTreeHandler.buildIteratively(tokens);
    }

    private void whenEvaluatingWithNoIdentifiers() {
        result = root.evaluate(Map.of());
    }

    private void thenItShouldBe(String expected) {
        var split = expected.split("\\s+");

        switch (split[0]) {
            case "int" -> {
                thenItShouldBeTheInteger(split[1]);
                return;
            }
            case "ratio" -> {
                thenItShouldBeTheRatio(split[1], split[2]);
                return;
            }
            case "real" -> {
                thenItShouldBeTheReal(split[1]);
                return;
            }
        }

        throw new IllegalArgumentException(expected);
    }

    private void thenItShouldBeTheInteger(String expected) {
        assertInstanceOf(MyInteger.class, result);
        assertEquals(new BigInteger(expected), result.toInteger());
    }

    private void thenItShouldBeTheRatio(String top, String bottom) {
        assertInstanceOf(MyRational.class, result);
        var expected = new Rational(new BigInteger(top), new BigInteger(bottom));
        assertEquals(expected, result.toRational());
    }

    private void thenItShouldBeTheReal(String expected) {
        assertInstanceOf(MyReal.class, result);
        MyMathTest.assertIsCloseTo(getExpectedReal(expected), result.toReal());
    }

    private BigDecimal getExpectedReal(String expected) {
        return switch (expected) {
            case "pi" -> new BigDecimal(Math.PI);
            case "e" -> new BigDecimal(Math.E);
            default -> new BigDecimal(expected);
        };
    }

    @NotNull
    private Iterator<String> tokenize(String expression) {
        if (expression.isBlank()) {
            return Collections.emptyIterator();
        }

        var tokens = expression.split("\\s+");
        return List.of(tokens).iterator();
    }
}
