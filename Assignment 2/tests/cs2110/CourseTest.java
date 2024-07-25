package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseTest {

    // Write a test case that covers `Course`'s constructor and the observers that have
    // already been implemented in the release code.  Confirm that the case initially fails, then
    // complete
    // As you proceed with TODOs 16-22, start by adding a test case here for the method being
    // implemented, then repeat the above (confirm that it fails, implement the method, confirm that
    // it passes).
    // public methods.
    // Be sure to verify any effects on objects of other classes as well.


    @Test
    void testConstructorsAndObservers() {
        //Sample course
        Course newCourse1 = new Course("Object-Oriented Programming and Data Structures", 4,
                "Muhlberger", "Bailey Hall 101", 8, 30, 90);
        //Observers
        assertEquals("Object-Oriented Programming and Data Structures", newCourse1.title());
        assertEquals(4, newCourse1.credits());
        assertEquals("Bailey Hall 101", newCourse1.location());

        Course newCourse2 = new Course("Differential Equation for Engineers", 4,
                "Ritz", "Klarman B01", 10, 30, 90);
        //Observers
        assertEquals("Differential Equation for Engineers", newCourse2.title());
        assertEquals(4, newCourse2.credits());
        assertEquals("Klarman B01", newCourse2.location());

        Course newCourse3 = new Course("D", 1,
                "R", "K", 10, 30, 90);
        //Observers
        assertEquals("D", newCourse3.title());
        assertEquals(1, newCourse3.credits());
        assertEquals("K", newCourse3.location());

    }

    @Test
    void testInstructor() {

        Course newCourse1 = new Course("Object-Oriented Programming and Data Structures", 4,
                "Muhlberger", "Bailey Hall 101", 8, 30, 90);

        assertEquals("Professor Muhlberger", newCourse1.instructor());

        Course newCourse2 = new Course("Intro to Computing with Python", 4,
                "White", "Uris 101", 8, 30, 90);

        assertEquals("Professor White", newCourse2.instructor());

        Course newCourse3 = new Course("Digital LOgic and Computer Organization", 4,
                "Albonesi", "Phillips Hall 101", 8, 30, 90);

        assertEquals("Professor Albonesi", newCourse3.instructor());

    }

    @Test
    void testFormatTime() {

        Course math = new Course("math ", 4, "Kariuki", "Hollister 101", 0, 0, 90);
        assertEquals("12:00 AM", math.formatStartTime());

        Course math2 = new Course("math ", 4, "Kariuki", "Hollister 101", 14, 40, 90);
        assertEquals("2:40 PM", math2.formatStartTime());

        Course algebra = new Course("Linear Algebra ", 4, "Kunal", "Mallot 101", 8, 30, 90);
        assertEquals("8:30 AM", algebra.formatStartTime());

        Course physics = new Course("Physics", 3, "Fulbright", "Rockefeller 101", 12, 30, 90);
        assertEquals("12:30 PM", physics.formatStartTime());

        Course physics2 = new Course("Physics2", 4, "Jones", "Rockefeller 101B", 11, 59, 90);
        assertEquals("11:59 AM", physics2.formatStartTime());

        Course algorithms = new Course("Algorithms", 4, "Kleinberg", "Statler 101", 14, 30, 90);
        assertEquals("2:30 PM", algorithms.formatStartTime());

        Course networks = new Course("Networks", 4, "Murran", "Gates 101", 22, 59, 10);
        assertEquals("10:59 PM", networks.formatStartTime());

        Course chemistry = new Course("Chemistry", 4, "Chemistry", "Baker 101", 23, 59, 1);
        assertEquals("11:59 PM", chemistry.formatStartTime());

        Course swim = new Course("Networks", 4, "Murran", "Gates 101", 22, 30, 90);
        assertEquals("10:30 PM", swim.formatStartTime());

    }

    //Test for method Overlap
    @Test
    void testOverlap() {

        Course dataStructures = new Course("Data Structures", 4, "Muhlberger",
                "Bailey Hall 101", 10, 0, 60);

        Course introToPython = new Course("Introduction to Python", 4,
                "White", "Bailey Hall 101", 11, 0, 60);

        assertFalse(dataStructures.overlaps(introToPython));

        Course algorithms = new Course("algorithms", 4,
                "Kleinberg", "Bailey Hall 101", 10, 0, 61);

        Course math = new Course("Mathematics", 4, "White", "Bailey Hall 101", 11, 0, 60);

        assertTrue(algorithms.overlaps(math));

        Course swim = new Course("algorithms", 4,
                "Kleinberg", "Bailey Hall 101", 11, 59, 61);

        Course bowling = new Course("Mathematics", 4, "White", "Bailey Hall 101", 13, 0, 60);

        assertFalse(swim.overlaps(bowling));

    }

    @Test
    void testHasStudent() {
        //Test Scenario 1 with the student is not in Course
        {
            //New course and new student
            Course newCourse = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            Student newStudent = new Student("Tony", "Kariuki");

            assertFalse(newCourse.hasStudent(newStudent));
        }
        //Test Scenario 2 with 1 student not in Course two in course
        {
            //New course and new student
            Course newCourse = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            Student newStudent1 = new Student("Tony", "Kariuki");
            Student newStudent2 = new Student("Sheldon", "Cooper");
            Student newStudent3 = new Student("Curan", "Muhlberger");

            newCourse.enrollStudent(newStudent1);
            newCourse.enrollStudent(newStudent2);

            assertTrue(newCourse.hasStudent(newStudent1));
            assertTrue(newCourse.hasStudent(newStudent2));
            assertFalse(newCourse.hasStudent(newStudent3));

        }
    }

    @Test
    void testEnrollStudent() {
        {
            //New course and new student
            Course newCourse = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            Student newStudent = new Student("Tony", "Kariuki");

            //Enroll student into course
            boolean enrollmentChange = newCourse.enrollStudent(newStudent);

            //Check that enrollment has changed
            assertTrue(enrollmentChange);

            //check student is now in course
            assertTrue(newCourse.hasStudent(newStudent));

            //Check that newStudent credits have been adjusted
            assertEquals(4, newStudent.credits());
        }
        //Test No 2
        {
            Course newCourse1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            Student newStudent1 = new Student("Tony", "Kariuki");

            //Enroll student into course
            boolean enrollmentChange1 = newCourse1.enrollStudent(newStudent1);

            //Attempt to Enroll student again in same course
            boolean enrollmentChange2 = newCourse1.enrollStudent(newStudent1);

            //Check student remains enrolled after 2nd attempt
            assertTrue(newCourse1.hasStudent(newStudent1));

            //Check that no additional enrollment changed happened after 2nd attempt
            assertTrue(enrollmentChange1);
            assertFalse(enrollmentChange2);

            //Check students credits
            assertEquals(4, newStudent1.credits());
            assertNotEquals(8, newStudent1.credits());
        }
    }

    @Test
    void testDropStudent() {
        {
            Course newCourse = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            Student newStudent = new Student("Tony", "Kariuki");

            //Enroll student into course
            boolean enrollmentChange1 = newCourse.enrollStudent(newStudent);

            //Drop student from course
            boolean dropStudent = newCourse.dropStudent(newStudent);

            //Check that enrollment has changed
            assertTrue(enrollmentChange1);

            //check student is no longer in course
            assertFalse(newCourse.hasStudent(newStudent));

            //check that students credits have been adjusted
            assertEquals(0, newStudent.credits());
        }
        //Test number 2
        {
            Course psgTraining = new Course("Math", 4,
                    "Curan", "Bailey 101", 10, 30, 60);

            Student Student1 = new Student("Kylian", "Mbappe");
            Student Student2 = new Student("Lionel", "Messi");

            //Enroll student 1 into course
            boolean enrollmentChanged = psgTraining.enrollStudent(Student1);

            //Attempt to drop student 2 from course even though not yet enrolled
            boolean droppedStudent = psgTraining.dropStudent(Student2);

            //Check that enrollment has changed
            assertTrue(enrollmentChanged);

            //Check that student 1 is still enrolled
            assertTrue(psgTraining.hasStudent(Student1));

            //Check credits for student 1 has not changed
            assertEquals(4, Student1.credits());

            //Check credits for student 2 has not changed
            assertEquals(0, Student2.credits());
        }
    }

    //Test cases for method formatStudents
    @Test
    void testFormatStudents() {
        //Scenario 1
        {
            //Create new course
            Course newCourse1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            //New Student
            Student newStudent1 = new Student("Tony", "Kariuki");
            Student newStudent2 = new Student("Albert", "Einstein");

            //Enroll Student into course
            newCourse1.enrollStudent(newStudent1);
            newCourse1.enrollStudent(newStudent2);

            assertEquals("{Tony Kariuki, Albert Einstein}", newCourse1.formatStudents());
        }
        //Test Scenario 2
        {
            //Create a new test course
            Course newCourse2 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            //New Student
            Student newStudent1 = new Student("Tony", "Kariuki");
            Student newStudent2 = new Student("Albert", "Einstein");

            //Enroll Student into course
            newCourse2.enrollStudent(newStudent1);
            newCourse2.enrollStudent(newStudent2);

            //Remove student 1 from course
            boolean dropStudent1 = newCourse2.dropStudent(newStudent1);

            assertEquals("{Albert Einstein}", newCourse2.formatStudents());
        }

        //Test case scenario when course has no students
        {
            //Create a new test course
            Course newCourse3 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 10, 30, 60);

            //New Student
            Student newStudent = new Student("Tony", "Kariuki");

            assertEquals("{}", newCourse3.formatStudents());

        }


    }

}



