import java.time.LocalTime;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LocalTime start1 = LocalTime.parse(scanner.next());
        LocalTime end1 = LocalTime.parse(scanner.next());
        LocalTime start2 = LocalTime.parse(scanner.next());
        LocalTime end2 = LocalTime.parse(scanner.next());

        boolean result = start1.isBefore(start2)
                ? !end1.isBefore(start2)
                : !end2.isBefore(start1);

        System.out.println(result);
    }
}