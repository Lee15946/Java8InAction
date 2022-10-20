package code.chapter6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.IntStream;

import static java.lang.Boolean.TRUE;

public class PrimeCollector {
    public static void main(String[] args) {
        System.out.println(partitionPrimesWithCustomCollector(20));
    }

    public static Map<Boolean, List<Integer>> partitionPrimesWithCustomCollector(int n) {
        return IntStream.rangeClosed(2, n).boxed().collect(new PrimeNumbersCollector());
    }

    public static class PrimeNumbersCollector implements java.util.stream.Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {
        @Override
        public Supplier<Map<Boolean, List<Integer>>> supplier() {
            return () -> {
                final var result = new HashMap<Boolean, List<Integer>>();
                result.put(true, new ArrayList<>());
                result.put(false, new ArrayList<>());
                return result;
            };
        }

        @Override
        public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
            return (Map<Boolean, List<Integer>> acc, Integer candidate) -> acc.get(isPrime(acc.get(TRUE), candidate)).add(candidate);
        }

        @Override
        public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
            return (Map<Boolean, List<Integer>> a, Map<Boolean, List<Integer>> b) -> {
                a.put(true, b.get(true));
                a.put(false, b.get(false));
                return a;
            };
        }

        @Override
        public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        }

    }
    public static boolean isPrime(List<Integer> primes, int candidate) {
        int candidateRoot = (int) Math.sqrt(candidate);
        return takeWhile(primes, i -> i <= candidateRoot).stream().noneMatch(p -> candidate % p == 0);
    }

    public static <A> List<A> takeWhile(List<A> list, Predicate<A> p) {
        int i = 0;
        for (A item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

}
