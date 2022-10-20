package code.chapter8.section3;

public class Guardian implements Observer {
    @Override
    public void notify(String tweet) {
        if (tweet != null && tweet.contains("queen")) {
            System.out.println("Yet another news in London..." + tweet);
        }
    }
}
