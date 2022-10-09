package code.chapter11;

import static code.chapter11.Util.delay;

public class Discount {
    public enum Code {

        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }
    public static String applyDiscount(Quote quote){
        return quote.shopName() + " price is " + apply(quote.price(), quote.discountCode());
    }

    public static int apply(double price,Code code){
        //Simulate response delay
        delay();
        return (int) (price * (100 - code.percentage) / 100);
    }
}
