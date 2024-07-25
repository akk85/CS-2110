package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RpnParserTest {

    @Test
    @DisplayName("Parsing an expression consisting of a single number should yield a Constant " +
            "node with that value")
    void testParseConstant() throws IncompleteRpnException, UndefinedFunctionException {
        Expression expr = RpnParser.parse("1.5", Map.of());
        assertEquals(new Constant(1.5), expr);
    }

    @Test
    @DisplayName("Parsing an expression consisting of a single identifier should yield a " +
            "Variable node with that name")
    void testParseVariable() throws IncompleteRpnException, UndefinedFunctionException {
        Expression expr = RpnParser.parse("x", Map.of());
        assertInstanceOf(Variable.class, expr);
        assertEquals(new Variable("x"), expr);
    }

    @Test
    @DisplayName("Parsing an expression ending with an operator should yield an Operation node " +
            "evaluating to the expected value")
    void testParseOperation()
            throws UnboundVariableException, IncompleteRpnException, UndefinedFunctionException {
        //Test Case 1 with same operands as a commutative operand
        {
            Expression expr = RpnParser.parse("1 1 +", Map.of());
            assertInstanceOf(Operation.class, expr);
            assertEquals(2.0, expr.eval(MapVarTable.empty()));

        }
        //Test Case 2 with different operands
        {
            Expression expr = RpnParser.parse("2 5 +", Map.of());
            assertInstanceOf(Operation.class, expr);
            assertEquals(7.0, expr.eval(MapVarTable.empty()));

        }
        //Test case 3 with an operator that's not commutative. e.g. Subtraction
        {
            Expression expr = RpnParser.parse("10 4 -", Map.of());
            assertInstanceOf(Operation.class, expr);
            assertEquals(6.0, expr.eval(MapVarTable.empty()));
        }
        //Test case 4 with an operator that's not commutative. e.g. Division
        {
            Expression expr = RpnParser.parse("20 4 /", Map.of());
            assertInstanceOf(Operation.class, expr);
            assertEquals(5.0, expr.eval(MapVarTable.empty()));
        }
        //Test Case 5 for a chain of operations
        {
            Expression expr = RpnParser.parse("2 3 + 4 * 5 /", Map.of());
            assertInstanceOf(Operation.class, expr);
            assertEquals(4.0, expr.eval(MapVarTable.empty()));
        }
        //Test case 6 when passing multiple operators in sequence
        {
            assertThrows(IncompleteRpnException.class, () -> RpnParser.parse("1 2 ++", Map.of()));
        }
    }

    @Test
    @DisplayName("Parsing an expression ending with a function should yield an Application node " +
            "evaluating to the expected value")
    void testParseApplication()
            throws UnboundVariableException, IncompleteRpnException, UndefinedFunctionException {
        //Test Case 1 with one application
        {
            Expression expr = RpnParser.parse("4 sqrt()", UnaryFunction.mathDefs());
            assertInstanceOf(Application.class, expr);
            assertEquals(2.0, expr.eval(MapVarTable.empty()));
        }
        //Test 2 with a nested application
        {
            Expression expr = RpnParser.parse("16 sqrt() sqrt()", UnaryFunction.mathDefs());
            assertInstanceOf(Application.class, expr);
            assertEquals(2.0, expr.eval(MapVarTable.empty()));
        }
        //Test 3 with complex expressions
        {
            Expression expr = RpnParser.parse("1 5 7 * + sqrt()", UnaryFunction.mathDefs());
            assertInstanceOf(Application.class, expr);
            assertEquals(6.0, expr.eval(MapVarTable.empty()));
        }

    }

    @Test
    @DisplayName("Parsing an empty expression should throw an IncompleteRpnException")
    void testParseEmpty() {
        assertThrows(IncompleteRpnException.class, () -> RpnParser.parse("", Map.of()));
    }

    @Test
    @DisplayName("Parsing an expression that leave more than one term on the stack should throw " +
            "an IncompleteRpnException")
    void testParseIncomplete() {
        assertThrows(IncompleteRpnException.class, () -> RpnParser.parse("1 1 1 +", Map.of()));
    }

    @Test
    @DisplayName("Parsing an expression that consumes more terms than are on the stack should " +
            "throw an IncompleteRpnException")
    void testParseUnderflow() {
        assertThrows(IncompleteRpnException.class, () -> RpnParser.parse("1 1 + +", Map.of()));
    }

    @Test
    @DisplayName("Parsing an expression that applies an unknown function should throw an " +
            "UnknownFunctionException")
    void testParseUndefined() {
        assertThrows(UndefinedFunctionException.class, () -> RpnParser.parse("1 foo()", Map.of()));
    }

    @Test
    @DisplayName("Parsing a single function application with no arguments should throw an error")
    void testSingleFunction() {
        assertThrows(IncompleteRpnException.class, () -> RpnParser.parse("sqrt()", UnaryFunction.mathDefs()));
    }


}
