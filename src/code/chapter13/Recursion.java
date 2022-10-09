package code.chapter13;

import java.util.stream.LongStream;

public class Recursion {

    private Recursion() {
    }

    static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    /**
     * Need to be alert that using recursion may cause stack overflow,
     * because every middle value is stored in stack frame， and will consume memory very soon.
     * To avoid this problem, see factorialTailRecursive function
     */
    static long factorialRecursive(long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }

    static long factorialStream(long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (long a, long b) -> a * b);
    }

    /**
     * Store the middle value in parameter instead of in stack frame to avoid potential stack overflow
     */
    static long factorialTailRecursive(long n) {
        return factorialHelper(1L, n);
    }

    private static long factorialHelper(long acc, long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

}
