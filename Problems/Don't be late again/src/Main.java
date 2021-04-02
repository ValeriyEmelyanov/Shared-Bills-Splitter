import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        List<String> list = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            list.add(scanner.nextLine());
        }
        scanner.close();

        LocalTime limit = LocalTime.of(19, 30).plusMinutes(30);
        list.stream()
                .map(s -> s.split("\\s+"))
                .filter(a -> limit.isBefore(LocalTime.parse(a[1])))
                .map(a -> a[0])
                .forEach(System.out::println);
    }
}