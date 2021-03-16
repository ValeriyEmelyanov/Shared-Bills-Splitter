package splitter.util;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Util {
    public static final Random RANDOM = new Random();
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    private Util() {
    }
}
