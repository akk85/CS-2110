package cs2110;

/**
 * A student tracked by the CMSμ course management system.
 */
public class Student {

    /**
     * First name of this Student (may not be empty or null).
     */
    private final String firstName;

    /**
     * Last name of this Student (may not be empty or null).
     */
    private final String lastName;

    /**
     * Number of credits student is currently enrolled in (integer; may not be negative).
     */
    private int numberOfCredits;

    /**
     * Class Invariant The 'firstName' field must not be null or empty. The 'lastName' field must
     * not be null or empty. The 'numberOfCredits' field must not be negative /*
     * <p>
     * /** Assert that this object satisfies its class invariants.
     */
    private void assertInv() {

        assert firstName != null && !firstName.isEmpty();
        assert lastName != null && !lastName.isEmpty();
        assert numberOfCredits >= 0;
    }

    /**
     * Create a new Student with first name `firstName` and last name `lastName` who is not enrolled
     * for any credits.  Requires firstName and lastName are not empty.
     */
    public Student(String firstName, String lastName) {
        // Assert that all preconditions are met.
        // Assert that the class invariant is satisfied before returning.
        // (These two assertions are not, in general, redundant - one pertains to arguments provided
        // by the client, while the other pertains to the work done by the constructor.)
        // Note: If a field's initial value is independent of constructor arguments, it is legal to
        // initialize it when it is declared (or even to rely on a default value), but prefer to
        // assign it in the constructor so that the whole state is initialized in one place.

        // Assert that all preconditions are met.

        assert !firstName.isEmpty();
        assert !lastName.isEmpty();

        //Constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.numberOfCredits = 0;

        assertInv();

    }

    /**
     * Return the first name of this Student.  Will not be empty.
     */
    public String firstName() {
        return firstName;
    }

    /**
     * Return the last name of this Student.  Will not be empty.
     */
    public String lastName() {

        return lastName;
    }

    /**
     * Return the full name of this student, formed by joining their first and last names separated
     * by a space.
     */
    public String fullName() {
        // Observe that, by invoking methods instead of referencing this fields, this method was
        // implemented without knowing how you will name your fields.
        assertInv();
        return firstName() + " " + lastName();


    }

    /**
     * Return the number of credits this student is currently enrolled in.  Will not be negative.
     */
    public int credits() {
        assert numberOfCredits >= 0;
        return numberOfCredits;
    }

    /**
     * Change the number of credits this student is enrolled in by `deltaCredits`. For example, if
     * this student were enrolled in 12 credits, then `this.adjustCredits(3)` would result in their
     * credits changing to 15, whereas `this.adjustCredits(-4)` would result in their credits
     * changing to 8.  Requires that the change would not cause the student's credits to become
     * negative.
     */
    void adjustCredits(int deltaCredits) {
        // This method has default visibility to prevent code in other packages from directly
        // adjusting a student's credits.
        // Assert that all preconditions are met.
        // Assert that the class invariant is satisfied before returning.

        assert (numberOfCredits + deltaCredits) >= 0;

        assertInv();
        numberOfCredits += deltaCredits;


    }

    /**
     * Return the full name of this student as its string representation.
     */
    @Override
    public String toString() {
        return fullName();
    }
}
