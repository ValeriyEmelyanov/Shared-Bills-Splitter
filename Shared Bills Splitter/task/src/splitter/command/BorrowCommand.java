package splitter.command;

import splitter.controller.Controller;

public class BorrowCommand extends TransactionalOperation implements Command {
    @Override
    public void execute(Controller controller) {
        move(controller, 1, 0);
    }
}
