package splitter.command;

import splitter.controller.Controller;

public enum MenuCommand implements Command {
    BALANCE(new BalanceCommand()),
    BORROW(new BorrowCommand()),
    EXIT(null),
    HELP(new HelpCommand()),
    REPAY(new RepayCommand());

    private final Command command;

    MenuCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute(Controller controller) {
        if (command == null) {
            return;
        }
        command.execute(controller);
    }
}
