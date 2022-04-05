package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.ParenthesesMismatchException;
import h05.exception.UndefinedOperatorException;
import h05.math.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * This class is used to parse an expression and build a tree out of it.
 *
 * @author Nhan Huynh
 */
public final class ExpressionTreeHandler {

    /**
     * Don't let anyone instantiate this class.
     */
    private ExpressionTreeHandler() {
    }

    /**
     * Builds an arithmetic expression tree from a string recursively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     *
     * @return the root node of the arithmetic expression tree
     *
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     * @see #buildRecursively(Iterator, ListItem, ListItem)
     */
    public static ArithmeticExpressionNode buildRecursively(Iterator<String> expression) {
        // This cannot happen, the recursion anchor is defined in the helper method
        if (!expression.hasNext()) {
            throw new BadOperationException("No expression");
        }
        String token = expression.next();
        boolean isLeftParenthesis = token.equals(ArithmeticExpressionNode.LEFT_BRACKET);
        boolean isRightParenthesis = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);

        if (isLeftParenthesis && !expression.hasNext()) {
            // Validate parentheses
            throw new ParenthesesMismatchException();
        } else if (isLeftParenthesis) {
            token = expression.next();
            // Validate operator
            // Operator and operands validation occurs in the constructor
            return new OperationExpressionNode(Operator.getOperator(token), buildRecursively(expression, null, null));
        } else if (isRightParenthesis) {
            // No tail
            return null;
        } else if (MyNumber.isNumber(token)) {
            MyNumber number = MyNumber.parseNumber(token);
            return new LiteralExpressionNode(number);
        } else if (IdentifierExpressionNode.IDENTIFIER_FORMAT.reset(token).matches()) {
            return new IdentifierExpressionNode(token);
        }
        throw new BadOperationException(token);
    }

    /**
     * Builds a sequence of arithmetic expression node from a string.
     *
     * @param expression the string representation of the arithmetic expression to parse
     *
     * @return the sequence of arithmetic expression node
     */
    private static ListItem<ArithmeticExpressionNode> buildRecursively(
        Iterator<String> expression,
        ListItem<ArithmeticExpressionNode> head,
        ListItem<ArithmeticExpressionNode> tail) {
        // Recursion anchor - no tokens left
        if (!expression.hasNext()) {
            return head;
        }

        ArithmeticExpressionNode node = buildRecursively(expression);

        // Reached right parenthesis
        if (node == null) {
            return head;
        }
        ListItem<ArithmeticExpressionNode> item = new ListItem<>();
        item.key = node;
        if (head == null) {
            return buildRecursively(expression, item, item);
        } else {
            tail.next = item;
            return buildRecursively(expression, head, item);
        }
    }

    /**
     * Builds an arithmetic expression tree from a string iteratively.
     *
     * @param expression the string representation of the arithmetic expression to parse
     *
     * @return the root node of the arithmetic expression tree
     *
     * @throws BadOperationException        if the iterator has no more tokens
     * @throws ParenthesesMismatchException if the parentheses are mismatched
     * @throws UndefinedOperatorException   if the operator is not defined
     */
    public static ArithmeticExpressionNode buildIteratively(Iterator<String> expression) {
        var stack = new Stack<BuildIterativelyStackFrame>();

        if (!expression.hasNext()) {
            throw new BadOperationException("No expression");
        }

        stack.push(new BuildIterativelyStackFrame(null));

        while (expression.hasNext()) {
            var token = expression.next();

            if (token.equals(ArithmeticExpressionNode.LEFT_BRACKET)) {
                var operator = Operator.getOperator(expression.next());
                stack.push(new BuildIterativelyStackFrame(operator));
            } else if (token.equals(ArithmeticExpressionNode.RIGHT_BRACKET)) {
                var argument = stack.pop();

                if (stack.isEmpty()) {
                    throw new ParenthesesMismatchException();
                }

                stack.peek().addArgument(argument.toArithmeticExpressionNode());
            } else if (isIdentifier(token)) {
                stack.peek().addArgument(new IdentifierExpressionNode(token));
            } else {
                stack.peek().addArgument(new LiteralExpressionNode(toNumber(token)));
            }
        }

        if (stack.size() > 1) {
            throw new ParenthesesMismatchException();
        }

        return stack.pop().arguments.key;
    }

    private static MyNumber toNumber(String token) {
        if (token.contains("/")) {
            var parts = token.split("/");
            BigInteger top = new BigInteger(parts[0]);
            BigInteger bottom = new BigInteger(parts[1]);
            return new MyRational(new Rational(top, bottom));
        }

        if (token.matches("-?\\d+")) {
            return new MyInteger(new BigInteger(token));
        }

        return new MyReal(new BigDecimal(token));
    }

    private static boolean isIdentifier(String token) {
        return IdentifierExpressionNode.IDENTIFIER_FORMAT.reset(token).matches();
    }

    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param root the root node of the arithmetic expression tree
     *
     * @return the string representation of the arithmetic expression tree
     */
    public static List<String> reconstruct(ArithmeticExpressionNode root) {
        return reconstruct(root, new ArrayList<>());
    }

    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param node        the current node of the arithmetic expression tree
     * @param expressions the list of string representation of the arithmetic expression tree
     *
     * @return the string representation of the arithmetic expression tree
     */
    private static List<String> reconstruct(ArithmeticExpressionNode node, List<String> expressions) {
        if (node.isOperand()) {
            expressions.add(node.toString());
            return expressions;
        }

        expressions.add(ArithmeticExpressionNode.LEFT_BRACKET);
        OperationExpressionNode operatorNode = (OperationExpressionNode) node;
        expressions.add(operatorNode.getOperator().toString());

        // Parse operands recursively
        for (ListItem<ArithmeticExpressionNode> operand = operatorNode.getOperands();
             operand != null; operand = operand.next) {
            reconstruct(operand.key, expressions);
        }

        expressions.add(ArithmeticExpressionNode.RIGHT_BRACKET);
        return expressions;
    }

    private static class BuildIterativelyStackFrame {
        private final Operator operator;
        private ListItem<ArithmeticExpressionNode> arguments;
        private ListItem<ArithmeticExpressionNode> tail;

        public BuildIterativelyStackFrame(Operator operator) {
            this.operator = operator;
            arguments = tail = null;
        }

        ArithmeticExpressionNode toArithmeticExpressionNode() {
            return new OperationExpressionNode(operator, arguments);
        }

        public void addArgument(ArithmeticExpressionNode node) {
            if (arguments == null) {
                arguments = tail = new ListItem<>();
            } else {
                tail = tail.next = new ListItem<>();
            }

            tail.key = node;
        }
    }
}
