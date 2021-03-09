package splitter.command;

import splitter.controller.Controller;
import splitter.model.Group;
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
        if (commandArguments.length != 3 || argumentGroup.length != 1) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Optional<Group> optionalGroup = GroupService.getByName(argumentGroup[0]);
        if (optionalGroup.isEmpty()) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Set<Person> members = optionalGroup.get().getMembers();
        LocalDate date;
        Person funder = new Person(commandArguments[0]);
        int intDivisor = members.size();
        if (intDivisor == 0) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        BigDecimal sum = new BigDecimal(commandArguments[commandArguments.length - 1])
                .setScale(2, RoundingMode.UNNECESSARY);
        BigDecimal divisor = BigDecimal.valueOf(intDivisor);
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

        for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
            if (funder.equals(entry.getKey())) {
                continue;
            }
            TransactionService.add(new Transaction(
                    controller.getOperationDate(),
                    funder,
                    entry.getKey(),
                    entry.getValue()
            ));
        }
    }
}
