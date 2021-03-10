package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;
import splitter.service.GroupService;
import splitter.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class Purchase implements Command {

    @Override
    public void execute(Controller controller) {
        String[] commandArguments = controller.getOperationArguments();
        String[] argumentGroup = controller.getArgumentGroup();
        if (commandArguments.length != 3 || argumentGroup.length < 1) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Optional<Set<Person>> optionalMembers =
                GroupService.groupMembersFromArgumentGroup(argumentGroup);
        if (optionalMembers.isEmpty()) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Set<Person> members = optionalMembers.get();
        int intDivisor = members.size();
        if (intDivisor == 0) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        BigDecimal sum;
        Person funder;
        try {
            sum = new BigDecimal(commandArguments[commandArguments.length - 1])
                    .setScale(2, RoundingMode.UNNECESSARY);
            funder = new Person(commandArguments[0]);
        } catch (Exception e) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Map<Person, BigDecimal> distribution = getDistribution(funder, members,
                sum, BigDecimal.valueOf(intDivisor));

        createTransactions(controller.getOperationDate(), funder, distribution);
    }

    private Map<Person, BigDecimal> getDistribution(
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

    private void createTransactions(LocalDate operationDate, Person funder, Map<Person, BigDecimal> distribution) {
        for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
            if (funder.equals(entry.getKey())) {
                continue;
            }
            TransactionService.add(new Transaction(
                    operationDate,
                    funder,
                    entry.getKey(),
                    entry.getValue()
            ));
        }
    }
}
