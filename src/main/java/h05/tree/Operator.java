package h05.tree;

import h05.exception.BadOperationException;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the available operators.
 *
 * @author Nhan Huynh
 */
public enum Operator {

    /**
     * The addition operator.
     */
    ADD("+"),
    /**
     * The subtraction operator.
     */
    SUB("-"),
    /**
     * The multiplication operator.
     */
    MUL("*"),
    /**
     * The division operator.
     */
    DIV("/"),
    /**
     * The power operator.
     */
    POW("pow"),
    /**
     * The logarithm operator.
     */
    LOG("log"),
    /**
     * The square root operator.
     */
    SQRT("sqrt");

    /**
     * Contains all operators symbol.
     */
    public static final Set<String> SYMBOLS = Arrays.stream(Operator.values())
        .map(Operator::getSymbol).collect(Collectors.toSet());
    /**
     * The operator's symbol.
     */
    private final String symbol;

    /**
     * Constructs and initializes an operator with the given symbol.
     *
     * @param symbol the operator's symbol.
     */
    Operator(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Returns the operator corresponding to the given symbol.
     *
     * @param symbol the symbol of the operator.
     *
     * @return the operator corresponding to the given symbol
     *
     * @throws BadOperationException if the given symbol is not a valid operator
     */
    public static Operator getOperator(String symbol) {
        for (Operator operator : Operator.values()) {
            if (operator.symbol.equals(symbol)) {
                return operator;
            }
        }
        throw new BadOperationException(symbol);
    }

    /**
     * Returns the operator's symbol.
     *
     * @return the operator's symbol.
     */
    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}