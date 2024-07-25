package cs2110;

/*
 * Assignment metadata
 * Name and NetID: Tony Kariuki akk85
 * Hours spent on assignment: Less than 2hrs
 */


/**
 * Collection of misc. static functions for showcasing the capabilities of Java in a procedural
 * context.
 */
public class A1 {

    /**
     * Return the area of a regular polygon with `nSides` sides of length `sideLength`. Units of
     * result are the square of the units of `sideLength`. Requires `nSides` is at least 3,
     * `sideLength` is non-negative.
     */
    public static double polygonArea(int nSides, double sideLength) {
        // TODO: Implement this method according to its specifications.

        double Area;
        return 0.25 * sideLength * sideLength * nSides / Math.tan(Math.PI / nSides);

    }

    /**
     * Return the next term in the Collatz sequence after the argument.  If the argument is even,
     * the next term is it divided by 2.  If the argument is odd, the next term is 3 times it plus
     * 1.  Requires magnitude of odd `x` is less than `Integer.MAX_VALUE/3` (otherwise overflow is
     * possible).
     */
    public static int nextCollatz(int number) {
        if (number % 2 == 0) {
            return number / 2;
        } else {
            return number * 3 + 1;
        }

    }

    // int argument and returns an int.

    /**
     * Return the sum of the Collatz sequence starting at `seed` and ending at 1 (inclusive).
     * Requires `seed` is positive, sum does not overflow.
     */
    public static int collatzSum(int seed) {
        // Implementation constraint: Use a while-loop.  Call `nextCollatz()` to
        // advance the sequence.
        int sum = seed;

        while (seed != 1) {
            seed = nextCollatz(seed);
            sum = sum + seed;
        }
        return sum;

    }

    /**
     * Return the median value among `{a, b, c}`.  The median has the property that at least half of
     * the elements are less than or equal to it and at least half of the elements are greater than
     * or equal to it.
     */
    public static int med3(int a, int b, int c) {
        // Implementation constraint: Do not call any other methods.
        if ((a >= b && a <= c) || (a <= b && a >= c)) {
            return a;

        } else if ((b >= a && b <= c) || (b <= a && b >= c)) {
            return b;

        } else {
            return c;
        }

    }

    /**
     * Return whether the closed intervals `[lo1, hi1]` and `[lo2, hi2]` overlap.  Two intervals
     * overlap if there exists a number contained in both of them.  Notation: the interval `[lo,
     * hi]` contains all numbers greater than or equal to `lo` and less than or equal to `hi`.
     * Requires `lo1` is less than or equal to `hi1` and `lo2` is less than or equal to `hi2`.
     */
    public static boolean intervalsOverlap(int lo1, int hi1, int lo2, int hi2) {
        // Implementation constraint: Use a single return statement to return
        // the value of a Boolean expression; do not use an if-statement.
        return lo1 <= hi2 && hi1 >= lo2;

    }

    /**
     * Return the approximation of pi computed from the sum of the first `nTerms` terms of the
     * Madhava-Leibniz series.  This formula states that pi/4 = 1 - 1/3 + 1/5 - 1/7 + 1/9 - ...
     * Requires `nTerms` is non-negative.
     */
    public static double estimatePi(int nTerms) {
        // Implementation constraint: Use a for-loop.  Do not call any other
        // methods (including `Math.pow()`).
        double PI = 0.0;

        for (int n = 0; n <= nTerms - 1; n++) {
            double termValue = 1.0 / (2 * n + 1);

            if (n % 2 == 0) {
                PI += termValue;
            } else {
                PI -= termValue;
            }
        }

        return PI *= 4;


    }

    /**
     * Returns whether the sequence of characters in `s` is equal (case-sensitive) to that sequence
     * in reverse order.
     */
    public static boolean isPalindrome(String s) {
        int length = s.length();
        for (int i = 0; i < length; i++) {
            if (s.charAt(i) != s.charAt(length - 1 - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Return an order confirmation message in English containing the order ID and the number of
     * items it contains.  Message shall handle item plurality properly (e.g. "1 item" vs. "3
     * items") and shall surround the order ID in single quotes. Examples:
     * <pre>
     * formatConfirmation("123ABC", 1) should return
     *   "Order '123ABC' contains 1 item."
     * formatConfirmation("XYZ-999", 3)" should return
     *   "Order 'XYZ-999' contains 3 items."
     * </pre>
     * Requires `orderId` only contains digits, hyphens, or letters 'A' - 'Z'; `itemCount` is
     * non-negative.
     */
    public static String formatConfirmation(String orderId, int itemCount) {
        // Implementation constraint: Use Java's ternary operator (`?:`) to give "item" the
        // appropriate plurality.
        String pluralItem = (itemCount == 1) ? "item" : "items";

        return "Order '" + orderId + "' contains " + itemCount + ' ' + pluralItem + '.';

    }

    /**
     * This main method demonstrates the use of the various methods above in A1 class and their use
     * to perform creative calculations and produce an entertaining result. The program computes the
     * sum of three Collatz sequences with seeds of 10, 20 and 30, finds the median of the sums, and
     * uses the median as the number of sides of a polygon. It then uses an estimate of pi as the
     * length of the polygon's sides and calculates the area of the polygon. The program checks if
     * the polygon's area is a palindrome and constructs a final message that includes the
     * calculated values and a playful note about the palindrome.
     */


    public static void main(String[] args) {
        //Compute the sums of the three Collatz sequence
        int collatzSum1 = collatzSum(10);
        int collatzSum2 = collatzSum(20);
        int collatzSum3 = collatzSum(30);

        // Find the median of the sum and use the median as the number of sides of a polygon
        int nSides = med3(collatzSum1, collatzSum2, collatzSum3);

        // Use estimate of pi as the length of the polygon sides
        double sideLength = estimatePi(1000);

        //Calculate and print the area of the polygon.
        double polygonArea = polygonArea(nSides, sideLength);

        //Check if the polygon's area is a palindrome.
        boolean isPalindromeArea = isPalindrome(String.valueOf(polygonArea));

        //Construct the final message
        String message = "The area of a polygon with " + nSides + " sides of length "
                + sideLength + " m is " + polygonArea + " m^2. ";

        if (isPalindromeArea) {
            message += " The area happens to be a palindrome.";
        } else {
            message += " The area happens not to be a palindrome.";
        }

        System.out.println(message);

    }
}
