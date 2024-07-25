package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedSeqTest {

    // Helper functions for creating lists used by multiple tests.  By constructing strings with
    // `new`, more likely to catch inadvertent use of `==` instead of `.equals()`.

    /**
     * Creates [].
     */
    static Seq<String> makeList0() {
        return new LinkedSeq<>();
    }

    /**
     * Creates ["A"].  Only uses prepend.
     */
    static Seq<String> makeList1() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B"].  Only uses prepend.
     */
    static Seq<String> makeList2() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates ["A", "B", "C"].  Only uses prepend.
     */
    static Seq<String> makeList3() {
        Seq<String> ans = new LinkedSeq<>();
        ans.prepend(new String("C"));
        ans.prepend(new String("B"));
        ans.prepend(new String("A"));
        return ans;
    }

    /**
     * Creates a list containing the same elements (in the same order) as array `elements`.  Only
     * uses prepend.
     */
    static <T> Seq<T> makeList(T[] elements) {
        Seq<T> ans = new LinkedSeq<>();
        for (int i = elements.length; i > 0; i--) {
            ans.prepend(elements[i - 1]);
        }
        return ans;
    }

    @Test
    void testConstructorSize() {
        Seq<String> list = new LinkedSeq<>();
        assertEquals(0, list.size());
    }

    @Test
    void testPrependSize() {
        // List creation helper functions use prepend.
        Seq<String> list;

        list = makeList1();
        assertEquals(1, list.size());

        list = makeList2();
        assertEquals(2, list.size());

        list = makeList3();
        assertEquals(3, list.size());
    }

    @Test
    void testToString() {
        Seq<String> list;

        list = makeList0();
        assertEquals("[]", list.toString());

        list = makeList1();
        assertEquals("[A]", list.toString());

        list = makeList2();
        assertEquals("[A, B]", list.toString());

        list = makeList3();
        assertEquals("[A, B, C]", list.toString());
    }

    @Test
    void testContains(){
        //Test 1 when list does not contain elem
        {
            Seq<String> newList = new LinkedSeq<>();
            assertFalse(newList.contains("elem"), "Test 1 failed: Expected 'elem' to not be in the list");
        }
        //Test 2 when list contains elem once.
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.prepend(new String("elem"));
            assertTrue(newList.contains("elem"), "Test 2 failed: Expected 'elem' to not be in the list");
        }
        //Test 3 when list contains elem twice
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.prepend(new String("elem"));
            newList.prepend(new String("elem"));
            assertTrue(newList.contains("elem"), "Test 3 failed: Expected 'elem' to not be in the list");
        }
        // Test 4: List is empty
        {
            Seq<String> newList = new LinkedSeq<>();
            assertFalse(newList.contains(null), "Test 4 failed: Expected null element to not be in the empty list");
        }
        // Test 5: Checking for null element in a non-empty list
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.prepend(new String("elem"));
            assertFalse(newList.contains(null), "Test 5 failed: Expected null element to not be in the list");
        }
        //Test 6: Where element is the last
        {
            Seq<String> newList;
            newList = makeList3();
            newList.append("D");
            assertTrue(newList.contains("D"), "Test 6 failed: Expected 'A' to be in the list");
        }



    }
    @Test
    void testGet(){
        //Test 1: Get element from an empty list
        {
            Seq<String> list;
            list = makeList0();
            try {
                list.get(0); // throw an exception since the list is empty
                assert false : "Test failed: Expected an exception when getting an element from an empty list.";
            } catch (ArrayIndexOutOfBoundsException e) {
                // Expected exception
            }
        }
        // Test 2: Get element at index 0
        {
            Seq<String> list;
            list = makeList3();
            String elem = list.get(0);
            assert "A".equals(elem) : "Test 2 failed: Expected element0, got " + elem;
        }
        //Test 3: Get element at Index 1
        {
            Seq<String> list;
            list = makeList3();
            String elem = list.get(1);
            assert "B".equals(elem) : "Test 3 failed: Expected element1, got " + elem;
        }
        //Test 4: Get element at Index 2
        {
            Seq<String> list;
            list = makeList3();
            String elem = list.get(2);
            assert "C".equals(elem) : "Test 4 failed: Expected element2, got " + elem;
        }
        //Test 5: Get element at invalid index (negative index)
        {
            Seq<String> list;
            list = makeList3();
            try {
                list.get(-1);
                assert false : "Test 5 failed: Expected an exception for negative index.";
            } catch (ArrayIndexOutOfBoundsException e) {
                // Expected exception
            }
        }
        //Test 6: Index ouf of Bounds (index = size of list)
        {
            Seq<String> list;
            list = makeList3();
            try {
                list.get(3);
                assert false : "Test 6 failed: Expected an exception for out-of-bounds index.";
            } catch (ArrayIndexOutOfBoundsException e) {
                // Expected exception
            }
        }
    }
    @Test
    void testAppend(){
        //Test 1: Appending to an empty list
        {
            Seq<String> list;
            list = makeList0();
            list.append("A");
            assert list.size() == 1;
            assert list.get(0).equals("A");
        }
        //Test 2: Appending Multiple elements
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.append("A");
            newList.append("B");
            newList.append("C");
            assert newList.size() == 3;
            assert newList.get(0).equals("A");
            assert newList.get(1).equals("B");
            assert newList.get(2).equals("C");


        }
        // Test 3: Appending duplicate elements
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.append("A");
            newList.append("A");
            newList.append("A");
            assert newList.size() == 3;
            assert newList.get(0).equals("A");
            assert newList.get(1).equals("A");
            assert newList.get(2).equals("A");
        }

    }
    @Test
    void testInsertBefore(){
        //Test 1: Inserting before the first element
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.append("B");
            newList.insertBefore("A", "B");
            assert newList.size() == 2;
            assert newList.get(0).equals("A");
        }
        //Test 2: Inserting before the middle element
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.append("A");
            newList.append("C");
            newList.append("D");
            newList.insertBefore("B","C");
            assert newList.get(1).equals("B");
        }
        //Test 3: Inserting before the last element
        {
            Seq<String> newList = new LinkedSeq<>();
            newList.append("A");
            newList.append("B");
            newList.append("C");
            newList.insertBefore("D", "C");
            assert newList.get(2).equals("D");
        }
        // Test 4: Trying to insert before a non-existent element
        {
            try {
                Seq<String> newList = new LinkedSeq<>();
                newList.append("A");
                newList.append("B");
                newList.append("C");
                newList.insertBefore("E", "D");
                assert false : "Test failed: Expected a NoSuchElementException.";
            } catch (NoSuchElementException e) {
                // Expected exception
            }
        }


    }
    @Test
    void testRemove(){
        //Test 1:  Removing element from an empty list
        {
            Seq<String> list;
            list = makeList0();
            boolean result = list.remove("A");
            assert !result;
            assert list.size() == 0;
        }
        //Test 2: Removing element where list size = 1
        {
            Seq<String> list;
            list = makeList1();
            boolean result = list.remove("A");
            assert result;
            assert list.size() == 0;
        }
        //Test 3: Element does not exist in list
        {
            Seq<String> list;
            list = makeList3();
            boolean result = list.remove("D");
            assert !result;
            assert list.size() == 3;
        }
        //Test 4: Removing the first element
        {
            Seq<String> list;
            list = makeList3();
            boolean result = list.remove("A");
            assert result;
            assert list.size() == 2;
            assert list.get(0).equals("B");
        }
        //Test 5: Removing the last element
        {
            Seq<String> list;
            list = makeList3();
            boolean result = list.remove("C");
            assert result;
            assert list.size() == 2;
            assert list.get(1).equals("B");
        }
        // Test 6: Removing an element that occurs more than once but next to each other.
        {
            Seq<String> list;
            list = makeList3();
            list.insertBefore("B", "C");
            boolean result = list.remove("B");
            assert result;
            assert list.size() == 3;
            assert list.get(1).equals("B");  // Only the first "B" should be removed
        }
        // Test 7: Removing an element that occurs more than once but not next to each other
        {
            Seq<String> list;
            list = makeList3();
            list.append("B");
            boolean result = list.remove("B");
            assert result;
            assert list.size() == 3;
            assert list.get(2).equals("B");
        }
    }
    @Test
    void testEquals(){
        //Test 1: Two empty strings
        {
            Seq<String> newList1;
            newList1 = makeList0();
            Seq<String> newList2;
            newList2 = makeList0();
            assert newList1.equals(newList2);
        }
        //Test 2: Two lists with different lengths
        {
            Seq<String> newList1;
            newList1 = makeList0();
            Seq<String> newList2;
            newList2 = makeList1();
            assert !newList1.equals(newList2);
        }
        //Test 3: Two lists with same length and values
        {
            Seq<String> newList1;
            newList1 = makeList2();
            Seq<String> newList2;
            newList2 = makeList2();
            assert newList1.equals(newList2);
        }
        // Test 4: Two lists with same length but different values
        {
            Seq<String> newList1;
            newList1 = makeList2();
            newList1.append("C");
            Seq<String> newList2;
            newList2 = makeList2();
            newList2.append("D");
            assert !newList1.equals(newList2);
        }
        //Test 5: List of same length with same values but different order
        {
            Seq<String> newList1 = new LinkedSeq<>();
            newList1.append("A");
            newList1.append("B");
            newList1.append("C");

            Seq<String> newList2 = new LinkedSeq<>();
            newList2.append("C");
            newList2.append("B");
            newList2.append("A");
            assert !newList1.equals(newList2);
        }
        //Test 6: When other is not an Instance of LinkedSeq.
        {
            Seq<String> newList = new LinkedSeq<>();
            String nonSeqObject = "A";
            newList.append("A");
            assert !newList.equals(nonSeqObject);
        }

    }





    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered `hashCode()` or `assertThrows()` in class.
     */

    @Test
    void testHashCode() {
        assertEquals(makeList0().hashCode(), makeList0().hashCode());

        assertEquals(makeList1().hashCode(), makeList1().hashCode());

        assertEquals(makeList2().hashCode(), makeList2().hashCode());

        assertEquals(makeList3().hashCode(), makeList3().hashCode());
    }

    @Test
    void testIterator() {
        Seq<String> list;
        Iterator<String> it;

        list = makeList0();
        it = list.iterator();
        assertFalse(it.hasNext());
        Iterator<String> itAlias = it;
        assertThrows(NoSuchElementException.class, () -> itAlias.next());

        list = makeList1();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertFalse(it.hasNext());

        list = makeList2();
        it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals("A", it.next());
        assertTrue(it.hasNext());
        assertEquals("B", it.next());
        assertFalse(it.hasNext());
    }
}
