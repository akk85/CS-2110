package cs2110;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.NoSuchElementException;

public class RpnParser {

    /**
     * Parse the RPN expression in `exprString` and return the corresponding expression tree. Tokens
     * must be separated by whitespace.  Valid tokens include decimal numbers (scientific notation
     * allowed), arithmetic operators (+, -, *, /, ^), function names (with the suffix "()"), and
     * variable names (anything else).  When a function name is encountered, the corresponding
     * function will be retrieved from `funcDefs` using the name (without "()" suffix) as the key.
     *
     * @throws IncompleteRpnException     if the expression has too few or too many operands
     *                                    relative to operators and functions.
     * @throws UndefinedFunctionException if a function name applied in `exprString` is not present
     *                                    in `funcDefs`.
     */
    public static Expression parse(String exprString, Map<String, UnaryFunction> funcDefs)
            throws IncompleteRpnException, UndefinedFunctionException {

        //Initialize a Stack of expression nodes.
        Deque<Expression> stack = new ArrayDeque<>();

        for (Token token : Token.tokenizer(exprString)) {
            //check if substring is a number. If so push a leaf node into the stack
            if (token instanceof Token.Number numToken) {
                Constant ConstLeafNode = new Constant(numToken.doubleValue());
                stack.push(ConstLeafNode);
                //check if substring is a Variable. if so push a leaf node into stack.
            } else if (token instanceof Token.Variable varToken) {
                Variable varLeafNode = new Variable(varToken.value);
                stack.push(varLeafNode);
                //check if substring is an Operator. If so pop the corresponding operands. Get
                //operator via opValue() and construct a new interior node with the operation
                //and push new node into stack.
            } else if (token instanceof Token.Operator opToken) {
                try {
                    Expression right = stack.pop();
                    Expression left = stack.pop();
                    Operator op = opToken.opValue();
                    Operation newOperation = new Operation(op, left, right);
                    stack.push(newOperation);
                } catch (NoSuchElementException e) {
                    throw new IncompleteRpnException(exprString, stack.size());
                }
                //check if substring is a Function name. If so pop the corresponding operands. Get
                //operator via opValue() and construct a new interior node with the operation
                //and push new node into stack.
            } else if (token instanceof Token.Function funcToken) {
                UnaryFunction func = funcDefs.get(funcToken.name());
                if (func == null) {
                    throw new UndefinedFunctionException("Undefined function: " + funcToken.name());
                }
                try {
                    Expression arg = stack.pop();
                    Application newApplication = new Application(func, arg);
                    stack.push(newApplication);
                } catch (NoSuchElementException e) {
                    throw new IncompleteRpnException(exprString, stack.size());
                }
            }
        }
        //Assuming the expression is valid, there will be one node left on the stack.
        //This is the root of the expression tree.
        if (stack.size() == 1) {
            return stack.pop();
        } else {
            throw new IncompleteRpnException(exprString, stack.size());
        }
    }
}
