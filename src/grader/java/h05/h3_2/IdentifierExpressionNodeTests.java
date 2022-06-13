package h05.h3_2;

import h05.exception.IllegalIdentifierExceptions;
import h05.exception.UndefinedIdentifierException;
import h05.math.MyNumber;
import h05.provider.IdentifierExpressionNodeProvider;
import h05.tree.ArithmeticExpressionNode;
import h05.tree.IdentifierExpressionNode;
import h05.utils.ReflectionUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h05")
public class IdentifierExpressionNodeTests {

    @ParameterizedTest
    @ArgumentsSource(IdentifierExpressionNodeProvider.Default.class)
    public void testConstructor(String identifier) {
        IdentifierExpressionNode instance = assertDoesNotThrow(() -> new IdentifierExpressionNode(identifier),
            "Constructor threw an exception for valid input");
        assertEquals(identifier,
            ReflectionUtils.getFieldValue(IdentifierExpressionNode.class, "value", instance),
            "Constructor did not assign passed argument to field [[[value]]]");
    }

    @ParameterizedTest
    @ArgumentsSource(IdentifierExpressionNodeProvider.ConstructorInvalid.class)
    public void testConstructorExceptions(IdentifierExpressionNodeProvider.ConstructorInvalid.IdentifierType identifierType,
                                          String identifier) {
        Class<? extends Exception> exceptionClass = switch (identifierType) {
            case NULL -> NullPointerException.class;
            case ILLEGAL_ARGUMENT -> IllegalArgumentException.class;
            case ILLEGAL_IDENTIFIER -> IllegalIdentifierExceptions.class;
        };

        assertThrows(exceptionClass, () -> new IdentifierExpressionNode(identifier));
    }

    @ParameterizedTest
    @ArgumentsSource(IdentifierExpressionNodeProvider.EvaluateValid.class)
    public void testEvaluate(String identifier, Map<String, MyNumber> identifiers) {
        assertEquals(identifiers.get(identifier), new IdentifierExpressionNode(identifier).evaluate(identifiers),
            "[[[evaluate(Map)]]] did not return expected value");
    }

    @ParameterizedTest
    @ArgumentsSource(IdentifierExpressionNodeProvider.EvaluateInvalid.class)
    public void testEvaluateExceptions(IdentifierExpressionNodeProvider.EvaluateInvalid.IdentifierType identifierType,
                                       String identifier,
                                       Map<String, MyNumber> identifiers) {
        Class<? extends Exception> exceptionClass = switch (identifierType) {
            case ILLEGAL_IDENTIFIER -> IllegalIdentifierExceptions.class;
            case UNDEFINED_IDENTIFIER -> UndefinedIdentifierException.class;
        };

        assertThrows(exceptionClass, () -> new IdentifierExpressionNode(identifier).evaluate(identifiers));
    }

    @ParameterizedTest
    @ArgumentsSource(IdentifierExpressionNodeProvider.Default.class)
    public void testClone(String identifier) {
        IdentifierExpressionNode instance = new IdentifierExpressionNode(identifier);
        ArithmeticExpressionNode clonedNode = instance.clone();

        assertTrue(clonedNode instanceof IdentifierExpressionNode,
            "Cloned node is not an instance of IdentifierExpressionNode");
        assertEquals(identifier,
            ReflectionUtils.getFieldValue(IdentifierExpressionNode.class, "value", (IdentifierExpressionNode) clonedNode),
            "Cloned node does not have the same value in field [[[value]]] as the original node");
    }
}
