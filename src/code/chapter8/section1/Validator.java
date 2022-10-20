package code.chapter8.section1;

public class Validator {
    private final ValidationStrategy validationStrategy;

    public Validator(ValidationStrategy validationStrategy) {
        this.validationStrategy = validationStrategy;
    }
    public boolean validate(String s){
        return validationStrategy.execute(s);
    }
}
