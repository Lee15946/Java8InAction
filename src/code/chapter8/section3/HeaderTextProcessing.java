package code.chapter8.section3;

public class HeaderTextProcessing extends ProcessingObject<String>{
    @Override
    protected String handleWork(String input) {
        return "From Jason Lee: " + input;
    }
}
