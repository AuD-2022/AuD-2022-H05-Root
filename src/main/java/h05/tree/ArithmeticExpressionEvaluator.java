package h05.tree;

import h05.math.MyNumber;
import h05.math.MyRational;
import h05.math.MyReal;
import h05.math.Rational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.ListIterator;
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
        ListIterator<String> it = expression.listIterator();
        while (it.hasNext()) {
            String token = it.next();
            if (Operator.SYMBOLS.contains(token) || MyNumber.isNumber(token)
                || token.equals(ArithmeticExpressionNode.LEFT_BRACKET)
                || token.equals(ArithmeticExpressionNode.RIGHT_BRACKET)
            ) {
                continue;
            }
            if (identifiers.containsKey(token)) {
                it.set(identifiers.get(token).toString());
            } else {
                it.set("<unknown!>");
            }
        }
        // TODO parenthesis
        return expression;
    }
}
