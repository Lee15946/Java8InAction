package code.chapter11;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.random.RandomGenerator;
import java.util.stream.Stream;

public class PriceUtil {
    public static final String MILLISECONDS = " msecs";
    public static final int CONVERSION_RATIO = 1_000_000;
    public static final String PRODUCT_NAME = "myPhone27s";

    private static final Shop singleShop = new Shop("BestShop");

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

    public static void main(String[] args) {
        compareSyncMethodWithAsyncMethod();
        compareSyncMethodWithParallelMethodAndAsyncMethod();
        compareSyncStreamWithAsyncStream();
        compareFutureWithCompletableFuture();
        reactCompletionInCompletableFuture();
    }

    public static void delay() {
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }
    }

    /**
     * Simulate random I/O, network delay
     */
    public static void randomDelay() {
        int delay = 100 + RandomGenerator.getDefault().nextInt(1000);
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException();
        }
    }

    public static long calculateRetrievalTime(long start) {
        return (System.nanoTime() - start) / CONVERSION_RATIO;
    }

    public static List<String> findPrices(String product) {
        return shops.stream().map(shop -> String.format("%s price is %.2f", shop.shopName(), shop.getPriceSync(product))).toList();
    }

    public static List<String> findPricesParallel(String product) {
        return shops.parallelStream().map(shop -> String.format("%s price is %.2f", shop.shopName(), shop.getPriceSync(product))).toList();
    }

    /**
     * Maintain a thread pool, numbers of thread need to less than or equal to the length of shops
     */
    private static final ExecutorService executors = Executors.newFixedThreadPool(shops.size(), r -> {
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        return thread;
    });

    public static List<String> findPricesAsync(String product) {
        final var priceFutures = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(
                        () -> String.format("%s price is %.2f", shop.shopName(), shop.getPriceSync(product)), executors)).toList();
        return priceFutures.stream().map(CompletableFuture::join).toList();
    }

    private static List<String> findPricesWithCodeSync() {
        return shops.stream().map(shop -> shop.getPriceWithCode(PriceUtil.PRODUCT_NAME))
                .map(Quote::parse)
                .map(Discount::applyDiscount).toList();
    }

    private static List<String> findPricesWithCodeAsync() {
        final Stream<CompletableFuture<String>> priceFutures = getCompletableFuturesStream();
        //Waiting for all tasks done, then extract the return value
        return priceFutures.map(CompletableFuture::join).toList();

    }

    private static Stream<CompletableFuture<String>> getCompletableFuturesStream() {
        return shops.stream()
                //Use async way to get original price
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        shop.getPriceWithCode(PRODUCT_NAME), executors))
                //Use sync way to get discount code
                .map(future -> future.thenApply(Quote::parse))
                //Use another async task to build the discount price
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executors)));
    }

    private static Stream<CompletableFuture<String>> getCompletableFuturesStreamWithRandomDelay() {
        randomDelay();
        return shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() ->
                        shop.getPriceWithCodeByRandomDelay(PRODUCT_NAME), executors))
                .map(future -> future.thenApply(Quote::parse))
                .map(future -> future.thenCompose(
                        quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executors)));
    }

    public static void compareSyncMethodWithAsyncMethod() {
        var start = System.nanoTime();
        final var priceSync = singleShop.getPriceSync(PRODUCT_NAME);
        System.out.printf("Sync Price is %.2f%n", priceSync);
        System.out.println("Sync Price returned after " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        final var priceFuture = singleShop.getPriceAsync(PRODUCT_NAME);
        System.out.println("Async Invocation returned after " + calculateRetrievalTime(start) + MILLISECONDS);
        try {
            double priceAsync = priceFuture.get();
            System.out.printf("Async Price is %.2f%n", priceAsync);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        System.out.println("Async Price returned after " + calculateRetrievalTime(start) + MILLISECONDS);

        start = System.nanoTime();
        final var priceLambdaFuture = singleShop.getPriceAsyncByLambda(PRODUCT_NAME);
        System.out.println("Async Lambda Invocation returned after " + calculateRetrievalTime(start) + MILLISECONDS);
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

    public static void compareSyncStreamWithAsyncStream() {
        var start = System.nanoTime();
        System.out.println(findPricesWithCodeSync());
        var duration = calculateRetrievalTime(start);
        System.out.println("Using sync stream done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        System.out.println(findPricesWithCodeAsync());
        duration = calculateRetrievalTime(start);
        System.out.println("Using async stream done in " + duration + MILLISECONDS);
    }

    /**
     * The main difference between Future and CompletableFuture is they can use Lambda expression or not.
     * Not too much performance difference.
     */
    public static void compareFutureWithCompletableFuture() {
        var start = System.nanoTime();
        final var priceAsEURByFuture = singleShop.getPriceAsEURByFuture(PRODUCT_NAME);
        try {
            final var priceByFuture = priceAsEURByFuture.get();
            System.out.printf("Future Price is %.2f%n", priceByFuture);

        } catch (Exception e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
        var duration = calculateRetrievalTime(start);
        System.out.println("Using future async done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        final var priceAsEURByCompletableFuture = singleShop.getPriceAsEURByCompletableFuture(PRODUCT_NAME);
        final var priceByCompletableFuture = priceAsEURByCompletableFuture.join();
        System.out.printf("CompletableFuture Price is %.2f%n", priceByCompletableFuture);
        duration = calculateRetrievalTime(start);
        System.out.println("Using completableFuture async done in " + duration + MILLISECONDS);
    }

    public static void reactCompletionInCompletableFuture() {
        var start = System.nanoTime();
        final var completableFutures = getCompletableFuturesStreamWithRandomDelay().map(future -> future.thenAccept(
                        s -> System.out.println(s + " (done in " + calculateRetrievalTime(start) + MILLISECONDS + ")")
                ))
                .toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(completableFutures).join();
        System.out.println("All shops have now responded in " + calculateRetrievalTime(start) + MILLISECONDS);
    }
}
