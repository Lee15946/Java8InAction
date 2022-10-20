package code.chapter3;

import code.chapter2.Apple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args) {
        final var list = List.of("lambdas", "in", "action");
        System.out.println(map(list, String::length));

        //No boxing
        IntPredicate evenNumbers = (int i) -> i % 2 == 0;
        System.out.println(evenNumbers.test(1000));
        System.out.println(evenNumbers.test(1001));
        //Boxing
        Predicate<Integer> oddNumbers = (Integer i) -> i % 2 == 1;
        System.out.println(oddNumbers.test(1000));
        System.out.println(oddNumbers.test(1001));

        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(130, "red"),
                new Apple(200, "green"),
                new Apple(340, "red"),
                new Apple(160, "red"));

        //Chaining Comparators
        inventory.sort(comparing(Apple::getWeight)
                .reversed()
                .thenComparing(Apple::getColor)
        );
        System.out.println(inventory);

        //Composing Predicates
        Predicate<Apple> redApple = a -> "red".equals(a.getColor());
        final var notRedApple = redApple.negate();
        final var redHeavyApple = redApple.and(a -> a.getWeight() > 150);
        final var redHeavyAppleOrGreenApple = redHeavyApple.or(apple -> "green".equals(apple.getColor()));
        System.out.println(inventory.stream().filter(redApple).toList());
        System.out.println(inventory.stream().filter(notRedApple).toList());
        System.out.println(inventory.stream().filter(redHeavyApple).toList());
        System.out.println(inventory.stream().filter(redHeavyAppleOrGreenApple).toList());

        Function<Integer,Integer> f = x -> x + 1;
        Function<Integer,Integer> g = x -> x * 2;
        final var h = f.andThen(g);
        final var h2 = f.compose(g);
        System.out.println(h.apply(1));
        System.out.println(h2.apply(1));

    }

    public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
        final var result = new ArrayList<R>();
        for (T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }
}
