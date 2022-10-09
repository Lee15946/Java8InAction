package code.chapter14;

import java.util.function.DoubleUnaryOperator;

public class Converter {
    private Converter() {
    }

    /**
     * An example of Curring, make method more dynamic, and partially applied
     */
    static DoubleUnaryOperator curriedConverter(double f, double b) {
        return (double x) -> x * f + b;
    }

    //Celsius to Fahrenheit
    static DoubleUnaryOperator converterCtof = curriedConverter(9.0 / 5, 32);
    //Dollar to Pound
    static DoubleUnaryOperator converterUSDtoGBP = curriedConverter(0.6, 0);
    // Kilometer to Mile
    static DoubleUnaryOperator converterKmtoMi = curriedConverter(0.6214, 0);

    public static void useCurriedConverter() {
        double temperature = converterCtof.applyAsDouble(100);
        double gbp = converterUSDtoGBP.applyAsDouble(1000);
        double miles = converterKmtoMi.applyAsDouble(100);

        System.out.println("100 degrees Celsius equals " + temperature + " degrees Fahrenheit");
        System.out.println("100 USD equals " + gbp + " GBP");
        System.out.println("100 kilometers equals " + miles + " miles");
    }
}
