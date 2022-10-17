package code.chapter3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileProcess {
    public static void main(String[] args) throws IOException {
        System.out.println(processFile());
        System.out.println(processFile(BufferedReader::readLine));
        System.out.println(processFile(br -> br.readLine() + br.readLine()));
    }

    public static String processFile() throws IOException {
        try (final var br = new BufferedReader(new FileReader("data.txt"))) {

            return br.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor bufferedReaderProcessor) throws IOException {
        try (final var br = new BufferedReader(new FileReader("data.txt"))) {
            return bufferedReaderProcessor.process(br);
        }

    }
}
