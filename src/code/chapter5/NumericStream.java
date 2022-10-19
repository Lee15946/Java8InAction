package code.chapter5;

import code.chapter4.Dish;

import java.util.stream.IntStream;

import static code.chapter4.Util.MENU;

public class NumericStream {
    public static void main(String[] args) {

        //Mapping to a numeric stream
        final var intStream = MENU.stream().mapToInt(Dish::getCalories);
        System.out.println(intStream.sum());
        //Converting back to a stream of objects
        final var stream = MENU.stream().mapToInt(Dish::getCalories).boxed();
        System.out.println(stream.toList());
        //Default values: OptionalInt
        final var maxCalories = MENU.stream().mapToInt(Dish::getCalories).max();
        System.out.println(maxCalories.orElse(0));

        //Numeric ranges
        final var evenNumbers = IntStream.rangeClosed(1, 100).filter(i -> i % 2 == 0);
        System.out.println(evenNumbers.count());

        //Pythagorean triple
        IntStream.rangeClosed(1, 100).boxed()
                .flatMap(a ->
                        IntStream.rangeClosed(a, 100)
                                .mapToObj(
                                        b -> new double[]{a, b, Math.sqrt((double) a * a + b * b)})
                                .filter(t -> t[2] % 1 == 0)
                ).limit(5).forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
    }
}
