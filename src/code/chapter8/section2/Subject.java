package code.chapter8.section2;

public interface Subject {
    void registerObserver(Observer observer);

    void notifyObserver(String tweet);
}
