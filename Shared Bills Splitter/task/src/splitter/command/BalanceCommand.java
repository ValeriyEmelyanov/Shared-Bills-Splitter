package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class BalanceCommand implements Command {

    @Override
    public void execute(Controller controller) {
        String[] arguments = controller.getOperationArguments();

        BalanceMode mode = arguments.length == 1
                ? BalanceMode.valueOf(arguments[0].toUpperCase(Locale.ROOT))
                : BalanceMode.CLOSE;

        LocalDate operationDate = controller.getOperationDate();
        if (mode == BalanceMode.OPEN) {
            operationDate = operationDate.withDayOfMonth(1).minusDays(1);
        }

        Map<Person, Map<Person, BigDecimal>> forDate = new TreeMap<>();
        for (Transaction record :
                controller.getTransactionService().getByDateBeforOrEquals(operationDate)) {

            Map<Person, BigDecimal> debts = forDate.computeIfAbsent(
                    record.getCreditor(), k -> new TreeMap<>());
            debts.put(record.getDebtor(),
                    debts.getOrDefault(record.getDebtor(), BigDecimal.ZERO).subtract(record.getSum()));

            debts = forDate.computeIfAbsent(
                    record.getDebtor(), k -> new TreeMap<>());
            debts.put(record.getCreditor(),
                    debts.getOrDefault(record.getCreditor(), BigDecimal.ZERO).add(record.getSum()));
        }

        String[] argumentGroup = controller.getArgumentGroup();
        Set<Person> people;
        if (argumentGroup.length > 0) {
            Optional<Set<Person>> optionalPeople = controller
                    .getGroupService()
                    .groupMembersFromArgumentGroup(
                            argumentGroup, controller.getPersonService());
            people = optionalPeople.orElse(Set.of());
        } else {
            people = Set.of();
        }

        List<String> balance = new ArrayList<>();
        for (Map.Entry<Person, Map<Person, BigDecimal>> debtsEntry : forDate.entrySet()) {
            for (Map.Entry<Person, BigDecimal> entry : debtsEntry.getValue().entrySet()) {
                if (entry.getValue().compareTo(BigDecimal.ZERO) <= 0) {
                    continue;
                }
                if (!people.isEmpty() && !people.contains(debtsEntry.getKey())) {
                    continue;
                }
                balance.add(String.format("%s owes %s %s",
                        debtsEntry.getKey().getName(),
                        entry.getKey().getName(),
                        entry.getValue().toString()));
            }
        }

        controller.getView().printBalance(balance);
    }
}

enum BalanceMode {
    OPEN, CLOSE;
}
