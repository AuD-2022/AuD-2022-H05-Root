package h05.tree;

import h05.math.MyNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Evaluates an arithmetic expression by replacing the variables (identifiers) of the expression
 * with their values.
 *
 * @author Nhan Huynh
 */
public class ArithmeticExpressionEvaluator {

    /**
     * The arithmetic expression tree to evaluate.
     */
    private ArithmeticExpressionNode root;

    /**
     * The map of variables and their values.
     */
    private final Map<String, MyNumber> identifiers;

    /**
     * Constructs and initializes an arithmetic expression evaluator.
     *
     * @param root        the root of the arithmetic expression tree to evaluate
     * @param identifiers the map of variables and their values
     */
    public ArithmeticExpressionEvaluator(
        ArithmeticExpressionNode root,
        Map<String, MyNumber> identifiers) {
        this.root = root.clone();
        this.identifiers = identifiers;
    }

    /**
     * Evaluates the arithmetic expression tree by replacing the variables (identifiers) of the
     * expression with their values.
     *
     * @return the list of tokens representing  the evaluation
     */
    public List<String> nextStep() {
        List<String> expression = ExpressionTreeHandler.reconstruct(root);
        if (root instanceof LiteralExpressionNode) {
            return expression;
        }
        List<String> newExpression = new ArrayList<>(expression.size());
        List<String> innerExpression = new ArrayList<>();

        for (String token : expression) {
            if (token.equals(ArithmeticExpressionNode.LEFT_BRACKET)) {
                // Found a new inner expression, no evaluation needed
                if (!innerExpression.isEmpty()) {
                    newExpression.addAll(innerExpression);
                    innerExpression.clear();
                }
                innerExpression.add(token);
            } else if (token.equals(ArithmeticExpressionNode.RIGHT_BRACKET)) {
                innerExpression.add(token);
                // Evaluate the inner expression
                ArithmeticExpressionNode node = ExpressionTreeHandler.buildIteratively(
                    innerExpression.iterator()
                );
                MyNumber number = node.evaluate(identifiers);
                LiteralExpressionNode literal = new LiteralExpressionNode(number);
                List<String> literalExpression = ExpressionTreeHandler.reconstruct(literal);
                newExpression.addAll(literalExpression);
            } else if (Operator.SYMBOLS.contains(token) || MyNumber.isNumber(token)) {
                innerExpression.add(token);
            } else if (identifiers.containsKey(token)) {
                innerExpression.add(identifiers.get(token).toString());
            } else {
                // Identifier not found
                innerExpression.add("<unknown!>");
            }
        }
        return newExpression;
    }
}
