import java.util.Scanner;
import java.util.stream.IntStream;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String[] strings = scanner.nextLine().split("\\s+");
        final int limit = strings.length - 1;
        IntStream.rangeClosed(0, limit)
                .filter(i -> (limit - i) % 2 != 0)
                .forEach(i -> System.out.printf("%s ", strings[limit - i]));
    }
}