package code.chapter6;


import code.chapter5.Trader;
import code.chapter5.Transaction;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.toCollection;

public class Collector {

    public static void main(String[] args) {

        final var transactions = getTransactions();

        System.out.println(transactions.stream().map(Transaction::getValue).reduce(Integer::sum).orElse(0));
        System.out.println(transactions.stream().mapToInt(Transaction::getValue).sum());
        System.out.println(transactions.stream().max(Comparator.comparingInt(Transaction::getValue)));

        String summary = transactions.stream().map(Transaction::getTrader).map(Trader::toString).reduce("", (i, j) -> i + j + ", ");

        enum Category {
            HIGH, MEDIUM, LOW
        }
        System.out.println(summary);
        System.out.println(transactions.stream().collect(
                groupingBy(Transaction::getTrader,
                        mapping(t -> {
                            if (t.getValue() >= 1000) return Category.HIGH;
                            return Category.LOW;
                        }, toCollection(HashSet::new))
                )));

        System.out.println(transactions.stream().collect(groupingBy(Transaction::getTrader, counting())));
        System.out.println(transactions.stream().collect(groupingBy(Transaction::getTrader, maxBy(Comparator.comparing(Transaction::getValue)))));

        System.out.println(transactions.stream().collect(Collectors.toMap(Transaction::getTrader, Function.identity(), BinaryOperator.maxBy(Comparator.comparing(Transaction::getValue)))));

        System.out.println(transactions.stream().collect(partitioningBy(transaction ->
                transaction.getValue() > 800)));
        System.out.println(transactions.stream().collect(partitioningBy(transaction ->
                transaction.getValue() > 800, collectingAndThen(maxBy(Comparator.comparing(Transaction::getValue)), Optional::get))));
        System.out.println(transactions.stream().collect(partitioningBy(transaction ->
                transaction.getValue() > 800, counting())));

    }

    public static List<Transaction> getTransactions() {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        return List.of(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }
}
