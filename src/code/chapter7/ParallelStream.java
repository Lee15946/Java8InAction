package code.chapter7;

import java.util.Spliterator;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ParallelStream {
    public static void main(String[] args) {
        System.out.println(ParallelStream.parallelSum(100));
        System.out.println(ParallelStream.forkJoinSum(100000));

        System.out.println(sequentialSum(10));
        System.out.println(iterativeSum(10));
        System.out.println(rangedSum(10));
        System.out.println(rangedParallelSum(10));

        final var sentence = "1a a b c d c e 2 3 4 5 6 7 87 7 321  3   1 2 r";
        System.out.println(countWordsInteractively(sentence));

        final var stream1 = IntStream.range(0, sentence.length()).mapToObj(sentence::charAt);
        System.out.println(countWords(stream1));

        Spliterator<Character> spliterator = new WordCounterSpliterator(sentence);
        Stream<Character> stream2 = StreamSupport.stream(spliterator, true);
        System.out.println(countWords(stream2.parallel()));

        Thread thread = new Thread(()-> System.out.println(2222));
        thread.start();
    }
    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(0L, Long::sum);
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i < n; i++) {
            result += i;
        }
        return result;
    }

    public static long parallelSum(int n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(0L, Long::sum);
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1, n).reduce(0L, Long::sum);
    }

    public static long rangedParallelSum(long n) {
        return LongStream.rangeClosed(1, n).parallel().reduce(0L, Long::sum);
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static int countWordsInteractively(String s) {
        int count = 0;
        boolean lastSpace = true;
        for (char c : s.toCharArray()) {
            if (Character.isWhitespace(c)) {
                lastSpace = true;
            } else {
                if (lastSpace) count++;
                lastSpace = false;
            }
        }
        return count;
    }

    public static int countWords(Stream<Character> stream) {
        final var wordCounter = stream.reduce(new WordCounter(0, true), WordCounter::accumulate, WordCounter::combine);
        return wordCounter.getCounter();

    }
}
