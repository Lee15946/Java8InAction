package code;

import static code.chapter11.Util.compareFutureWithCompletableFuture;
import static code.chapter11.Util.compareSyncMethodWithAsyncMethod;
import static code.chapter11.Util.compareSyncMethodWithParallelMethodAndAsyncMethod;
import static code.chapter11.Util.compareSyncStreamWithAsyncStream;
import static code.chapter11.Util.reactCompletionInCompletableFuture;
import static code.chapter13.Util.compareIterationWithRecursion;
import static code.chapter13.Util.useListSplitter;
import static code.chapter14.Converter.useCurriedConverter;

public class Main {
    public static void main(String[] args) {
        compareSyncMethodWithAsyncMethod();
        compareSyncMethodWithParallelMethodAndAsyncMethod();
        compareSyncStreamWithAsyncStream();
        compareFutureWithCompletableFuture();
        reactCompletionInCompletableFuture();

        useListSplitter();
        compareIterationWithRecursion();

        useCurriedConverter();
    }
}