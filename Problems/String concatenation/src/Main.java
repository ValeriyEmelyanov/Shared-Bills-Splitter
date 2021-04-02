import java.util.Scanner;
import java.util.function.Function;

class CurryConcat {

    public static String calc(String... str) {
        if (str.length != 4) {
            throw new IllegalArgumentException();
        }

        Function<String, Function<String, Function<String, Function<String, String>>>> stringFun =
            a -> b -> c -> d -> a.toLowerCase() + c.toUpperCase()
                + b.toLowerCase() + d.toUpperCase();

        return stringFun.apply(str[0]).apply(str[1]).apply(str[2]).apply(str[3]);
    }

    // Don't change the code below
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String[] values = scanner.nextLine().split(" ");
        System.out.println(calc(values[0], values[1], values[2], values[3]));
    }
}