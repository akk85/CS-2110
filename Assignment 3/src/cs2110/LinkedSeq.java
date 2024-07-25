package cs2110;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Assignment metadata
 * Name(s) and NetID(s): Tony Kariuki(akk85) and Kofi Ohene Adu (kao65)
 * Hours spent on assignment: 30hrs
 */

/**
 * A list of elements of type `T` implemented as a singly linked list.  Null elements are not
 * allowed.
 */
public class LinkedSeq<T> implements Seq<T> {

    /**
     * Number of elements in the list.  Equal to the number of linked nodes reachable from `head`.
     */
    private int size;

    /**
     * First node of the linked list (null if list is empty).
     */
    private Node<T> head;

    /**
     * Last node of the linked list starting at `head` (null if list is empty).  Next node must be
     * null.
     */
    private Node<T> tail;

    /**
     * Assert that this object satisfies its class invariants.
     */
    private void assertInv() {
        assert size >= 0;
        if (size == 0) {
            assert head == null;
            assert tail == null;
        } else {
            assert head != null;
            assert tail != null;

        }
        Node<T> checkHead = head;
        int numberOfLinkedNodes = 0;
        while (checkHead != null) {
            numberOfLinkedNodes++;
            assert checkHead.next() != null || checkHead == tail;
            checkHead = checkHead.next();


        }
        assert numberOfLinkedNodes == size;


    }

