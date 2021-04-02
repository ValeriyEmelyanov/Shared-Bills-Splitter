public class Main {

    public static void main(String[] args) {
        int count = 0;
        for (Secret secret : Secret.values()) {
            if (secret.name().startsWith("STAR")) {
                count++;
            }
        }
        System.out.println(count);
    }
}

enum Secret {
    STAR, CRASH, START;
}
