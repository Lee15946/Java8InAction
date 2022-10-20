package code.chapter12;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

public class MyDate {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2014, 3, 18);
        System.out.println(date.get(ChronoField.YEAR));
        System.out.println(date.getDayOfWeek());
        System.out.println(date.get(ChronoField.MONTH_OF_YEAR));
        final var localTime = LocalTime.of(13, 45, 20);
        System.out.println(localTime.getHour());
        System.out.println(LocalDate.parse("2014-03-18"));
    }
}
