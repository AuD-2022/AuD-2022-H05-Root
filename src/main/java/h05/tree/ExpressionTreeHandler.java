package h05.tree;

import h05.exception.BadOperationException;
import h05.exception.ParenthesesMismatchException;
import h05.exception.UndefinedOperatorException;
import h05.math.MyNumber;

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
        boolean isLeft = token.equals(ArithmeticExpressionNode.LEFT_BRACKET);
        boolean isRight = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);

        if (isLeft && !expression.hasNext()) {
            // Validate parentheses
            throw new ParenthesesMismatchException();
        } else if (isLeft) {
            token = expression.next();
            // Validate operator
            // Operator and operands validation occurs in the constructor
            return new OperationExpressionNode(Operator.getOperator(token), buildRecursively(expression, null, null));
        } else if (isRight) {
            // No tail
            return null;
        } else if (MyNumber.isNumber(token)) {
            MyNumber number = MyNumber.parseNumber(token);
            return new LiteralExpressionNode(number);
        } else if (IdentifierExpressionNode.isIdentifier(token)) {
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
        if (!expression.hasNext()) {
            throw new BadOperationException("No expression");
        }

        Stack<Operator> operators = new Stack<>();
        // Stack to store/build the arithmetic expression node
        Stack<ListItem<ArithmeticExpressionNode>> operands = new Stack<>();
        // Faster access of the tail of an operand list
        Stack<ListItem<ArithmeticExpressionNode>> tails = new Stack<>();

        while (expression.hasNext()) {
            String token = expression.next();

            boolean isLeft = token.equals(ArithmeticExpressionNode.LEFT_BRACKET);
            boolean isRight = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);

            if (isLeft && !expression.hasNext()) {
                // Validate parentheses
                throw new ParenthesesMismatchException();
            } else if (isLeft) {
                token = expression.next();
                // Validate operator
                Operator operator = Operator.getOperator(token);

                operators.push(operator);
                // Marker node
                operands.push(null);
            } else if (isRight) {
                ListItem<ArithmeticExpressionNode> ops = null;
                // Retrieve operands
                while (!operands.isEmpty() && operands.peek() != null) {
                    ListItem<ArithmeticExpressionNode> head = operands.pop();
                    ListItem<ArithmeticExpressionNode> tail = tails.pop();

                    // Combine all operands
                    if (ops != null) {
                        // Old operands are added to the last of the previous operands
                        tail.next = ops;
                    }
                    // New operands head
                    ops = head;
                }

                // Missing left bracket to a right bracket
                if (operands.isEmpty()) {
                    throw new ParenthesesMismatchException();
                }
                // Remove marker node
                operands.pop();

                // Build the combined expression node
                // Operator and operands validation occurs in the constructor
                Operator operator = operators.pop();
                ListItem<ArithmeticExpressionNode> node = new ListItem<>();
                node.key = new OperationExpressionNode(operator, ops);
                operands.push(node);
                tails.push(node);
            } else if (MyNumber.isNumber(token)) {
                MyNumber number = MyNumber.parseNumber(token);
                ListItem<ArithmeticExpressionNode> node = new ListItem<>();
                node.key = new LiteralExpressionNode(number);

                // Create a new operand list if the previous element was the marker node
                if (operands.peek() == null) {
                    operands.push(node);
                    tails.push(node);
                } else {
                    // Add new operand to the tail of the previous operand list
                    ListItem<ArithmeticExpressionNode> tail = tails.pop();
                    tail = tail.next = node;
                    tails.push(tail);
                }
            } else if (IdentifierExpressionNode.isIdentifier(token)) {
                ListItem<ArithmeticExpressionNode> node = new ListItem<>();
                node.key = new IdentifierExpressionNode(token);

                // Create a new operand list if the previous element was the marker node
                if (operands.peek() == null) {
                    operands.push(node);
                    tails.push(node);
                } else {
                    // Add new operand to the tail of the previous operand list
                    ListItem<ArithmeticExpressionNode> tail = tails.pop();
                    tail = tail.next = node;
                    tails.push(tail);
                }
            } else {
                throw new BadOperationException(token);
            }

        }
        return operands.pop().key;
    }

    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param root the root node of the arithmetic expression tree
     *
     * @return the string representation of the arithmetic expression tree
     */
    public static List<String> reconstruct(ArithmeticExpressionNode root) {
        List<String> tokens = new ArrayList<String>();
        reconstruct(root, tokens);
        return tokens;
    }

    /**
     * Reconstructs the string representation of the arithmetic expression tree.
     *
     * @param node        the current node of the arithmetic expression tree
     * @param expressions the list of string representation of the arithmetic expression tree
     */
    private static void reconstruct(ArithmeticExpressionNode node, List<String> expressions) {
        if (node.isOperation()) {
            expressions.add(ArithmeticExpressionNode.LEFT_BRACKET);
            OperationExpressionNode operatorNode = (OperationExpressionNode) node;
            expressions.add(operatorNode.getOperator().toString());

            // Parse operands recursively
            for (ListItem<ArithmeticExpressionNode> operand = operatorNode.getOperands();
                 operand != null; operand = operand.next) {
                reconstruct(operand.key, expressions);
            }

            expressions.add(ArithmeticExpressionNode.RIGHT_BRACKET);
            return;
        } else if (node instanceof LiteralExpressionNode) {
            LiteralExpressionNode literalNode = (LiteralExpressionNode) node;
            expressions.add(literalNode.getValue().toString());
            return;
        } else if (node instanceof IdentifierExpressionNode) {
            IdentifierExpressionNode identifierNode = (IdentifierExpressionNode) node;
            expressions.add(identifierNode.getValue());
            return;
        }
        throw new IllegalArgumentException("Unknown node type");
    }
}
