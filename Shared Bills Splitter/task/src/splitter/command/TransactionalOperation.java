package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;
import splitter.service.PersonService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TransactionalOperation {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    protected void move(Controller controller, int fromIndex, int toIndex) {
        String[] arguments = controller.getCommandArguments();
        if (arguments.length < 3) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        if (arguments.length == 3) {
            fromIndex--;
            toIndex--;
        }

        LocalDate date;
        int sum;
        try {
            date = arguments.length == 4 ? LocalDate.parse(arguments[0], formatter) : LocalDate.now();
            sum = Integer.parseInt(arguments[arguments.length == 4 ? 3 : 2]);
        } catch (Exception e) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Person from = PersonService.getByName(arguments[fromIndex]);
        Person to = PersonService.getByName(arguments[toIndex]);
        if (from.equals(to)) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        controller.getRegister().add(
                new Transaction(date, from, to, sum));
    }
}
