package code.chapter11;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class Util {
    public static final String MILLISECONDS = " msecs";
    public static final int CONVERSION_RATIO = 1_000_000;
    public static final String PRODUCT_NAME = "myPhone27s";

    private static final Shop shop = new Shop("BestShop");
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

    public static void compareSyncMethodWithParallelMethodAndAsyncMethod() {
        System.out.println("The number of available processors are " + Runtime.getRuntime().availableProcessors());

        var start = System.nanoTime();
        System.out.println(findPrices(PRODUCT_NAME));
        var duration = (System.nanoTime() - start) / CONVERSION_RATIO;
        System.out.println("Using sync done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        System.out.println(findPricesParallel(PRODUCT_NAME));
        duration = (System.nanoTime() - start) / CONVERSION_RATIO;
        System.out.println("Using parallel done in " + duration + MILLISECONDS);

        start = System.nanoTime();
        System.out.println(findPricesAsync(PRODUCT_NAME));
        duration = (System.nanoTime() - start) / CONVERSION_RATIO;
        System.out.println("Using async done in " + duration + MILLISECONDS);
    }
}
