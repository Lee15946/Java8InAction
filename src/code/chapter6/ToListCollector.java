package code.chapter6;

import code.chapter5.Trader;
import code.chapter5.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static code.chapter4.Main.MENU;
import static code.chapter6.Collector.getTransactions;

public class ToListCollector {
    public static void main(String[] args) {

        System.out.println(myToList(getTransactions()));

        final var dishes = MENU.stream().collect(
                ArrayList::new,
                List::add,
                List::addAll
        );
        System.out.println(dishes);

    }


    public static List<Trader> myToList(
            List<Transaction> list) {
        return list.stream().map(Transaction::getTrader).collect(new MyToListCollector<>());
    }

    public static class MyToListCollector<T> implements java.util.stream.Collector<T, List<T>, List<T>> {

        //initialize
        @Override
        public Supplier<List<T>> supplier() {
            return ArrayList::new;
        }

        //accumulator
        @Override
        public BiConsumer<List<T>, T> accumulator() {

            return List::add;
        }

        @Override
        public BinaryOperator<List<T>> combiner() {
            return (list1, list2) -> {
                list1.addAll(list2);
                return list1;
            };
        }

        //result
        @Override
        public Function<List<T>, List<T>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
        }
    }
}
