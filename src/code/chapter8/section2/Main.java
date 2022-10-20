package code.chapter8.section2;

public class Main {
    public static void main(String[] args) {
        //Observer pattern
        Feed feed = new Feed();
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new Guardian());
        feed.registerObserver(new LeMonde());
        feed.registerObserver(tweet -> {
            if (tweet != null && tweet.contains("book")) {
                System.out.println("Recommend book is: " + tweet);
            }
        });
        feed.notifyObserver("The queen said her favorite book is Java 8 in Action!");

    }
}
