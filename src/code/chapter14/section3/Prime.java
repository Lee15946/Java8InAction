package code.chapter14.section3;

import static code.chapter14.section3.Util.from;

public class Prime {
    public static void main(String[] args) {
        final var numbers = from(2);
        final var firstPrime = primes(numbers).head();
        final var secondPrime = primes(numbers).tail().head();
        final var thirdPrime = primes(numbers).tail().tail().head();
        System.out.println("First prime: " + firstPrime);
        System.out.println("Second prime: " + secondPrime);
        System.out.println("Third prime: " + thirdPrime);

        // This will run until a stackoverflow occur because Java does not
        // support tail call elimination
        printAll(primes(numbers));
    }

    static MyList<Integer> primes(MyList<Integer> numbers) {
        return new LazyList<>(
                numbers.head(),
                () -> primes(numbers.tail().filter(n -> n % numbers.head() != 0))
        );
    }

    static <T> void printAll(MyList<T> list) {
        while (!list.isEmpty()) {
            System.out.println(list.head());
            list = list.tail();
        }
    }
}
