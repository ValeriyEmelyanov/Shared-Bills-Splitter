package splitter.command;

import splitter.controller.Controller;

public interface Command {
    void execute(Controller controller);
}
