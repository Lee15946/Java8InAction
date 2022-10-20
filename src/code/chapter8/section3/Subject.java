package code.chapter8.section3;

public interface Subject {
    void registerObserver(Observer observer);

    void notifyObserver(String tweet);
}
