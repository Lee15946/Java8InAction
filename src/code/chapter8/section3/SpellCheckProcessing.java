package code.chapter8.section3;

public class SpellCheckProcessing extends ProcessingObject<String>{
    @Override
    protected String handleWork(String input) {
        return input.replace("labda", "lambda");
    }
}
