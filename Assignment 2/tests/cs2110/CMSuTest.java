package cs2110;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CMSuTest {

    // Each method must be tested in at least two different scenarios.

    @Test
    void testHasConflicts() {
        //Test Scenario where the students two courses have conflicts
        {
            CMSu cms1 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 90);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course2.enrollStudent(student1);

            //Add Courses and students into CMSu
            cms1.addCourse(Course1);
            cms1.addCourse(Course2);
            cms1.addStudent(student1);

            //Check for Conflicts
            assertTrue(cms1.hasConflict(student1));

        }
        //Test Scenario where the students two courses have conflicts
        {
            CMSu cms1 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course2.enrollStudent(student1);

            //Add Courses and students into CMSu
            cms1.addCourse(Course1);
            cms1.addCourse(Course2);
            cms1.addStudent(student1);

            //Check for Conflicts
            assertFalse(cms1.hasConflict(student1));

        }
    }

    @Test
    void testCreditConsistency() {
        //Test scenario 1 when credits do indeed match
        {
            CMSu cms1 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course1.enrollStudent(student2);

            Course2.enrollStudent(student1);
            Course2.enrollStudent(student2);

            //Add Courses and students into CMSu
            cms1.addCourse(Course1);
            cms1.addCourse(Course2);
            cms1.addStudent(student1);
            cms1.addStudent(student2);

            //Test Case 1 for when credits do match
            boolean result1 = cms1.checkCreditConsistency();
            //Confirm students credits and CSMu credits are equal.
            assertTrue(result1);

        }
        //Test scenario case 2 when credits do not match
        {
            CMSu cms2 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course1.enrollStudent(student2);

            Course2.enrollStudent(student1);
            Course2.enrollStudent(student2);

            //Add Courses and students into CMSu
            cms2.addCourse(Course1);
            cms2.addCourse(Course2);
            cms2.addStudent(student1);
            cms2.addStudent(student2);

            //Adjust credits to not match
            student2.adjustCredits(2);

            //Confirm that students credits and those in CSMu are not equal since we adjusted the credits
            boolean result2 = cms2.checkCreditConsistency();
            assertFalse(result2);
        }
    }

    @Test
    void testAuditCredits() {
        //Test Scenario 1 when a student exceeds the credit limits.
        {
            CMSu cms1 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 3, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course2.enrollStudent(student1);
            Course2.enrollStudent(student2);

            //Add Courses and students into CMSu
            cms1.addCourse(Course1);
            cms1.addCourse(Course2);
            cms1.addStudent(student1);
            cms1.addStudent(student2);

            StudentSet creditlimitSet = cms1.auditCredits(6);

            //Verify that only student 1 is in the credit limit set
            assertEquals(1, creditlimitSet.size());
            assertTrue(creditlimitSet.contains(student1));
            assertFalse(creditlimitSet.contains(student2));

        }
        //Test for scenario when no students exceed the credit limit
        {
            CMSu cms2 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course2.enrollStudent(student1);
            Course2.enrollStudent(student2);

            //Add Courses and students into CMSu
            cms2.addCourse(Course1);
            cms2.addCourse(Course2);
            cms2.addStudent(student1);
            cms2.addStudent(student2);

            StudentSet creditlimitSet = cms2.auditCredits(9);

            //Verify that no student is in the credit limit set
            assertEquals(0, creditlimitSet.size());
            assertFalse(creditlimitSet.contains(student1));
            assertFalse(creditlimitSet.contains(student2));

        }
        //Test where the credit limit for students is zero
        {
            CMSu cms2 = new CMSu();

            //Creating new Courses
            Course Course1 = new Course("Object-Oriented Programming and Data Structures", 4,
                    "Muhlberger", "Bailey Hall 101", 8, 30, 60);

            Course Course2 = new Course("Discrete Math", 4, "Griezman", "Bailey Hall 101",
                    9, 30, 60);

            //Creating new Students
            Student student1 = new Student("Tony", "Kariuki");
            Student student2 = new Student("Albert", "Einstein");

            //Enroll students into courses
            Course1.enrollStudent(student1);
            Course2.enrollStudent(student1);
            Course2.enrollStudent(student2);

            //Add Courses and students into CMSu
            cms2.addCourse(Course1);
            cms2.addCourse(Course2);
            cms2.addStudent(student1);
            cms2.addStudent(student2);

            StudentSet creditlimitSet = cms2.auditCredits(0);

            //Verify that no student is in the credit limit set
            assertEquals(2, creditlimitSet.size());
            assertTrue(creditlimitSet.contains(student1));
            assertTrue(creditlimitSet.contains(student2));
        }

    }

}
