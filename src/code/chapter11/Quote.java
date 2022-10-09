package code.chapter11;

public record Quote(String shopName, double price, Discount.Code discountCode) {
    public static Quote parse(String s) {
        final var split = s.split(":");
        final var shopName = split[0];
        final var price = Double.parseDouble(split[1]);
        final var discountCode = Discount.Code.valueOf(split[2]);
        return new Quote(shopName, price, discountCode);
    }
}
