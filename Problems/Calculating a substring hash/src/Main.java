import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        final String str = scanner.next();
        final int num = scanner.nextInt();
        int[][] indexes = new int[num][2];
        for (int i = 0; i < num; i++) {
            indexes[i][0] = scanner.nextInt();
            indexes[i][1] = scanner.nextInt();
        }

        long[] hashs = calcPolynomialRollingHash(str);
        for (int i = 0; i < hashs.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(hashs[i]);
        }
        System.out.println();

        for (int i = 0; i < num; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(calcSubstringHash(indexes[i][0], indexes[i][1], hashs));
        }

    }

    private static long calcSubstringHash(int start, int end, long[] hashs) {
        final int m = (int) (1e9 + 9);
        if (start == 0) {
            return hashs[end - 1];
        }
        return (hashs[end - 1] - hashs[start - 1] + m) % m;
    }

    private static long[] calcPolynomialRollingHash(String str) {
        long[] result = new long[str.length()];
        final int p = 53;
        final int m = (int) (1e9 + 9);
        long powerOfP = 1;
        long hash = 0;

        for (int i = 0; i < str.length(); i++) {
            hash = (hash + (str.charAt(i) - 'A' + 1) * powerOfP) % m;
            result[i] = hash;
            powerOfP = (powerOfP * p) % m;
        }
        return result;
    }
}