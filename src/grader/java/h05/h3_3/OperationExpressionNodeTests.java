package h05.h3_3;

import h05.exception.WrongNumberOfOperandsException;
import h05.math.MyInteger;
import h05.math.MyNumber;
import h05.provider.OperationExpressionNodeProvider;
import h05.tree.ArithmeticExpressionNode;
import h05.tree.ListItem;
import h05.tree.OperationExpressionNode;
import h05.tree.Operator;
import h05.utils.ListItemUtils;
import h05.utils.ReflectionUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@TestForSubmission("h05")
public class OperationExpressionNodeTests {

    @ParameterizedTest
    @ArgumentsSource(OperationExpressionNodeProvider.Default.class)
    public void testConstructor(Operator operator, ListItem<ArithmeticExpressionNode> operands) {
        OperationExpressionNode instance = assertDoesNotThrow(() -> new OperationExpressionNode(operator, operands));

        assertSame(operator,
            ReflectionUtils.getFieldValue(OperationExpressionNode.class, "operator", instance),
            "Constructor did not assign first argument to field [[[operator]]]");
        assertSame(operands,
            ReflectionUtils.getFieldValue(OperationExpressionNode.class, "operands", instance),
            "Constructor did not assign second argument to field [[[operands]]]");
    }

    @ParameterizedTest
    @ArgumentsSource(OperationExpressionNodeProvider.ConstructorInvalid.class)
    public void testConstructorException(Operator operator, ListItem<ArithmeticExpressionNode> operands) {
        assertThrows(WrongNumberOfOperandsException.class, () -> new OperationExpressionNode(operator, operands),
            "Constructor did not throw an exception for an invalid operator-operands combination");
    }

    @ParameterizedTest
    @EnumSource(value = Operator.class, names = {"ADD", "MUL"})
    public void testEvaluateNeutralElement(Operator operator) {
        MyNumber myNumber = switch (operator) {
            case ADD -> MyInteger.ZERO;
            case MUL -> MyInteger.ONE;
            default -> null;
        };

        assertEquals(myNumber, new OperationExpressionNode(operator, null).evaluate(Collections.emptyMap()),
            "Expected [[[evaluate(Map)]]] to return the neutral element for operation " + operator);
    }

    @ParameterizedTest
    @ArgumentsSource(OperationExpressionNodeProvider.Default.class)
    public void testClone(Operator operator, ListItem<ArithmeticExpressionNode> operands) {
        OperationExpressionNode instance = new OperationExpressionNode(operator, operands);
        ArithmeticExpressionNode clonedNode = instance.clone();

        assertTrue(clonedNode instanceof OperationExpressionNode,
            "Object returned by [[[clone()]]] is not an instance of OperationExpressionNode");
        assertEquals(operator,
            ReflectionUtils.getFieldValue(OperationExpressionNode.class, "operator", (OperationExpressionNode) clonedNode),
            "Object returned by [[[clone()]]] does not have same value for field [[[operator]]] as original node");
        assertTrue(ListItemUtils.deepEquals(operands,
                ReflectionUtils.getFieldValue(OperationExpressionNode.class, "operands", (OperationExpressionNode) clonedNode)),
            "Object returned by [[[clone()]]] does not have same value for field [[[operands]]] as original node");
    }
}
