package h05.tree;

import h05.exception.UndefinedOperatorException;

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
     * The exponential function operator.
     */
    EXP("exp"),
    /**
     * The exponentiation operator.
     */
    EXPT("expt"),
    /**
     * The natural logarithm operator.
     */
    LN("ln"),
    /**
     * The logarithm operator.
     */
    LOG("log"),
    /**
     * The square root operator.
     */
    SQRT("sqrt");

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
     * Returns {@code true} if the given symbol is corresponding to an operator, {@code false} otherwise.
     *
     * @param symbol the symbol to check
     *
     * @return {@code true} if the given symbol is corresponding to an operator, {@code false} otherwise
     */
    public static boolean isOperator(String symbol) {
        for (Operator operator : Operator.values()) {
            if (operator.symbol.equals(symbol)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the operator corresponding to the given symbol.
     *
     * @param symbol the symbol of the operator.
     *
     * @return the operator corresponding to the given symbol
     *
     * @throws UndefinedOperatorException if the given symbol is not corresponding to any operator.
     */
    public static Operator getOperator(String symbol) {
        for (Operator operator : Operator.values()) {
            if (operator.symbol.equals(symbol)) {
                return operator;
            }
        }
        throw new UndefinedOperatorException(symbol);
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
