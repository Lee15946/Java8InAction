package code.chapter14;

import java.util.stream.IntStream;

public class Prime {
    public static void main(String[] args) {
        IntStream numbers = numbers();
        int head = head(numbers);
    }
    static IntStream numbers() {
        return IntStream.iterate(2, n -> n + 1);
    }

    static int head(IntStream numbers) {
        return numbers.findFirst().orElse(2);
    }
    static IntStream tail(IntStream numbers){
        return numbers().skip(1);
    }
    static IntStream primes(IntStream stream){
        int head = head(numbers());
        return IntStream.concat(
                IntStream.of(head),
                primes(tail(numbers().filter(n -> n % head != 0)))
        );

    }}
