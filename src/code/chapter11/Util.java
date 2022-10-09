package code.chapter11;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Util {
    private Util() {
    }
    public static final String MILLISECONDS = " msecs";
    public static final int CONVERSION_RATIO = 1_000_000;
    public static final String PRODUCT_NAME = "myPhone27s";

    private static final Shop shop = new Shop("BestShop");

    /**
     * The length of shops need to be double than the number of CPU core.
     * Because the Hyper-Thread is default enabled.
     */
    private static final List<Shop> shops = List.of(
            new Shop("BestPrice"), new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"), new Shop("BuyItAll"),
            new Shop("Apple"), new Shop("Amazon"),
            new Shop("Bottle"), new Shop("Ear"),
            new Shop("Phone"), new Shop("Case"),
            new Shop("Book"), new Shop("Link"),
            new Shop("Plug"));

    public static List<String> findPrices(String product) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPriceSync(product))).toList();
    }

    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.getShopName(), shop.getPriceSync(product))).toList();
    }

    public static List<String> findPricesAsync(String product) {
        final var executors = Executors.newFixedThreadPool(Math.min(shops.size(), 100), r -> {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        final var priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.getShopName(), shop.getPriceSync(product)), executors)).toList();
        return priceFutures.stream().map(CompletableFuture::join).toList();
    }

    private static long calculateRetrievalTime(long start) {
        return (System.nanoTime() - start) / CONVERSION_RATIO;
    }

    public static void compareSyncMethodWithAsyncMethod() {
        var start = System.nanoTime();
        final var priceSync = shop.getPriceSync(PRODUCT_NAME);
        System.out.printf("Sync Price is %.2f%n", priceSync);
        System.out.println("Sync Price returned after " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        final var priceFuture = shop.getPriceAsync(PRODUCT_NAME);
        System.out.println("Async Invocation returned after " + calculateRetrievalTime(start)+MILLISECONDS);
        try {
            double priceAsync = priceFuture.get();
            System.out.printf("Async Price is %.2f%n", priceAsync);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        System.out.println("Async Price returned after " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        final var priceLambdaFuture = shop.getPriceAsyncByLambda(PRODUCT_NAME);
        System.out.println("Async Lambda Invocation returned after " + calculateRetrievalTime(start)+MILLISECONDS);
        try {
            double priceAsyncLambda = priceLambdaFuture.get();
            System.out.printf("Async Lambda Price is %.2f%n", priceAsyncLambda);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        System.out.println("Async Lambda Price returned after " + calculateRetrievalTime(start) + MILLISECONDS);
    }


    public static void compareSyncMethodWithParallelMethodAndAsyncMethod() {
        System.out.println("The number of available processors are " + Runtime.getRuntime().availableProcessors());

        var start = System.nanoTime();
        System.out.println(findPrices(PRODUCT_NAME));
        var duration = calculateRetrievalTime(start);
        System.out.println("Using sync done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        System.out.println(findPricesParallel(PRODUCT_NAME));
        duration = calculateRetrievalTime(start);
        System.out.println("Using parallel done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        System.out.println(findPricesAsync(PRODUCT_NAME));
        duration = calculateRetrievalTime(start);
        System.out.println("Using async done in " + duration + MILLISECONDS);
    }
}
