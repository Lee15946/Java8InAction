package code.chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class AppleFilter {
    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(
                new Apple(80, "green"),
                new Apple(155, "green"),
                new Apple(130, "red"),
                new Apple(200, "green"),
                new Apple(340, "red"),
                new Apple(160, "red"));
        System.out.println(filterGreenApple(inventory));
        System.out.println(filterApplesByColor(inventory, "red"));
        System.out.println(filterApplesByWeight(inventory, 150));
        System.out.println(filterApples(inventory, "green", 0, true));
        System.out.println(filterApples(inventory, "", 150, false));
        System.out.println(filterApples(inventory, new AppleGreenColorPredicate()));
        System.out.println(filterApples(inventory, new AppleHeavyWeightPredicate()));
        System.out.println(filterApples(inventory, new AppleRedHeavyPredicate()));
        final var redApples = filterApples(inventory, new ApplePredicate() {
            @Override
            public boolean test(Apple apple) {
                return "red".equals(apple.getColor());
            }
        });
        System.out.println(redApples);

        final var greenApples = filterApples(inventory, apple -> "green".equals(apple.getColor()));
        System.out.println(greenApples);

        List<Apple> heavyApples = myFilter(inventory, apple -> apple.getWeight() > 150);
        System.out.println(heavyApples);
        final var integers = Stream.iterate(0, i -> i + 1).limit(20).toList();
        List<Integer> evenNumbers = myFilter(integers, (Integer i) -> i % 2 == 0);
        System.out.println(evenNumbers);

        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);

        Thread t = new Thread(() -> System.out.println("Hello world!"));
        t.start();
    }

    public static List<Apple> filterGreenApple(List<Apple> inventory) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByColor(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApplesByWeight(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, String color, int weight, boolean flag) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if ((flag && apple.getColor().equals(color)) || (!flag && apple.getWeight() > weight)) {
                result.add(apple);

            }
        }
        return result;
    }

    public static List<Apple> filterApples(List<Apple> inventory, ApplePredicate applePredicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (applePredicate.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }

    public static class AppleHeavyWeightPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return apple.getWeight() > 150;
        }
    }

    public static class AppleGreenColorPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "green".equals(apple.getColor());
        }
    }

    public static class AppleRedHeavyPredicate implements ApplePredicate {
        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }

    public static <T> List<T> myFilter(List<T> list, MyPredicate<T> p) {
        List<T> result = new ArrayList<>();
        for (T item : list) {
            if (p.test(item)) {
                result.add(item);
            }
        }
        return result;
    }
}
