package splitter.command;

import splitter.controller.Controller;

public enum MenuCommand implements Command {
    BALANCE(new BalanceCommand()),
    BORROW(new BorrowCommand()),
    CASHBACK(new CashBackCommand()),
    EXIT(null),
    GROUP(new GroupCommand()),
    HELP(new HelpCommand()),
    PURCHASE(new Purchase()),
    REPAY(new RepayCommand()),
    SECRETSANTA(new SecretSantaCommand()),
    WRITEOFF(new WriteOffCommand());

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
