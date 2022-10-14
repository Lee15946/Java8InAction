package code.chapter1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        System.out.println(filterApples(inventory,new AppleGreenColorPredicate()));
        System.out.println(filterApples(inventory,new AppleHeavyWeightPredicate()));
        System.out.println(filterApples(inventory,new AppleRedHeavyPredicate()));
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

    public static class AppleRedHeavyPredicate implements ApplePredicate{
        @Override
        public boolean test(Apple apple) {
            return "red".equals(apple.getColor()) && apple.getWeight() > 150;
        }
    }
}
