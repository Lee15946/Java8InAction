package code.chapter14.section3;

import java.util.function.Predicate;

public interface MyList<T> {
    T head();

    MyList<T> tail();

    default boolean isEmpty() {
        return true;
    }

    default MyList<T> filter(Predicate<T> p) {
        return isEmpty() ?
                this :
                p.test(head()) ?
                        new LazyList<>(head(), () -> tail().filter(p)) :
                        tail().filter(p);
    }
}
