package h05.tree;

import h05.exception.UndefinedIdentifierException;
import h05.math.MyNumber;
import h05.math.MyReal;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents predefined identifiers (constants).
 *
 * @author Nhan Huynh
 * @see <a href="https://docs.racket-lang.org/reference/generic-numbers.html#%28part._.Extra_.Constants_and_.Functions%29">
 * https://docs.racket-lang.org/reference/generic-numbers.html#%28part._.Extra_.Constants_and_.Functions%29</a>
 */
public enum Identifier {

    /**
     * The {@code MyNumber} value that is closer than any other to <i>e</i>, the base of the natural logarithms.
     */
    E("e", new MyReal(BigDecimal.valueOf(Math.E))),
    /**
     * The {@code MyNumber} value that is closer than any other to <i>pi</i>, the ratio of the circumference of a circle to its
     * diameter.
     */
    PI("pi", new MyReal(BigDecimal.valueOf(Math.PI)));

    /**
     * Contains all identifiers name.
     */
    public static final Set<String> NAMES = Arrays.stream(Identifier.values())
        .map(Identifier::getName)
        .collect(Collectors.toSet());

    /**
     * The name of this identifier.
     */
    private final String name;

    /**
     * The value of this identifier.
     */
    private final MyNumber value;

    /**
     * Constructs and initializes an identifier with the given name and value.
     *
     * @param name  the name of the identifier
     * @param value the value of the identifier
     */
    Identifier(String name, MyNumber value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Returns the identifier corresponding to the given name.
     *
     * @param name the name of the identifier.
     *
     * @return the identifier corresponding to the given name
     *
     * @throws UndefinedIdentifierException if the given name is not corresponding to any identifier
     */
    public static Identifier getIdentifier(String name) {
        for (Identifier identifier : Identifier.values()) {
            if (identifier.name.equals(name)) {
                return identifier;
            }
        }
        throw new UndefinedIdentifierException(name);
    }

    /**
     * Returns the name of this identifier.
     *
     * @return the name of this identifier
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value of this identifier.
     *
     * @return the value of this identifier
     */
    public MyNumber getValue() {
        return value;
    }
}
