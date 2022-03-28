package h05.tree;

/**
 * Represents predefined identifiers.
 *
 * @author Nhan Huynh
 */
public enum Identifier {

    /**
     * The euler constant.
     */
    E("e", Math.E),
    /**
     * The pi constant.
     */
    PI("pi", Math.PI);

    /**
     * The name of this identifier.
     */
    private final String name;

    /**
     * The value of this identifier.
     */
    private final double value;

    /**
     * Constructs and initializes an identifier with the given name and value.
     *
     * @param name  the name of the identifier
     * @param value the value of the identifier
     */
    Identifier(String name, double value) {
        this.name = name;
        this.value = value;
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
    public double getValue() {
        return value;
    }
}
