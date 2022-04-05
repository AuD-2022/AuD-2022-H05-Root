package h05.tree;

import h05.math.MyNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Evaluates an arithmetic expression by replacing the variables (identifiers) of the expression with their values.
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
     * Returns the root of the arithmetic expression tree to evaluate.
     *
     * @return the root of the arithmetic expression tree to evaluate
     */
    public ArithmeticExpressionNode getRoot() {
        return root;
    }

    /**
     * Returns the map of variables and their values.
     *
     * @return the map of variables and their values
     */
    public Map<String, MyNumber> getIdentifiers() {
        return identifiers;
    }

    /**
     * Evaluates the arithmetic expression tree by replacing the variables (identifiers) of the expression with their values and
     * evaluates the most inner expressions.
     *
     * @return the list of tokens representing  the evaluation
     */
    public List<String> nextStep() {
        List<String> expression = ExpressionTreeHandler.reconstruct(root);

        // No operation needed on a literal expression
        if (root instanceof LiteralExpressionNode) {
            return expression;
        }

        // Building the "evaluated" expression
        List<String> newExpression = new ArrayList<>(expression.size());

        // Most inner expressions are evaluated first
        List<String> innerExpression = new ArrayList<>();

        for (String token : expression) {
            boolean isEmpty = innerExpression.isEmpty();
            boolean isEnd = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);

            if (token.equals(ArithmeticExpressionNode.LEFT_BRACKET)) {
                // Found a new inner expression, no evaluation needed
                if (!isEmpty) {
                    newExpression.addAll(innerExpression);
                    innerExpression.clear();
                }
                innerExpression.add(token);
            } else if (isEnd && isEmpty) {
                // End of the expression, add closing bracket to the new expression
                newExpression.add(token);
            } else if (isEnd) {
                innerExpression.add(token);
                // Evaluate the inner expression
                ArithmeticExpressionNode node = ExpressionTreeHandler.buildRecursively(innerExpression.iterator());
                assert node != null;
                MyNumber number = node.evaluate(identifiers);
                LiteralExpressionNode newNode = new LiteralExpressionNode(number);

                // Replace the inner expression with the evaluated expression
                List<String> nodeExpression = ExpressionTreeHandler.reconstruct(newNode);
                newExpression.addAll(nodeExpression);
                innerExpression.clear();
            } else if (Operator.SYMBOLS.contains(token) || MyNumber.isNumber(token)) {
                // If we did not read an inner expression, add the token to the new expression
                if (isEmpty) {
                    newExpression.add(token);
                } else {
                    innerExpression.add(token);
                }
            } else if (identifiers.containsKey(token)) {
                innerExpression.add(identifiers.get(token).toString());
            } else if (Identifier.NAMES.contains(token)) {
                innerExpression.add(token);
            } else {
                // Identifier not found
                innerExpression.add("<unknown!>");
            }
        }

        root = ExpressionTreeHandler.buildRecursively(newExpression.iterator());
        return newExpression;
    }
}
