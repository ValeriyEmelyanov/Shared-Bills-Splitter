package splitter.command;

import splitter.controller.Controller;
import splitter.model.Person;
import splitter.util.CalculationUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Purchase implements Command {

    @Override
    public void execute(Controller controller) {
        String[] commandArguments = controller.getOperationArguments();
        String[] argumentGroup = controller.getArgumentGroup();
        if (commandArguments.length != 3 || argumentGroup.length < 1) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Optional<Set<Person>> optionalMembers = controller
                .getGroupService()
                .groupMembersFromArgumentGroup(
                        argumentGroup, controller.getPersonService());
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
        try {
            sum = new BigDecimal(commandArguments[commandArguments.length - 1])
                    .setScale(2, RoundingMode.UNNECESSARY);
        } catch (Exception e) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Person funder = controller
                .getPersonService()
                .getByNameOrCreate(commandArguments[0]);

        Map<Person, BigDecimal> distribution = CalculationUtil.getDistribution(
                funder, members, sum, BigDecimal.valueOf(intDivisor));

        controller.getTransactionService().createTransactionsFromDistribution(
                controller.getOperationDate(), funder, distribution);
    }
}
