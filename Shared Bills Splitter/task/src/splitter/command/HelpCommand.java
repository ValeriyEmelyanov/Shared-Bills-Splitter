package splitter.command;

import splitter.controller.Controller;

public class HelpCommand implements Command {
    @Override
    public void execute(Controller controller) {
        controller.getView().printCommandList();
    }
}
