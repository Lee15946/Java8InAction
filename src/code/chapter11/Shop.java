package code.chapter11;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.random.RandomGenerator;

import static code.chapter11.Util.delay;
import static code.chapter11.Util.randomDelay;

public record Shop(String shopName) {

    double calculatePrice(String product) {
        //Simulate the I/O or network delay
        delay();
        return RandomGenerator.getDefault().nextDouble() * product.charAt(0) + product.charAt(1);
    }

    double calculatePriceWithRandomDelay(String product) {
        //Simulate the I/O or network, the delay time is random
        randomDelay();
        return RandomGenerator.getDefault().nextDouble() * product.charAt(0) + product.charAt(1);
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

    public String getPriceWithCodeByRandomDelay(String product) {
        final var price = calculatePriceWithRandomDelay(product);
        Discount.Code code = Discount.Code.values()[RandomGenerator.getDefault().nextInt(Discount.Code.values().length)];
        return String.format("%s:%.2f:%s", shopName, price, code);
    }
    /**
     * Get exchange rate between dollar and EUR
     */
    private double getExchangeRate() {
        //Simulate response delay
        delay();
        return 1.02;
    }

    public CompletableFuture<Double> getPriceAsEURByCompletableFuture(String product) {
        return CompletableFuture.supplyAsync(
                () -> getPriceSync(product)
        ).thenCombine(
                CompletableFuture.supplyAsync(this::getExchangeRate), (price, rate) -> price * rate
        );
    }

    public Future<Double> getPriceAsEURByFuture(String product) {
        final var executor = Executors.newCachedThreadPool();
        final var futureRate = executor.submit(this::getExchangeRate);
        return executor.submit(() -> {
            final var price = getPriceSync(product);
            return price * futureRate.get();
        });
    }
}
