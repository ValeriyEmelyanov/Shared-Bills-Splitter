package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.model.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TransactionalOperation {

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

        Person from = controller
                .getPersonService()
                .getByNameOrCreate(arguments[fromIndex]);
        Person to = controller
                .getPersonService()
                .getByNameOrCreate(arguments[toIndex]);
        if (from.equals(to)) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        controller.getTransactionService().create(
                new Transaction(controller.getOperationDate(), from, to, sum));
    }
}
