package cs2110;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantExpressionTest {

    @Test
    @DisplayName("A Constant node should evaluate to its value (regardless of var table)")
    void testEval() throws UnboundVariableException {
        Expression expr = new Constant(1.5);
        assertEquals(1.5, expr.eval(MapVarTable.empty()));
    }


    @Test
    @DisplayName("A Constant node should report that 0 operations are required to evaluate it")
    void testOpCount() {
        Expression expr = new Constant(1.5);
        assertEquals(0, expr.opCount());
    }


    @Test
    @DisplayName("A Constant node should produce an infix representation with just its value (as " +
            "formatted by String.valueOf(double))")
    void testInfix() {
        Expression expr = new Constant(1.5);
        assertEquals("1.5", expr.infixString());

        expr = new Constant(Math.PI);
        assertEquals("3.141592653589793", expr.infixString());
    }

    @Test
    @DisplayName("A Constant node should produce an postfix representation with just its value " +
            "(as formatted by String.valueOf(double))")
    void testPostfix() {
        Expression expr = new Constant(1.5);
        assertEquals("1.5", expr.postfixString());

        expr = new Constant(Math.PI);
        assertEquals("3.141592653589793", expr.postfixString());
    }


    @Test
    @DisplayName("A Constant node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Constant(1.5);
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("A Constant node should equal another Constant node with the same value")
    void testEqualsTrue() {
        Expression expr1 = new Constant(1.5);
        Expression expr2 = new Constant(1.5);
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("A Constant node should not equal another Constant node with a different value")
    void testEqualsFalse() {
        Expression expr1 = new Constant(1.5);
        Expression expr2 = new Constant(2.0);
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("A Constant node does not depend on any variables")
    void testDependencies() {
        Expression expr = new Constant(1.5);
        Set<String> deps = expr.dependencies();
        assertTrue(deps.isEmpty());
    }


    @Test
    @DisplayName("A Constant node should optimize to itself (regardless of var table)")
    void testOptimize() {
        Expression expr = new Constant(1.5);
        Expression opt = expr.optimize(MapVarTable.empty());
        assertEquals(expr, opt);
    }
}

class VariableExpressionTest {

    @Test
    @DisplayName("A Variable node should evaluate to its variable's value when that variable is " +
            "in the var map")
    void testEvalBound() throws UnboundVariableException {
        String testName = "A";
        Double testValue = 22.0;
        MapVarTable table = MapVarTable.of(testName, testValue);
        Variable variable = new Variable(testName);
        assertEquals(testValue, variable.eval(table));

    }

    @Test
    @DisplayName("A Variable node should throw an UnboundVariableException when evaluated if its " +
            "variable is not in the var map")
    void testEvalUnbound() {
        Expression expr = new Variable("x");
        assertThrows(UnboundVariableException.class, () -> expr.eval(MapVarTable.empty()));
    }


    @Test
    @DisplayName("A Variable node should report that 0 operations are required to evaluate it")
    void testOpCount() {
        Expression expr = new Variable("A");
        assertEquals(0, expr.opCount());
    }


    @Test
    @DisplayName("A Variable node should produce an infix representation with just its name")
    void testInfix() {
        Expression expr = new Variable("A");
        assertEquals("A", expr.infixString());

    }

    @Test
    @DisplayName("A Variable node should produce an postfix representation with just its name")
    void testPostfix() {
        Expression expr = new Variable("A");
        assertEquals("A", expr.postfixString());
    }


    @Test
    @DisplayName("A Variable node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Variable("x");
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("A Variable node should equal another Variable node with the same name")
    void testEqualsTrue() {
        // Force construction of new String objects to detect inadvertent use of `==`
        Expression expr1 = new Variable(new String("x"));
        Expression expr2 = new Variable(new String("x"));
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("A Variable node should not equal another Variable node with a different name")
    void testEqualsFalse() {
        Expression expr1 = new Variable(new String("x"));
        Expression expr2 = new Variable(new String("y"));
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("A Variable node only depends on its name")
    void testDependencies() {
        Expression expr = new Variable("x");
        Set<String> deps = expr.dependencies();
        assertTrue(deps.contains("x"));
        assertEquals(1, deps.size());
    }


    @Test
    @DisplayName("A Variable node should optimize to a Constant if its variable is in the var map")
    void testOptimizeBound() {
        Expression expr = new Variable("x");
        Expression opt = expr.optimize(MapVarTable.of("x", 1.5));
        assertEquals(new Constant(1.5), opt);
    }

    @Test
    @DisplayName("A Variable node should optimize to itself if its variable is not in the var map")
    void testOptimizeUnbound() {
        Expression expr = new Variable("x");
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertTrue(optimizedExpr instanceof Variable);
        assertEquals("x", optimizedExpr.postfixString());
        assertEquals(expr, optimizedExpr);
    }
}

class OperationExpressionTest {

    @Test
    @DisplayName("An Operation node for ADD with two Constant operands should evaluate to their " +
            "sum")
    void testEvalAdd() throws UnboundVariableException {
        Expression expr = new Operation(Operator.ADD, new Constant(1.5), new Constant(2));
        assertEquals(3.5, expr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("An Operation node for ADD with a Variable for an operand should evaluate " +
            "to its operands' sum when the variable is in the var map")
    void testEvalAddBound() throws UnboundVariableException {
        String testName = "X";
        double testValue = 1.5;
        MapVarTable table = MapVarTable.of(testName, testValue);
        Expression expr = new Operation(Operator.ADD, new Variable("X"), new Constant(2));
        assertEquals(3.5, expr.eval(table));
    }

    @Test
    @DisplayName("An Operation node for ADD with a Variable for an operand should throw an " +
            "UnboundVariableException when evaluated if the variable is not in the var map")
    void testEvalAddUnbound() {
        Expression expr = new Operation(Operator.ADD, new Variable("X"), new Constant(2));
        assertThrows(UnboundVariableException.class, () -> expr.eval(MapVarTable.empty()));
    }


    @Test
    @DisplayName("An Operation node with leaf operands should report that 1 operation is " +
            "required to evaluate it")
    void testOpCountLeaves() {
        Expression expr = new Operation(Operator.ADD, new Variable("X"), new Constant(2));
        assertEquals(1, expr.opCount());
    }


    @Test
    @DisplayName("An Operation node with an Operation for either or both operands should report " +
            "the correct number of operations to evaluate it")
    void testOpCountRecursive() {
        Expression expr = new Operation(Operator.ADD,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")),
                new Constant(2.0));
        assertEquals(2, expr.opCount());

        expr = new Operation(Operator.SUBTRACT,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")),
                new Operation(Operator.DIVIDE, new Constant(1.5), new Variable("x")));
        assertEquals(3, expr.opCount());
    }


    @Test
    @DisplayName("An Operation node with leaf operands should produce an infix representation " +
            "consisting of its first operand, its operator symbol surrounded by spaces, and " +
            "its second operand, all enclosed in parentheses")
    void testInfixLeaves() {
        {
            Expression expr = new Operation(Operator.ADD, new Constant(1), new Constant(2));
            assertEquals("(1.0 + 2.0)", expr.infixString());
        }
        {
            Expression expr = new Operation(Operator.MULTIPLY, new Variable("2y"), new Constant(1));
            assertEquals("(2y * 1.0)", expr.infixString());
        }
        {
            Expression expr = new Operation(Operator.ADD, new Variable("x"), new Constant(3));
            assertEquals("(x + 3.0)", expr.infixString());
        }
        {
            Expression multiplication = new Operation(Operator.MULTIPLY, new Constant(2.0),
                    new Variable("y"));
            Expression addition = new Operation(Operator.ADD, multiplication, new Constant(1.0));
            Expression expr = new Operation(Operator.POW, addition, new Constant(3.0));
            assertEquals("(((2.0 * y) + 1.0) ^ 3.0)", expr.infixString());
        }
    }

    @Test
    @DisplayName("An Operation node with an Operation for either operand should produce the " +
            "expected infix representation with parentheses around each operation")
    void testInfixRecursive() {
        Expression expr = new Operation(Operator.ADD,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")),
                new Constant(2.0));
        assertEquals("((1.5 * x) + 2.0)", expr.infixString());

        expr = new Operation(Operator.SUBTRACT,
                new Constant(2.0),
                new Operation(Operator.DIVIDE, new Constant(1.5), new Variable("x")));
        assertEquals("(2.0 - (1.5 / x))", expr.infixString());
    }


    @Test
    @DisplayName("An Operation node with leaf operands should produce a postfix representation " +
            "consisting of its first operand, its second operand, and its operator symbol " +
            "separated by spaces")
    void testPostfixLeaves() {
        {
            Expression expr = new Operation(Operator.ADD, new Constant(1), new Constant(2.0));
            assertEquals("1.0 2.0 +", expr.postfixString());
        }
        {
            Expression expr = new Operation(Operator.MULTIPLY, new Variable("2y"),
                    new Constant(1.0));
            assertEquals("2y 1.0 *", expr.postfixString());
        }
    }

    @Test
    @DisplayName("An Operation node with an Operation for either operand should produce the " +
            "expected postfix representation")
    void testPostfixRecursive() {
        Expression expr = new Operation(Operator.ADD,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")),
                new Constant(2.0));
        assertEquals("1.5 x * 2.0 +", expr.postfixString());

        expr = new Operation(Operator.SUBTRACT,
                new Constant(2.0),
                new Operation(Operator.DIVIDE, new Constant(1.5), new Variable("x")));
        assertEquals("2.0 1.5 x / -", expr.postfixString());
    }


    @Test
    @DisplayName("An Operation node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("An Operation node should equal another Operation node with the same " +
            "operator and operands")
    void testEqualsTrue() {
        Expression expr1 = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        Expression expr2 = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Operation node should not equal another Operation node with a different " +
            "operator")
    void testEqualsFalseDifferentOperator() {
        Expression expr1 = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        Expression expr2 = new Operation(Operator.DIVIDE, new Constant(1.5), new Variable("x"));
        assertFalse(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Operation node should not equal another Operation node with a same " +
            "operator but different operands")
    void testEqualsFalseDifferentOperands() {
        Expression expr1 = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        Expression expr2 = new Operation(Operator.ADD, new Constant(1.0), new Variable("x"));
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("An Operation node should not equal another node which is not of the same " +
            "instance as an Operation")
    void testEqualsFalseInstance() {
        Expression expr1 = new Operation(Operator.ADD, new Constant(1.5), new Variable("x"));
        Expression expr2 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("An Operation node depends on the dependencies of both of its operands")
    void testDependencies() {
        Expression expr = new Operation(Operator.ADD, new Variable("x"), new Variable("y"));
        Set<String> deps = expr.dependencies();
        assertTrue(deps.contains("x"));
        assertTrue(deps.contains("y"));
        assertEquals(2, deps.size());
    }


    @Test
    @DisplayName("An Operation node for ADD with two Constant operands should optimize to a " +
            "Constant containing their sum")
    void testOptimizeAdd() throws UnboundVariableException {
        Expression expr = new Operation(Operator.ADD, new Constant(10), new Constant(15));
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertEquals(25.0, optimizedExpr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("An Operation node for SUBTRACT  with two Constant operands should optimize " +
            " to a Constant containing their differences")
    void testOptimizeSubtract() throws UnboundVariableException {
        Expression expr = new Operation(Operator.SUBTRACT, new Constant(30), new Constant(15));
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertEquals(15.0, optimizedExpr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("An Operation node for MULTIPLICATION with two Constant operands should " +
            "optimize to a Constant containing their sum")
    void testOptimizeMULTIPLIPY() throws UnboundVariableException {
        Expression expr = new Operation(Operator.MULTIPLY, new Constant(10), new Constant(15));
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertEquals(150.0, optimizedExpr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("Evaluating an Operation node with an unbound operation should " +
            " throw an UnboundVariableException")
    void testOptimizeUnboundOperationException() {
        Expression left = new Variable("x");
        Expression right = new Constant(5.0);
        Expression expr = new Operation(Operator.ADD, left, right);
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertTrue(optimizedExpr instanceof Operation);
    }


}

class ApplicationExpressionTest {

    @Test
    @DisplayName("An Application node for SQRT with a Constant argument should evaluate to its " +
            "square root")
    void testEvalSqrt() throws UnboundVariableException {
        UnaryFunction sqrtFunction = UnaryFunction.SQRT;
        Expression argument = new Constant(16);
        Application SQRT = new Application(sqrtFunction, argument);
        double result = SQRT.eval(null);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("An Application node with a Variable for its argument should throw an " +
            "UnboundVariableException when evaluated if the variable is not in the var map")
    void testEvalAbsUnbound() {
        Expression argument = new Variable("x");
        assertThrows(UnboundVariableException.class, () -> argument.eval(MapVarTable.empty()));

    }


    @Test
    @DisplayName("An Application node with a leaf argument should report that 1 operation is " +
            "required to evaluate it")
    void testOpCountLeaf() {
        {
            UnaryFunction sqrtFunction = UnaryFunction.SQRT;
            Expression argument = new Constant(16);
            Application SQRT = new Application(sqrtFunction, argument);

            double result = SQRT.opCount();

            assertEquals(1, result);
        }
        {
            UnaryFunction sinFunction = UnaryFunction.SIN;

            Expression argument = new Variable("y/2");
            Application SIN = new Application(sinFunction, argument);

            double result = SIN.opCount();

            assertEquals(1, result);
        }
    }


    @Test
    @DisplayName("An Application node with non-leaf expressions for its argument should report " +
            "the correct number of operations to evaluate it")
    void testOpCountRecursive() {
        Expression expr = new Application(UnaryFunction.SQRT,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")));
        assertEquals(2, expr.opCount());
    }


    @Test
    @DisplayName(
            "An Application node with a leaf argument should produce an infix representation " +
                    "consisting of its name, followed by the argument enclosed in parentheses.")
    void testInfixLeaves() {
        UnaryFunction sqrtFunction = UnaryFunction.SIN;
        Expression argument = new Variable("y/2");
        Application SIN = new Application(sqrtFunction, argument);

        String result = SIN.infixString();

        assertEquals("sin((y / 2))", result);
    }

    @Test
    @DisplayName("An Application node with an Operation for its argument should produce the " +
            "expected infix representation with redundant parentheses around the argument")
    void testInfixRecursive() {
        Expression expr = new Application(UnaryFunction.ABS,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")));
        assertEquals("abs((1.5 * x))", expr.infixString());
    }


    @Test
    @DisplayName("An Application node with a leaf argument should produce a postfix " +
            "representation consisting of its argument, followed by a space, followed by its " +
            "function's name appended with parentheses")
    void testPostfixLeaves() {
        UnaryFunction sqrtFunction = UnaryFunction.SIN;
        Expression argument = new Variable("y/2");
        Application SIN = new Application(sqrtFunction, argument);

        String result = SIN.postfixString();

        assertEquals("y/2 sin()", result);
    }

    @Test
    @DisplayName("An Application node with an Operation for its argument should produce the " +
            "expected postfix representation")
    void testPostfixRecursive() {
        Expression expr = new Application(UnaryFunction.ABS,
                new Operation(Operator.MULTIPLY, new Constant(1.5), new Variable("x")));
        assertEquals("1.5 x * abs()", expr.postfixString());
    }

    @Test
    @DisplayName("An Application node should equal itself")
    void testEqualsSelf() {
        Expression expr = new Application(UnaryFunction.SQRT, new Constant(4.0));
        assertTrue(expr.equals(expr));
    }

    @Test
    @DisplayName("An Application node should equal another Application node with the same " +
            "function and argument")
    void testEqualsTrue() {
        Expression expr1 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        Expression expr2 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        assertTrue(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Application node should not equal another Application node with a different " +
            "argument")
    void testEqualsFalseArg() {
        Expression expr1 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        Expression expr2 = new Application(UnaryFunction.SQRT, new Constant(8.0));
        assertFalse(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Application node should not equal another Application node with a different " +
            "function")
    void testEqualsFalseFunc() {
        Expression expr1 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        Expression expr2 = new Application(UnaryFunction.ABS, new Constant(4.0));
        assertFalse(expr1.equals(expr2));
    }

    @Test
    @DisplayName("An Application node should not equal another node which is not of the same " +
            "instance as an Application")
    void testEqualsFalseInstance() {
        Expression expr1 = new Application(UnaryFunction.SQRT, new Constant(4.0));
        Expression expr2 = new Constant(12);
        assertFalse(expr1.equals(expr2));
    }


    @Test
    @DisplayName("An Application node for SQRT with a Constant argument should optimize to a " +
            "Constant containing its square root")
    void testOptimizeConstant() throws UnboundVariableException {

        Expression expr = new Application(UnaryFunction.SQRT, new Constant(4.0));
        Expression optimizedExpr = expr.optimize(MapVarTable.empty());
        assertTrue(optimizedExpr instanceof Constant);
        assertEquals(2.0, optimizedExpr.eval(MapVarTable.empty()));
    }

    @Test
    @DisplayName("An Application node with an argument depending on a variable should optimize " +
            "to an Application node if the variable is unbound")
    void testOptimizeUnbound() {
        Expression expr = new Application(UnaryFunction.SQRT, new Variable("x"));
        Expression opt = expr.optimize(MapVarTable.empty());
        assertInstanceOf(Application.class, opt);
    }

    @Test
    @DisplayName("Evaluating an Application node with an unbound variable should throw an UnboundVariableException")
    void testEvalUnboundVariableException() {
        Expression expr = new Application(UnaryFunction.SQRT, new Variable("x"));
        assertThrows(UnboundVariableException.class, () -> expr.eval(MapVarTable.empty()));
    }


    @Test
    @DisplayName("An Application node has the same dependencies as its argument")
    void testDependencies() {
        Expression arg = new Variable("x");
        Expression expr = new Application(UnaryFunction.SQRT, arg);
        Set<String> argDeps = arg.dependencies();
        Set<String> exprDeps = expr.dependencies();
        assertEquals(argDeps, exprDeps);
    }


}
