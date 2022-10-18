package code.chapter4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class Util {
    public static void main(String[] args) {
        final var menu = List.of(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 200, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", false, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH)
        );
        List<Dish> lowCaloriesDishes = new ArrayList<>();
        for (Dish dish : menu) {
            if (dish.getCalories() < 400) {
                lowCaloriesDishes.add(dish);
            }
        }
        lowCaloriesDishes.sort(Comparator.comparing(Dish::getCalories));
        List<String> lowCaloricDishesName = new ArrayList<>();
        for (Dish dish : lowCaloriesDishes) {
            lowCaloricDishesName.add(dish.getName());
        }
        System.out.println(lowCaloricDishesName);

        final var newLowCaloriesDishesName = menu.parallelStream()
                .filter(dish -> dish.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .toList();
        System.out.println(newLowCaloriesDishesName);

        final var dishesByType = menu.stream().collect(groupingBy(Dish::getType));
        System.out.println(dishesByType);

        final var threeHighCaloricDishNames = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .toList();
        System.out.println(threeHighCaloricDishNames);

        List<String> namesByFor = new ArrayList<>();
        for (Dish dish : menu) {
            namesByFor.add(dish.getName());
        }

        List<String> namesByIterator = new ArrayList<>();
        Iterator<Dish> iterator = menu.iterator();
        while (iterator.hasNext()) {
            Dish d = iterator.next();
            namesByIterator.add(d.getName());

        }

        List<String> namesByStream = menu.stream().map(Dish::getName).toList();

        System.out.println(namesByFor);
        System.out.println(namesByIterator);
        System.out.println(namesByStream);

        List<String> names = menu.stream()
                .filter(dish -> {
                    System.out.println("filtering " + dish.getName());
                    return dish.getCalories() > 300;
                })
                .map(dish -> {
                    System.out.println("mapping " + dish.getName());
                    return dish.getName();
                }).limit(3)
                .toList();
        System.out.println(names);

    }

}
