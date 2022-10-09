package code.chapter11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;

public class Shop {
    private final String shopName;

    public Shop(String shopName) {
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    double calculatePrice(String product) {
        delay();
        return RandomGenerator.getDefault().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    //Simulate the I/O or network delay
    public static void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }
    }

    public double getPriceSync(String product) {
        return calculatePrice(product);
    }

    public Future<Double> getPriceAsync(String product) {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                // Will throw an ExecutionException, with the internal exact exception and failure reason
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;
    }

    /**
     * Use factory method in CompletableFuture, hand over to Executor in ForkJoinPool.
     * Alternatively, you can pass the second parameter to assign specific thread to execute the supply method
     */
    public Future<Double> getPriceAsyncByLambda(String product) {
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    public String getPriceWithCode(String product) {
        final var price = calculatePrice(product);
        Discount.Code code = Discount.Code.values()[RandomGenerator.getDefault().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", shopName, price, code);
    }
}
