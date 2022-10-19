package code.chapter5;

import code.chapter4.Dish;

import java.util.Arrays;
import java.util.List;

import static code.chapter4.Util.MENU;

public class Util {
    public static void main(String[] args) {
        //Filtering with a predicate
        System.out.println(MENU.stream()
                .filter(Dish::isVegetarian)
                .toList());

        //Filtering unique elements
        final var numbers = List.of(1, 2, 1, 3, 3, 2, 4);
        System.out.println(numbers.stream()
                .filter(i -> i % 2 == 0)
                .distinct()
                .toList());

        //Truncating a stream
        System.out.println(MENU.stream()
                .filter(dish -> dish.getCalories() > 300)
                .limit(3)
                .toList());

        //Skipping elements
        System.out.println(MENU.stream()
                .filter(dish -> dish.getCalories() > 300)
                .skip(2)
                .toList());

        //Applying a function to each element of a stream
        System.out.println(MENU.stream()
                .map(Dish::getName)
                .map(String::length)
                .toList());

        //Flattening streams
        final var words = List.of("Hello", "World");
        System.out.println(words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .toList());

        final var numbers1 = List.of(1, 2, 3);
        final var numbers2 = List.of(4, 5);
        final var pairs = numbers1.stream()
                .flatMap(i -> numbers2.stream()
                        .map(j -> new int[]{i, j}))
                .toList();
        pairs.stream().map(Arrays::toString).forEach(System.out::println);
        final var pairs2 = numbers1.stream().flatMap(i -> numbers2.stream()
                        .filter(j -> (i + j) % 3 == 0)
                        .map(j -> new int[]{i, j}))
                .toList();
        pairs2.stream().map(Arrays::toString).forEach(System.out::println);

        //Checking to see if a predicate matches at least one element
        if (MENU.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("The menu is (somewhat) vegetarian friendly!");
        }

        //Checking to see if a predicate matches all elements
        System.out.println(MENU.stream().allMatch(dish -> dish.getCalories() < 1000));

        //Checking to see if a predicate matches none elements
        System.out.println(MENU.stream().noneMatch(dish -> dish.getCalories() >= 1000));

        //Finding an element
        MENU.stream().filter(Dish::isVegetarian).findAny().ifPresent(System.out::println);

        //Finding the first element
        //More limitation on parallel
        final var someNumbers = Arrays.asList(1, 2, 3, 4, 5);
        someNumbers.stream().map(x -> x * x).filter(x -> x % 3 == 0).findFirst().ifPresent(System.out::println);

        //Reducing
        //Summing the elements
        System.out.println(numbers1.stream().reduce(0, Integer::sum));
        numbers1.stream().reduce(Integer::sum).ifPresent(System.out::println);
        System.out.println(numbers1.stream().reduce(1, (x, y) -> x * y));

        //Maximum and minimum
        System.out.println(numbers1.stream().reduce(Integer::max));
        System.out.println(numbers1.stream().reduce(Integer::min));

    }
}
