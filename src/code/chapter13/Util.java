package code.chapter13;

import java.util.List;

import static code.chapter11.Util.MILLISECONDS;
import static code.chapter11.Util.calculateRetrievalTime;
import static code.chapter13.Recursion.factorialIterative;
import static code.chapter13.Recursion.factorialRecursive;
import static code.chapter13.Recursion.factorialStream;
import static code.chapter13.Recursion.factorialTailRecursive;

public class Util {
    private Util() {
    }

    public static void useListSplitter() {
        final var list = List.of(1, 2, 3);
        System.out.println(ListSplitter.subsets(list));
    }

    public static void compareIterationWithRecursion() {
        var start = System.nanoTime();
        System.out.println("Result of factorialIterative is " + factorialIterative(10));
        System.out.println("Duration is " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        System.out.println("Result of factorialRecursive is " + factorialRecursive(10));
        System.out.println("Duration is " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        System.out.println("Result of factorialStream is " + factorialStream(10));
        System.out.println("Duration is " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        System.out.println("Result of factorialTailRecursive is " + factorialTailRecursive(10));
        System.out.println("Duration is " + calculateRetrievalTime(start) + MILLISECONDS);
    }
}
