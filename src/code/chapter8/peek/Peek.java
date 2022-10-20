package code.chapter8.peek;

import java.util.List;

public class Peek {
    public static void main(String[] args) {
        final var numbers = List.of(1, 2, 3, 4, 5);

        final var result = numbers.stream()
                .peek(x -> System.out.println("from stream: " + x))
                .map(x -> x + 17)
                .peek(x -> System.out.println("after map: " + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter: " + x))
                .limit(3)
                .peek(x -> System.out.println("after limit:" + x))
                .toList();
        System.out.println(result);

    }
}
