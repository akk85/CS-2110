package cs2110;

/**
 * A mutable set of students.
 */
public class StudentSet {
    // Implementation: the StudentSet is implemented as a resizable array data structure.
    // Implementation constraint: do not use any classes from java.util.
    // Implementation constraint: assert preconditions for all method parameters and assert that the
    // invariant is satisfied at least at the end of every method that mutates any fields.

    /**
     * Array containing the students in the set.  Elements `store[0..size-1]` contain the distinct
     * students in this set, none of which are null.  All elements in `store[size..]` are null.  The
     * length of `store` is the current capacity of the data structure and is at least 1.  Two
     * students `s1` and `s2` are distinct if `s1.equals(s2)` is false.
     */
    private Student[] store;

    /**
     * The number of distinct students in this set.  Non-negative and no greater than
     * `store.length`.
     */
    private int size;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {

        assert store != null && store.length > 0;
        assert size >= 0 && size <= store.length;

        //Elements `store[0...size-1]` contain the distinct * students in this set, none of which are null.
        for (int i = 0; i < size; ++i) {

            //Check that none of the students in the set is null
            assert store[i] != null;

            // Check that students are all distinct
            for (int j = i + 1; j < size; ++j) {
                assert !store[i].equals(store[j]);

            }
        }

        // Check that unused capacity is all null
        for (int i = size; i < store.length; ++i) {
            assert store[i] == null;
        }
    }

    /**
     * Create an empty set of students.
     */
    public StudentSet() {
        // You will need to decide on an initial capacity for your backing array (the capacity is
        // not observable by the client, so this is not constrained by the method spec).
        // The choice is a tradeoff between potentially wasted space vs. potentially needing to
        // resize the backing array sooner.  Choose something "small," say, less than 20 (the exact
        // value is up to you).  Don't forget to assert that invariants are satisfied (this is the
        // last time we'll remind you).

        int initialCapacity = 15;

        store = new Student[initialCapacity];

        assertInv();
    }

    /**
     * Return the number of students in this set.
     */
    public int size() {

        return size;

    }

    /**
     * Effect: Add student `s` to the set.  Requires `s` is not already in the set.
     */
    public void add(Student s) {
        // If the backing array runs out of space, create a new backing array with twice the
        // capacity and copy all elements from the old array to it.  Consider delegating this task
        // to a helper method.
        // We recommend proceeding as follows:
        // 1. Write a small test case to add a student and check the impact on the set's size.
        //    Confirm that it fails.
        // 2. Implement a basic version of `add()`, ignoring the resizing requirements above.
        //    Confirm that your test case passes.
        // 3. Add a larger test case that adds more students than your initial capacity (hint: use a
        //    loop).  Confirm that it fails.
        // 4. Implement the resizing logic for `add()`.  Confirm that your new test case passes.
        // If you're not sure how to check a precondition, leave yourself a
        // might be inspired by a later task.

        // Check if student is in set
        boolean studentInSet = false;
        for (int i = 0; i < size; i++) {
            if (store[i].equals(s)) {
                studentInSet = true;
                break;

            }
        }
        //If student not in set, add them to set
        if (!studentInSet) {
            if (size == store.length) {
                resizeBackingArray();
            }
            store[size] = s;
            size++;

            assertInv();
        }

    }

        //If the backing array runs out of space, create a new backing array with twice the
        //capacity and copy all elements from the old array to it.  Consider delegating this task
        //to a helper method.
        public void resizeBackingArray() {
        //New backing array with twice the capacity
        Student[] newStore = new Student[store.length * 2];

        //copy contents to new array
        for (int i = 0; i < size; i++) {
            newStore[i] = store[i];
        }
        store = newStore;
        assertInv();

    }

    /**
     * Return whether this set contains student `s`.
     */
    public boolean contains(Student s) {
        for (int i = 0; i < size; i++) {
            if (store[i].equals(s)) {
                return true;
            }
        }
        assertInv();
        return false;


    }

    /**
     * Effect: If student `s` is in this set, remove `s` from the set and return true. Otherwise
     * return false.
     */
    public boolean remove(Student s) {
        // You are welcome to decompose this task into operations that can be performed by
        // "helper methods", which you may define below.
        //Check if student is in set
        if (!contains(s)) {
            return false;
        }
        // find index of student s
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (store[i].equals(s)) {
                index = i;
                break;
            }
        }
        // remove `s` from the set and return true
        // We can remove student s by shifting
        for (int i = index; i < size; i++) {
            store[i] = store[i + 1];
        }
        //Set element to null and decrease the size by 1
        store[size - 1] = null;
        size--;

        assertInv();
        return true;

    }

    /**
     * Return the String representation of this StudentSet.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (int i = 0; i < size; ++i) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(store[i]);
        }
        sb.append("}");
        return sb.toString();
    }
}
