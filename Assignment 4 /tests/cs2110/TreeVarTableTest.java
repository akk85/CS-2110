package cs2110;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TreeVarTableTest {

    private TreeVarTable varTable;

    @Test
    void testSetAndGet() throws UnboundVariableException {
        varTable = new TreeVarTable();
        //Test 1: Setting one variable
        {
            varTable.set("A", 10.0);
            assertEquals(10.0, varTable.get("A"));
        }
        //Setting multiple variables
        {
            varTable.set("A", 10.5);
            varTable.set("B", 15.5);
            varTable.set("C", 20.5);
            assertEquals(10.5, varTable.get("A"));
            assertEquals(15.5, varTable.get("B"));
            assertEquals(20.5, varTable.get("C"));
        }
        //Test 3. Setting value to same variable
        {
            varTable.set("A", 10.0);
            varTable.set("A", 20.0);

            assertEquals(20.0, varTable.get("A"));
        }
    }

    @Test
    void testGetVariableNotPresent(){
        varTable = new TreeVarTable();
        assertThrows(UnboundVariableException.class, () -> varTable.get("A"));
    }

    @Test
    void testUnsetVariable() throws UnboundVariableException {
        varTable = new TreeVarTable();
        varTable.set("A", 10.0);
        assertEquals(10.0, varTable.get("A"));

        varTable.unset("A");
        assertThrows(UnboundVariableException.class, () -> varTable.get("A"));

    }

    @Test
    void testContains() {
        varTable = new TreeVarTable();
        varTable.set("X", 100.5);
        assertTrue(varTable.contains("X"));
        assertFalse(varTable.contains("Y"));
    }

    @Test
    void testSize() {
        varTable = new TreeVarTable();
        assertEquals(0, varTable.size());

        varTable.set("M", 50.5);
        varTable.set("N", 60.5);
        assertEquals(2, varTable.size());

        varTable.unset("M");
        assertEquals(1, varTable.size());
    }

    @Test
    void testNames() {
        varTable = new TreeVarTable();
        varTable.set("R", 200.5);
        varTable.set("S", 250.5);

        Set<String> names = varTable.names();
        assertTrue(names.contains("R"));
        assertTrue(names.contains("S"));
        assertFalse(names.contains("T"));
    }
    @Test
    void testUnsetVariableNotPresent() {
        varTable = new TreeVarTable();
        // Try unsetting a variable that's not set
        assertDoesNotThrow(() -> varTable.unset("A"));
    }

    @Test
    void testInsertDuplicateVariables() {
        varTable = new TreeVarTable();
        varTable.set("A", 10.0);
        assertEquals(1, varTable.size());

        varTable.set("A", 20.0);
        assertEquals(1, varTable.size());

    }

    @Test
    void testInsertionAndDeletionSequence() {
        varTable = new TreeVarTable();

        varTable.set("B", 2.0);
        varTable.set("A", 1.0);
        varTable.set("C", 3.0);

        assertEquals(3, varTable.size());
        assertTrue(varTable.contains("A"));
        assertTrue(varTable.contains("B"));
        assertTrue(varTable.contains("C"));

        varTable.unset("A");
        assertFalse(varTable.contains("A"));
        assertEquals(2, varTable.size());

        varTable.unset("B");
        assertFalse(varTable.contains("B"));
        assertEquals(1, varTable.size());

        assertTrue(varTable.contains("C"));
    }


    @Test
    void testLargeSequentialInsert() throws UnboundVariableException {
        varTable = new TreeVarTable();
        for (int i = 0; i < 1000; i++) {
            varTable.set("Var" + i, i * 1.0);
        }

        for (int i = 0; i < 1000; i++) {
            assertEquals(i * 1.0, varTable.get("Var" + i));
        }

        assertEquals(1000, varTable.size());
    }

    @Test
    void testLargeRandomInsertAndDelete() {
        varTable = new TreeVarTable();
        Set<String> variableNames = new HashSet<>();

        for (int i = 0; i < 1000; i++) {
            String varName = "Var" + (int) (Math.random() * 2000);
            varTable.set(varName, i * 1.0);
            variableNames.add(varName);
        }

        for (String varName : variableNames) {
            assertTrue(varTable.contains(varName));
            varTable.unset(varName);
            assertFalse(varTable.contains(varName));
        }
    }
}
