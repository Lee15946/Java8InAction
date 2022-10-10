package code.chapter14.section3;

public class Util {
    public static void main(String[] args) {
        MyList<Integer> list =
                new MyLinkedList<>(5, new MyLinkedList<>(10, new Empty<>()));
        System.out.println("This Linked list is " + list);

        LazyList<Integer> numbers = from(2);
        int two = numbers.head();
        int three = numbers.tail().head();
        int four = numbers.tail().tail().head();
        System.out.println("This Lazy list is " + two + " " + three + " " + four);
    }

    public static LazyList<Integer> from(int n) {
        return new LazyList<>(n, () -> from(n + 1));
    }
}
