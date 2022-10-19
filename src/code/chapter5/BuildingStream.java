package code.chapter5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BuildingStream {
    public static void main(String[] args) {

        //Streams from values
        Stream.of("Java 8", "Lambdas", "In").map(String::toUpperCase).forEach(
                System.out::println
        );
        //Empty stream
        System.out.println(Stream.empty());
        //Stream from array
        int[] numbers = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.stream(numbers).sum());
        //Stream from file
        try (Stream<String> lines = Files.lines(Paths.get("data2.txt"))) {
            long uniqueWords = lines
                    .flatMap(line -> Arrays.stream(line.split("")))
                    .distinct().count();
            System.out.println("There are " + uniqueWords + " unique words");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Streams from functions
        Stream.iterate(new int[]{0, 1}, n -> new int[]{n[1], n[0] + n[1]}).limit(20).map(
                t -> t[0]
        ).forEach(System.out::println);

        Stream.generate(Math::random).limit(5).forEach(System.out::println);

        IntSupplier fib = new IntSupplier() {
            int previous = 0;
            int current = 1;

            @Override
            public int getAsInt() {
                int oldPrevious = this.previous;
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return oldPrevious;
            }
        };

        IntStream.generate(fib).limit(5).boxed().forEach(System.out::println);
    }
}
