package splitter.view;

import splitter.command.MenuCommand;

import java.util.List;
import java.util.Map;

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

    public void printBalance(List<String> balance) {
        if (balance.isEmpty()) {
            System.out.println("No repayments need");
        } else {
            balance.forEach(System.out::println);
        }
    }

    public void printList(List<String> list) {
        list.forEach(System.out::println);
    }

    public void printUnknownGroup() {
        System.out.println("Unknown group");
    }

    public void printGifting(String from, String to) {
        System.out.printf("%s gift to %s\n", from, to);
    }

    public void printGiftingPairs(Map<String, String> pairs) {
        for (Map.Entry<String, String> pair : pairs.entrySet()) {
            printGifting(pair.getKey(), pair.getValue());
        }
    }
}
