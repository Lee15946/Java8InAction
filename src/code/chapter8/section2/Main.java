package code.chapter8.section2;

public class Main {
    public static void main(String[] args) {
        //Strategy pattern
        final var numericValidator = new Validator(s -> s.matches("\\d+"));
        System.out.println(numericValidator.validate("abcd"));
        final var lowCaseValidator = new Validator(s -> s.matches("[a+z]+"));
        System.out.println(lowCaseValidator.validate("abcd"));
    }
}
