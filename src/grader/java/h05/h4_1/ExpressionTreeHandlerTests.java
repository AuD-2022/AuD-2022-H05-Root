package h05.h4_1;

import h05.provider.TokenProvider;
import h05.tree.ExpressionTreeHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestForSubmission("h05")
public class ExpressionTreeHandlerTests {

    private static final Function<List<String>, String> JOINER_FUNCTION = tokenList -> {
        StringBuilder stringBuilder = new StringBuilder();
        tokenList.forEach(token -> stringBuilder.append(token.matches("^[+\\-*/)]$") ? token : " " + token));
        return stringBuilder.toString().trim();
    };

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.SimpleInteger.class)
    @ArgumentsSource(TokenProvider.SimpleRational.class)
    @ArgumentsSource(TokenProvider.SimpleReal.class)
    public void testBuildRecursivelySimple(List<String> tokens) {
        assertEquals(JOINER_FUNCTION.apply(tokens), ExpressionTreeHandler.buildRecursively(tokens.iterator()).toString(),
            "tree created by [[[buildRecursively(Iterator)]]] did not equal expected one");
    }

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.ComplexInteger.class)
    @ArgumentsSource(TokenProvider.ComplexRational.class)
    @ArgumentsSource(TokenProvider.ComplexReal.class)
    public void testBuildRecursivelyComplex(List<String> tokens) {
        assertEquals(JOINER_FUNCTION.apply(tokens), ExpressionTreeHandler.buildRecursively(tokens.iterator()).toString(),
            "tree created by [[[buildRecursively(Iterator)]]] did not equal expected one");
    }

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.Invalid.class)
    public void testBuildRecursivelyExceptions(Class<? extends Exception> exceptionClass, List<String> tokens) {
        assertThrows(exceptionClass, () -> ExpressionTreeHandler.buildRecursively(tokens.iterator()),
            "[[[buildRecursively(Iterator)]]] did not throw the correct exception");
    }

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.Invalid.class)
    public void testBuildIterativelyExceptions(Class<? extends Exception> exceptionClass, List<String> tokens) {
        assertThrows(exceptionClass, () -> ExpressionTreeHandler.buildIteratively(tokens.iterator()),
            "[[[buildIteratively(Iterator)]]] did not throw the correct exception");
    }

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.SimpleInteger.class)
    @ArgumentsSource(TokenProvider.SimpleRational.class)
    @ArgumentsSource(TokenProvider.SimpleReal.class)
    public void testBuildIterativelySimple(List<String> tokens) {
        assertEquals(JOINER_FUNCTION.apply(tokens), ExpressionTreeHandler.buildIteratively(tokens.iterator()).toString(),
            "tree created by [[[buildIteratively(Iterator)]]] did not equal expected one");
    }

    @ParameterizedTest
    @ArgumentsSource(TokenProvider.ComplexInteger.class)
    @ArgumentsSource(TokenProvider.ComplexRational.class)
    @ArgumentsSource(TokenProvider.ComplexReal.class)
    public void testBuildIterativelyComplex(List<String> tokens) {
        assertEquals(JOINER_FUNCTION.apply(tokens), ExpressionTreeHandler.buildIteratively(tokens.iterator()).toString(),
            "tree created by [[[buildIteratively(Iterator)]]] did not equal expected one");
    }
}
