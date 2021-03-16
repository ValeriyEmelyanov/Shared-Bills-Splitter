package splitter.command;

import splitter.controller.Controller;

public class WriteOffCommand implements Command {
    @Override
    public void execute(Controller controller) {
        controller
                .getTransactionService()
                .deleteAllByDateBefore(controller.getOperationDate().plusDays(1));
    }
}