    /**
     * Create an empty list.
     */
    public LinkedSeq() {
        size = 0;
        head = null;
        tail = null;

        assertInv();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void prepend(T elem) {
        assertInv();
        assert elem != null;

        head = new Node<>(elem, head);
        // If list was empty, assign tail as well
        if (tail == null) {
            tail = head;
        }
        size += 1;

        assertInv();
    }

    /**
     * Return a text representation of this list with the following format: the string starts with
     * '[' and ends with ']'.  In between are the string representations of each element, in
     * sequence order, separated by ", ".
     * <p>
     * Example: a list containing 4 7 8 in that order would be represented by "[4, 7, 8]".
     * <p>
     * Example: a list containing two empty strings would be represented by "[, ]".
     * <p>
     * The string representations of elements may contain the characters '[', ',', and ']'; these
     * are not treated specially.
     */
    @Override
    public String toString() {
        assertInv();
        String str = "[";
        //Loop through the whole linked list
        Node<T> currentNode = head;
        while (currentNode != null) {

            //Append the data of the current node to the string
            str = str + currentNode.data();

            //If there's another node, add a comma and space for separation
            if (currentNode.next() != null) {
                str += ", ";
            }
            //Move to the next node in the linked list
            currentNode = currentNode.next();

        }
        //Close the string representation of the String.
        str += "]";

        return str;

    }


    @Override
    public boolean contains(T elem) {
        assertInv();
        if (elem == null) {
            return false;
        }
        Node<T> current = head;
        while (current != null) {
            if (current.data().equals(elem)) {
                return true;
            }
            current = current.next();
        }
        return false;

    }

    @Override
    public T get(int index) {
        assertInv();

        if (index < 0 || index >= size) { // Handle Index out of bounds
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds!");
        }
        Node<T> current = head;
        for (int count = 0; count < index; count++) {
            current = current.next();
        }

        return current.data();
    }

    @Override
    public void append(T elem) {
        assertInv();
        assert elem != null;

        Node<T> newNode = new Node<>(elem, null);
        //I referenced next node as null since our new node is appended to end of list
        // If list was empty, assign head to point to tail.
        if (head == null) {
            head = newNode;
            tail = newNode;
            //If list is not empty
        } else {
            //set current tail's next reference to the new node
            tail.setNext(newNode);
            tail = newNode;
        }
        size += 1;

        assertInv();

    }

    @Override
    public void insertBefore(T elem, T successor) {
        assertInv();
        assert elem != null;

        assert successor != null;

        //If list is of size 1, head  = successor
        if (head.data().equals(successor)) {
            head = new Node<T>(elem, head);
            size++;
            return;
        }
        Node<T> current = head;
        Node<T> previous = null;

        while (current != null && !current.data().equals(successor)) {
            previous = current;
            current = current.next();
        }

        if (current == null) {
            throw new NoSuchElementException("Successor has to be in the list");
        }

        assert previous != null;
        previous.setNext(new Node<>(elem, current));
        size++;

        assertInv();

    }

    @Override
    public boolean remove(T elem) {
        assertInv();
        assert elem != null;

        //Empty list
        if (head == null) {
            return false;
        }

        //if elem == head
        if (elem.equals(head.data())) {
            head = head.next();
            //if list has only one element
            if (head == null) {
                tail = null;
            }
            size--;
            return true;
        }

        Node<T> current = head;
        Node<T> previous = null;

        while (current != null && !current.data().equals(elem)) {
            previous = current;
            current = current.next();

        }
        // element was found in the list
        if (current == null) {
            return false;
        }
        // Link the previous node to the node after current

        assert previous != null;
        previous.setNext(current.next());

        // if we removed the last element
        if (current == tail) {
            tail = previous;
        }
        size--;
        return true;


    }

    /**
     * Return whether this and `other` are `LinkedSeq`s containing the same elements in the same
     * order.  Two elements `e1` and `e2` are "the same" if `e1.equals(e2)`.  Note that `LinkedSeq`
     * is mutable, so equivalence between two objects may change over time.  See `Object.equals()`
     * for additional guarantees.
     */
    @Override
    public boolean equals(Object other) {
        // Note: In the `instanceof` check, we write `LinkedSeq` instead of `LinkedSeq<T>` because
        // of a limitation inherent in Java generics: it is not possible to check at run-time
        // what the specific type `T` is.  So instead we check a weaker property, namely,
        // that `other` is some (unknown) instantiation of `LinkedSeq`.  As a result, the static
        // type returned by `currNodeOther.data()` is `Object`.
        assertInv();
        if (!(other instanceof LinkedSeq)) {
            return false;
        }
        LinkedSeq otherSeq = (LinkedSeq) other;
        Node<T> currNodeThis = head;
        Node currNodeOther = otherSeq.head;
        while (currNodeThis != null && currNodeOther != null) {
            if (!currNodeThis.data().equals(currNodeOther.data())) {
                return false;
            }
            currNodeThis = currNodeThis.next();
            currNodeOther = currNodeOther.next();
        }

        return currNodeThis == null && currNodeOther == null;
    }

    /*
     * There is no need to read the remainder of this file for the purpose of completing the
     * assignment.  We have not yet covered the implementation of these concepts in class.
     */

    /**
     * Returns a hash code value for the object.  See `Object.hashCode()` for additional
     * guarantees.
     */
    @Override
    public int hashCode() {
        // Whenever overriding `equals()`, must also override `hashCode()` to be consistent.
        // This hash recipe is recommended in _Effective Java_ (Joshua Bloch, 2008).
        int hash = 1;
        for (T e : this) {
            hash = 31 * hash + e.hashCode();
        }
        return hash;
    }

    /**
     * Return an iterator over the elements of this list (in sequence order).  By implementing
     * `Iterable`, clients can use Java's "enhanced for-loops" to iterate over the elements of the
     * list.  Requires that the list not be mutated while the iterator is in use.
     */
    @Override
    public Iterator<T> iterator() {
        assertInv();

        // Return an instance of an anonymous inner class implementing the Iterator interface.
        // For convenience, this uses Java features that have not eyt been introduced in the course.
        return new Iterator<>() {
            private Node<T> next = head;

            public T next() throws NoSuchElementException {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T result = next.data();
                next = next.next();
                return result;
            }

            public boolean hasNext() {
                return next != null;
            }
        };
    }
}
