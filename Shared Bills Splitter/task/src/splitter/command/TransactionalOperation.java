package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;
import splitter.service.PersonService;
import splitter.service.TransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;

public class TransactionalOperation {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    protected void move(Controller controller, int fromIndex, int toIndex) {
        String[] arguments = controller.getOperationArguments();
        if (arguments.length != 3) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        BigDecimal sum;
        try {
            sum = new BigDecimal(arguments[arguments.length - 1])
                    .setScale(2, RoundingMode.UNNECESSARY);
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

        TransactionService.add(
                new Transaction(controller.getOperationDate(), from, to, sum));
    }
}
