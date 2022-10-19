package code.chapter5;

import java.util.Comparator;
import java.util.List;

public class Statistics {
    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");
        List<Transaction> transactions = List.of(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        System.out.println(filterAt2011(transactions));
        System.out.println(filterCity(transactions));
        System.out.println(filterCambridge(transactions));
        System.out.println(filterName(transactions));
        System.out.println(findMilan(transactions));
        System.out.println(sumValueInCambridge(transactions));
        System.out.println(findMax(transactions));
        System.out.println(findMin(transactions));


    }

    public static List<Transaction> filterAt2011(List<Transaction> transactions) {
        return transactions.stream().filter((transaction -> transaction.getYear() == 2011))
                .sorted(Comparator.comparing(Transaction::getValue)).toList();
    }

    public static List<String> filterCity(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> transaction.getTrader().getCity()).distinct().toList();
    }

    public static List<Trader> filterCambridge(List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge")).distinct()
                .sorted(Comparator.comparing(Trader::getName)).toList();
    }

    public static List<String> filterName(List<Transaction> transactions) {
        return transactions.stream().map(transaction -> transaction.getTrader().getName())
                .distinct().sorted().toList();
    }

    public static Boolean findMilan(List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getTrader).anyMatch(i -> i.getCity().equals("Milan"));
    }

    public static Integer sumValueInCambridge (List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue).reduce(Integer::sum).orElse(0);
    }

    public static Integer findMax(List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getValue).reduce(Integer::max).orElse(0);
    }

    public static Transaction findMin(List<Transaction> transactions) {
        return transactions.stream().min(Comparator.comparing(Transaction::getValue)).orElse(null);
    }
}
