package splitter.util;

import splitter.model.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CalculationUtil {
    private CalculationUtil() {
    }

    public static Map<Person, BigDecimal> getDistribution(
            Person funder, Set<Person> members,
            BigDecimal sum, BigDecimal divisor) {
        BigDecimal portion = sum.divide(divisor, RoundingMode.FLOOR);

        Map<Person, BigDecimal> distribution = new TreeMap<>();
        for (Person p : members) {
            if (p.equals(funder)) {
                continue;
            }
            distribution.put(p, portion);
        }

        BigDecimal remainder = sum.subtract(portion.multiply(divisor));
        if (remainder.compareTo(BigDecimal.ZERO) != 0) {
            BigDecimal increment = new BigDecimal("0.01");
            for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
                if (entry.getKey().equals(funder)) {
                    continue;
                }
                distribution.put(entry.getKey(), entry.getValue().add(increment));
                remainder = remainder.subtract(increment);
                if (remainder.compareTo(BigDecimal.ZERO) == 0) {
                    break;
                }
            }
        }
        return distribution;
    }
}
