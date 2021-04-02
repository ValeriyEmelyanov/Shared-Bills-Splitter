import java.util.*;
import java.util.function.*;
import java.util.stream.*;


class MinMax {

    public static <T> void findMinMax(
            Stream<? extends T> stream,
            Comparator<? super T> order,
            BiConsumer<? super T, ? super T> minMaxConsumer) {
        List<? extends T> orderedList = stream
                .sorted(order)
                .collect(Collectors.toList());
        if (orderedList.isEmpty()) {
            minMaxConsumer.accept(null, null);
        } else {
            minMaxConsumer.accept(
                    orderedList.get(0),
                    orderedList.get(orderedList.size() - 1));
        }
    }
}