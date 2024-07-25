package cs2110;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents an application of a unary function to an expression.
 */
public class Application implements Expression {

    final UnaryFunction func;
    final Expression argument;

    /**
     * Constructs an Application with a specified unary function and argument. Takes parameter func
     * as the function of the unary function to be applied. Takes parameter argument which is the
     * argument to the function.
     */
    public Application(UnaryFunction func, Expression argument) {
        this.func = func;
        this.argument = argument;
    }


    /**
     * Evaluates the application using the provided variable table. Takes parameter vars as the
     * variable table containing values for variables and returns the evaluated result of the
     * function applied to the argument. throws UnboundVariableException if a variable is unbound in
     * the VarTable.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        double argValue = argument.eval(vars);
        return func.apply(argValue);
    }

    /**
     * Returns the number of operations needed to compute this expression.
     */
    @Override
    public int opCount() {
        return 1 + argument.opCount();
    }


    /**
     * Returns the infix string representation of the function applied to the argument.
     */
    @Override
    public String infixString() {
        String argString = argument.infixString();

        if (argString.contains(" ") && argString.charAt(0) != '(') {
            argString = "(" + argString + ")";
        }

        return func.name() + "(" + argString + ")";
    }

    /**
     * Returns the post fix string representation of the function applied to the argument.
     */
    @Override
    public String postfixString() {
        return argument.postfixString() + " " + func.name() + "()";
    }


    /**
     * Checks if the given object is equal to the application other and returns true if they are
     * equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Application)) {
            return false;
        }
        Application otherApp = (Application) other;
        return this.func.equals(otherApp.func) && this.argument.equals(otherApp.argument);
    }


    /**
     * Optimizes the application using the provided variable table. An Application node can be fully
     * optimized to a Constant if its children can all be evaluated to yield a number. But even if a
     * child depends on an unbound variable, the parent node can still be partially optimized by
     * creating a new copy whose children are replaced with their optimized forms.
     */
    @Override
    public Expression optimize(VarTable vars) {

        try {
            double result = this.eval(vars);
            return new Constant(result);
        } catch (UnboundVariableException e) {
            Expression argumentOptimized = argument.optimize(vars);
            return new Application(this.func, argumentOptimized);
        }
    }


    /**
     * Returns the set of variables on which this application depends on. An application node
     * depends on its arguments dependencies.
     */
    @Override
    public Set<String> dependencies() {
        return new HashSet<>(argument.dependencies());
    }
}
