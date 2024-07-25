package cs2110;

import java.util.HashSet;
import java.util.Set;


/**
 * Represents an operation consisting of an operator and two operand expressions.
 */
public class Operation implements Expression {

    final Operator op;
    final Expression leftOperand;
    final Expression rightOperand;

    /**
     * Constructs an Operation with a specified operator, left operand, and right operand. Takes
     * three parameters , op The operator for the operation, leftOperand - The left operand
     * expression and rightOperand - The right operand expression.
     */
    public Operation(Operator op, Expression leftOperand, Expression rightOperand) {
        this.op = op;
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }

    /**
     * Evaluates the operation using the provided variable table. Takes parameter vars which is a
     * variable table containing values for variables and returns the evaluated result of the
     * operation. Throws an UnboundVariableException if a variable is unbound in the VarTable.
     */
    @Override
    public double eval(VarTable vars) throws UnboundVariableException {
        double leftValue = leftOperand.eval(vars);
        double rightValue = rightOperand.eval(vars);

        return op.operate(leftValue, rightValue);
    }

    /**
     * Returns the number of operations needed to compute this expression.
     */
    @Override
    public int opCount() {
        return 1 + leftOperand.opCount() + rightOperand.opCount();
    }


    /**
     * Returns the infix string representation of this operation.
     */
    @Override
    public String infixString() {
        return "(" + leftOperand.infixString() + " " + op.symbol() + " "
                + rightOperand.infixString() + ")";
    }

    /**
     * Returns the postfix string representation of this operation.
     */
    @Override
    public String postfixString() {
        return leftOperand.postfixString() + " " + rightOperand.postfixString() + " " + op.symbol();
    }

    /**
     * Determines if this operation is equal to another object other Returns true if the other
     * object is an operation with the same operator and operands as this variable and false
     * otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Operation)) {
            return false;
        }
        Operation otherApp = (Operation) other;
        return this.op.equals(otherApp.op) && this.leftOperand.equals(otherApp.leftOperand)
                && this.rightOperand.equals(otherApp.rightOperand);

    }

    /**
     * Optimizes the operation using the provided variable table. An Operation node can be fully
     * optimized to a Constant if its operands all be evaluated to yield a number. But even if an
     * operand depends on an unbound variable the Operation node can still be partially optimized by
     * creating a new copy whose operands are replaced with their optimized forms.
     */
    @Override
    public Expression optimize(VarTable vars) {

        try {
            double result = this.eval(vars);
            return new Constant(result);
        } catch (UnboundVariableException e) {
            Expression leftOptimized = leftOperand.optimize(vars);
            Expression rightOptimized = rightOperand.optimize(vars);
            return new Operation(this.op, leftOptimized, rightOptimized);
        }
    }

    /**
     * Retrieves a set containing the dependencies of this operation. An operation node depends on
     * dependencies of its arguments ie left and right dependencies.
     */
    @Override
    public Set<String> dependencies() {
        Set<String> resultSet = new HashSet<>(leftOperand.dependencies());
        resultSet.addAll(rightOperand.dependencies());
        return resultSet;
    }
}
