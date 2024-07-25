package cs2110;

import java.util.Set;

/**
 * Represents a variable in an expression tree. This class encapsulates the name of the variable and
 * provides methods to evaluate its value, retrieve its name in different formats, and check its
 * dependencies.
 */
public class Variable implements Expression {

    /**
     * The name of this expression.
     */
    final String name;

    /**
     * Create a node variable with the given name  `name`.
     */
    public Variable(String name) {
        this.name = name;
    }

    /**
     * Evaluates the value of this variable using the provided variable table. Takes the parameter
     * vars and returns the value associated with this variable's name in the given variable table.
     * Throws UnboundVariableException If the variable's name is not found in the variable table.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        return vars.get(name);
    }

    /**
     * Returns the number of operations required to evaluate this variable's value. Since a variable
     * itself does not involve any operations, this method always returns 0.
     */
    @Override
    public int opCount() {
        return 0;
    }

    /**
     * Returns the infix representation of this variable's name. Any operator symbols present in the
     * name are spaced out for clarity.
     */

    @Override
    public String infixString() {
        String spacedName = name;

        String[] operators = {Operator.ADD_SYMBOL, Operator.SUBTRACT_SYMBOL,
                Operator.MULTIPLY_SYMBOL, Operator.DIVIDE_SYMBOL, Operator.POW_SYMBOL};

        for (String symbol : operators) {
            if (spacedName.contains(symbol)) {
                spacedName = spacedName.replace(symbol, " " + symbol + " ");
            }
        }


        return spacedName.trim();
    }

    /**
     * Returns the postfix representation of this variable's name.
     */

    @Override
    public String postfixString() {
        return name;
    }

    /**
     * Determines if this variable is equal to another object other Returns true if the other object
     * is a variable with the same name as this variable, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Variable newVariable = (Variable) other;
        return name.equals(newVariable.name);
    }

    /**
     * Attempts to optimize this variable using the provided variable table. Takes vars as the
     * parameter used for optimization and returns a new constant if this variable's name exists in
     * the variable table otherwise returns this variable.
     */
    @Override
    public Expression optimize(VarTable vars) {
        try {
            if (vars.contains(name)) {
                return new Constant(vars.get(name));
            }
        } catch (UnboundVariableException e) {
            // If an exception occurs during optimization, we return the current variable.
        }
        return this;
    }

    /**
     * Retrieves a set containing the dependencies of this variable. Returns a set containing only
     * this variable's name since a variable node depends on itself.
     */
    @Override
    public Set<String> dependencies() {
        return Set.of(this.name);
    }

}
