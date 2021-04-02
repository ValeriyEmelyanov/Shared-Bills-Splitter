import java.time.LocalDateTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        LocalDateTime dateTime = null;
        for (int i = 0; i < number; i++) {
            LocalDateTime newDateTime = LocalDateTime.parse(scanner.next());
            if (dateTime == null || dateTime.isBefore(newDateTime)) {
                dateTime = newDateTime;
            }
        }
        System.out.println(dateTime);
    }
}