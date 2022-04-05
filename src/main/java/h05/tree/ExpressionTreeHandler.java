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
        throw new ParenthesesMismatchException();
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

        // Stack to store/build the arithmetic expression node
        Stack<ListItem<ArithmeticExpressionNode>> operands = new Stack<>();

        // Faster access of the tail of an operand list
        Stack<ListItem<ArithmeticExpressionNode>> tails = new Stack<>();

        while (expression.hasNext()) {
            String token = expression.next();

            // Expression group
            boolean isStart = token.equals(ArithmeticExpressionNode.LEFT_BRACKET);
            boolean isEnd = token.equals(ArithmeticExpressionNode.RIGHT_BRACKET);

            if (isStart && !expression.hasNext()) {
                // Missing operation and closing bracket
                throw new ParenthesesMismatchException();
            } else if (isStart) {
                token = expression.next();

                // Validate operator
                Operator operator = Operator.getOperator(token);

                // Marker node - Contains only the operator
                ListItem<ArithmeticExpressionNode> item = new ListItem<>();
                item.key = new OperationExpressionNode(operator, null);
                operands.push(item);
                tails.push(item);
            } else if (isEnd) {
                ListItem<ArithmeticExpressionNode> ops = null;

                // Retrieve operands
                while (!operands.isEmpty()) {
                    ListItem<ArithmeticExpressionNode> item = operands.pop();
                    ListItem<ArithmeticExpressionNode> tail = tails.pop();
                    ArithmeticExpressionNode node = item.key;

                    // Combine all operands
                    if (operands != null) {
                        // Old operands are added to the last of the previous operands
                        tail.next = ops;
                    }
                    // New operands head
                    ops = item;

                    // Break if we reached the marker node - Contains only the operator
                    if (node instanceof OperationExpressionNode) {
                        OperationExpressionNode operatorNode = (OperationExpressionNode) node;
                        if (operatorNode.getOperands() == null) {
                            break;
                        }
                    }
                }

                // Missing left parenthesis to a right parenthesis
                if (ops == null) {
                    throw new ParenthesesMismatchException();
                }
                // Marker node - Contains only the operator
                OperationExpressionNode operatorNode = (OperationExpressionNode) ops.key;
                ops = ops.next;

                Operator operator = operatorNode.getOperator();

                // Build the combined expression node
                // Operator and operands validation occurs in the constructor
                OperationExpressionNode node = new OperationExpressionNode(operator, ops);
                ListItem<ArithmeticExpressionNode> operand = new ListItem<>();
                operand.key = node;
                operands.push(operand);
                tails.push(operand);
            } else if (MyNumber.isNumber(token)) {
                ListItem<ArithmeticExpressionNode> tail = tails.pop();
                MyNumber value = MyNumber.parseNumber(token);
                tail = tail.next = new ListItem<>();
                tail.key = new LiteralExpressionNode(value);
            } else if (IdentifierExpressionNode.IDENTIFIER_FORMAT.reset(token).matches()) {
                ListItem<ArithmeticExpressionNode> tail = tails.pop();
                tail = tail.next = new ListItem<>();
                tail.key = new IdentifierExpressionNode(token);
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
}
