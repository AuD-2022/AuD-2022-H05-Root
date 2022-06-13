package h05.h3_1;

import h05.math.MyNumber;
import h05.math.MyReal;
import h05.provider.BigDecimalProvider;
import h05.tree.ArithmeticExpressionNode;
import h05.tree.LiteralExpressionNode;
import h05.utils.ReflectionUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestForSubmission("h05")
public class LiteralExpressionNodeTests {

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testEvaluate(BigDecimal value) {
        MyNumber myNumber = new MyReal(value);
        LiteralExpressionNode node = new LiteralExpressionNode(myNumber);

        assertEquals(myNumber, node.evaluate(Collections.emptyMap()),
            "[[[evaluate(Map)]]] did not return the value it was instantiated with");
    }

    @ParameterizedTest
    @ArgumentsSource(BigDecimalProvider.class)
    public void testClone(BigDecimal value) {
        MyNumber myNumber = new MyReal(value);
        LiteralExpressionNode node = new LiteralExpressionNode(myNumber);
        ArithmeticExpressionNode clonedNode = node.clone();

        assertTrue(clonedNode instanceof LiteralExpressionNode,
            "Object returned by [[[clone()]]] is not a LiteralExpressionNode");
        assertEquals(myNumber,
            ReflectionUtils.getFieldValue(LiteralExpressionNode.class, "value", (LiteralExpressionNode) clonedNode),
            "Value in node returned by [[[clone()]]] does not equal expected value");
    }
}
