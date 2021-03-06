package splitter.view;

import splitter.command.MenuCommand;

import java.util.List;

public class ConsoleView {
    public void printCommandList() {
        for (MenuCommand menuCommand : MenuCommand.values()) {
            System.out.println(menuCommand.name().toLowerCase());
        }
    }

    public void printUnknownCommand() {
        System.out.println("Unknown command. Print help to show commands list");
    }

    public void printInvalidCommandArguments() {
        System.out.println("Illegal command arguments");
    }

    public void printList(List<String> balance) {
        if (balance.isEmpty()) {
            System.out.println("No repayments need");
        } else {
            balance.forEach(System.out::println);
        }
    }
}
