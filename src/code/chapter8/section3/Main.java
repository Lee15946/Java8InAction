package code.chapter8.section3;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Main {
    public static void main(String[] args) {
        //Chain of responsibility pattern
        final var p1 = new HeaderTextProcessing();
        final var p2 = new SpellCheckProcessing();
        p1.setSuccessor(p2);
        final var content = "Study labda now";
        final var result = p1.handle(content);

        System.out.println(result);

        UnaryOperator<String> headerProcessing = (String text) -> "From Jason Lee: " + text;
        UnaryOperator<String> spellCheckProcessing =
                (String input) -> input.replace("labda", "lambda");
        Function<String, String> pipeline = headerProcessing.andThen(spellCheckProcessing);

        System.out.println(pipeline.apply(content));

    }
}
