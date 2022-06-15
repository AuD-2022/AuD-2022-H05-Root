package h05.h4_1;

import h05.provider.TokenProvider;
import h05.tree.ExpressionTreeHandler;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@TestForSubmission("h05")
public class ExpressionTreeHandlerTests {

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
}
