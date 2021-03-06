package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

public class BalanceCommand implements Command {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    @Override
    public void execute(Controller controller) {
        String[] arguments = controller.getCommandArguments();

        int currentIndex = 0;
        LocalDate date = null;
        BalanceMode mode = null;

        if (currentIndex < arguments.length) {
            try {
                date = LocalDate.parse(arguments[0], formatter);
                currentIndex++;
            } catch (Exception ignored) {
            }
        }
        if (currentIndex < arguments.length) {
            mode = BalanceMode.valueOf(arguments[currentIndex].toUpperCase(Locale.ROOT));
            currentIndex ++;
        }

        if (currentIndex < arguments.length) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        if (date == null) {
            date = LocalDate.now();
        }
        if (mode == null) {
            mode = BalanceMode.CLOSE;
        }

        if (mode == BalanceMode.OPEN) {
            date = date.withDayOfMonth(1).minusDays(1);
        }

        Map<Person, Map<Person, Integer>> forDate = new TreeMap<>();
        List<Transaction> list = controller.getRegister().getList();
        for (Transaction record : list) {
            if (record.getDate().isAfter(date)) {
                continue;
            }

            Map<Person, Integer> debts = forDate.computeIfAbsent(
                    record.getCreditor(), k -> new TreeMap<>());
            debts.put(record.getDebtor(),
                    debts.getOrDefault(record.getDebtor(), 0) - record.getSum());

            debts = forDate.computeIfAbsent(
                    record.getDebtor(), k -> new TreeMap<>());
            debts.put(record.getCreditor(),
                    debts.getOrDefault(record.getCreditor(), 0) + record.getSum());
        }

        List<String> balance = new ArrayList<>();
        for (Map.Entry<Person, Map<Person, Integer>> debtsEntry : forDate.entrySet()) {
            for (Map.Entry<Person, Integer> entry : debtsEntry.getValue().entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }
                balance.add(String.format("%s owes %s %d",
                        debtsEntry.getKey().getName(),
                        entry.getKey().getName(),
                        entry.getValue()));
            }
        }

        controller.getView().printList(balance);
    }
}

enum BalanceMode {
    OPEN, CLOSE;
}
