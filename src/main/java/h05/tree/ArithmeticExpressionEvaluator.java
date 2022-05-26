package h05.tree;

import h05.math.MyNumber;

import java.util.ArrayList;
import java.util.Iterator;
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

        return nextStep(expression.iterator(), new ArrayList<>(expression.size()));
    }

    /**
     * Evaluates the arithmetic expression tree by replacing the variables (identifiers) of the expression with their values and
     * evaluates the most inner expressions.
     *
     * @param tokens     the tokens to evaluate
     * @param expression the built expression so far
     *
     * @return the list of tokens representing  the evaluation
     */
    private List<String> nextStep(Iterator<String> tokens, List<String> expression) {
        boolean isInner = true;
        while (tokens.hasNext()) {
            String token = tokens.next();
            boolean isLeft = token.equals(ArithmeticExpressionNode.LEFT_BRACKET);
            boolean isRight = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);
            if (isLeft) {
                // If the iteration contains an open bracket, it cannot be the innermost expression since the open bracket will
                // be added by the caller
                List<String> innerExpression = new ArrayList<>();
                innerExpression.add(token);
                expression.addAll(nextStep(tokens, innerExpression));
                isInner = false;
            } else if (isRight && isInner) {
                // Evaluate only the innermost expression
                expression.add(token);
                ArithmeticExpressionNode node = ExpressionTreeHandler.buildRecursively(expression.iterator());
                MyNumber number = node.evaluate(identifiers);
                LiteralExpressionNode newNode = new LiteralExpressionNode(number);
                expression.clear();
                expression.addAll(ExpressionTreeHandler.reconstruct(newNode));
                return expression;
            } else if (isRight) {
                expression.add(token);
                return expression;
            } else if (identifiers.containsKey(token)) {
                expression.add(identifiers.get(token).toString());
            } else {
                expression.add(token);
            }
        }
        root = ExpressionTreeHandler.buildRecursively(expression.iterator());
        return expression;
    }
}
