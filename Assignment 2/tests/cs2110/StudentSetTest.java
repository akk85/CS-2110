package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import com.sun.source.tree.AssertTree;
import org.junit.jupiter.api.Test;


class StudentSetTest {

    //Test case for the constructor and observers for class StudentTest
    @Test
    void testConstructorAndSize() {
        // Constructor should yield an empty set
        StudentSet students = new StudentSet();
        assertEquals(0, students.size());
    }

    // Complete TODOs 9-10, then run it
    // again and confirm that it now passes.  As you proceed with TODOs 11-13, start by adding a
    // test case here for the method being implemented, then repeat the above (confirm that it
    // fails, implement the method, confirm that it passes).  This T/ODO is complete when there are
    // test cases covering all of `StudentSet`'s public methods.
    // Be sure that at least one test case triggers a resize, given your chosen initial capacity.


    //Test case for the method add
    @Test
    void testAdd() {
        //This test is to test the method add in Student Set
        {
            // create new Student Set
            StudentSet newStudentSet = new StudentSet();
            // New Student
            Student s = new Student("Tony", "Kariuki");

            //Check initial size of set
            assertEquals(0, newStudentSet.size());

            //Add new Student S to the set
            newStudentSet.add(s);

            //Check new size of set
            assertEquals(1, newStudentSet.size());
        }
        //Small test case of trying to add a student that is already in the set
        {
            StudentSet newStudentSet = new StudentSet();

            Student newStudent = new Student("Tony", "Kariuki");

            //Check initial size of set
            assertEquals(0, newStudentSet.size());

            //Add new Student S to the set
            newStudentSet.add(newStudent);

            //Attempt to add new student to set again

            newStudentSet.add(newStudent);

            assertEquals(1, newStudentSet.size());


        }

        // Larger Test Case
        {
            // create new Student Set
            StudentSet newStudentSet = new StudentSet();

            //Add more students than initial capacity
            for (int i = 1; i <= 15; i++) {
                Student s = new Student("first " + i, "last " + i);
                newStudentSet.add(s);
            }
            assertEquals(15, newStudentSet.size());
        }

    }

    //Test case for testing the method contains
    @Test
    void testContains() {
        //Create a new Student Set
        {
            StudentSet Set1 = new StudentSet();

            // Create new students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Walker", "White");

            //Add students to the set
            Set1.add(student1);
            Set1.add(student2);

            //Test if set contains the added students
            assertTrue(Set1.contains(student1));
            assertTrue(Set1.contains(student2));

            // Create new student not in this set
            Student student3 = new Student("Curran", "Muhlberger");

            // Test if set contains the new student
            assertFalse(Set1.contains(student3));
        }

    }

    //Test case for the method remove
    @Test
    void testRemove() {
        {
            //Create a new Student Set
            StudentSet newSet = new StudentSet();

            //Create new students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Walker", "White");
            Student student3 = new Student("Curran", "Muhlberger");

            //Add Students to the set
            newSet.add(student1);
            newSet.add(student2);
            newSet.add(student3);

            //remove existing students from set
            assertTrue(newSet.remove(student1));
            assertEquals(2, newSet.size());

            //remove non-existing student
            assertFalse(newSet.remove(student1));
            assertEquals(2, newSet.size());

            //remove another existing student from the set
            assertTrue(newSet.remove(student2));
            assertEquals(1, newSet.size());

            //remove non - existing student from set
            assertFalse(newSet.remove(student2));
            assertEquals(1, newSet.size());

            //remove last student from set
            assertTrue(newSet.remove(student3));
            assertEquals(0, newSet.size());

            //remove non - existing student from set
            assertFalse(newSet.remove(student3));
            assertEquals(0, newSet.size());
        }


    }

    //Test case for when resizing happens
    @Test
    void testResizeBackingArray() {
        {
            StudentSet newSet = new StudentSet();

            //Add students exceeding the initial student size
            for (int i = 1; i <= 20; i++) {
                Student newStudent = new Student("First " + i, "Last " + i);
                newSet.add(newStudent);
            }
            assertEquals(20, newSet.size());
        }
    }


    @Test
        //Test case for method toString
    void testToString() {
        //Test Scenario 1 for Normal Names
        {
            //Create new student set
            StudentSet newStudentSet = new StudentSet();

            //Add new students to set
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Empty student set should just be an empty string
            assertEquals("{}", newStudentSet.toString());

            //Add students to set
            newStudentSet.add(student1);
            newStudentSet.add(student2);

            //Check string representation
            assertEquals("{Tony Kariuki, Albert Einstein}", newStudentSet.toString());
        }
        {
            //Create new student set
            StudentSet newStudentSet = new StudentSet();

            //Add new students to set
            Student student1 = new Student("1a2b", "1b2a");
            Student student2 = new Student("2a1b", "2b1a");

            //Empty student set should just be an empty string
            assertEquals("{}", newStudentSet.toString());

            //Add students to set
            newStudentSet.add(student1);
            newStudentSet.add(student2);

            //Check string representation
            assertEquals("{1a2b 1b2a, 2a1b 2b1a}", newStudentSet.toString());
        }

    }


}
