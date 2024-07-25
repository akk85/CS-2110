package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {

    //Test cases for the constructors and observers
    @Test
    void testConstructorAndObservers() {
        // Typical case
        {
            Student s = new Student("Tony", "Kariuki");
            assertEquals("Tony", s.firstName());
            assertEquals("Kariuki", s.lastName());
            assertEquals(0, s.credits());
        }

        // Short names
        {
            Student s = new Student("f", "l");
            assertEquals("f", s.firstName());
            assertEquals("l", s.lastName());
            assertEquals(0, s.credits());
        }
    }

    //Test case to test method fullName
    @Test
    void testFullName() {
        //Typical case
        {
            Student student1 = new Student("first", "last");
            assertEquals("first last", student1.fullName());
        }

        {
            Student student2 = new Student("Tony", "Kariuki");
            assertEquals("Tony Kariuki", student2.fullName());
        }

        {
            Student student3 = new Student("Curran", "Muhlberger");
            assertEquals("Curran Muhlberger", student3.fullName());
        }

    }

    //Test case to test method adjustCredits.
    @Test
    void testAdjustCredits() {
        {
            Student newStudent = new Student("first", "last");
            newStudent.adjustCredits(3);
            assertEquals(3, newStudent.credits());

            // A second adjustment should be cumulative
            newStudent.adjustCredits(4);
            assertEquals(7, newStudent.credits());

            // Negative adjustments
            newStudent.adjustCredits(-3);
            assertEquals(4, newStudent.credits());

            // Back to zero
            newStudent.adjustCredits(-4);
            assertEquals(0, newStudent.credits());

        }
    }

    //Test case to test method toStrings
    @Test
    void testToStrings() {
        {
            Student newStudent1 = new Student("Tony", "Kariuki");
            Student newStudent2 = new Student("Albert", "Einstein");
            Student newStudent3 = new Student("1", "2");
            assertEquals("Tony Kariuki", newStudent1.toString());
            assertEquals("Albert Einstein", newStudent2.toString());
            assertEquals("1 2", newStudent3.toString());

        }
    }
}
