import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FunctionUtils {

    public static <T> Supplier<Stream<T>> saveStream(Stream<T> stream) {
        return stream.collect(Collectors.toList())::stream;
    }

}