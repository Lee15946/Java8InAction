package code.chapter3;

import java.util.function.Function;

public class Letter {
    public static void main(String[] args) {
        Function<String, String> addHeader = Letter::addHeader;
        Function<String, String> addFooter = Letter::addFooter;
        Function<String, String> checkSpelling = Letter::checkSpelling;
        final var transFormation = addHeader.andThen(addFooter).andThen(checkSpelling);
        System.out.println(transFormation.apply("Hello, I'm new to lambda."));

    }
    public static String addHeader(String text){
        return "From Jason Lee: " + text;
    }
    public static String addFooter(String text){
        return text + " Kind regards";
    }
    public static String checkSpelling(String text){
        return text.replace("labda", "lambda");
    }
}
