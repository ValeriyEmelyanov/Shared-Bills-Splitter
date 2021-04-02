import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalDateTime moment = LocalDateTime.parse(scanner.nextLine());
        scanner.close();

        long result = LocalDateTime.of(moment.getYear(), 1, 1, 0, 0)
                .until(moment, ChronoUnit.HOURS);

        System.out.println(result);
    }
}