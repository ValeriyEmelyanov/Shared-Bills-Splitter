package splitter.controller;

import splitter.command.Command;
import splitter.command.MenuCommand;
import splitter.model.Register;
import splitter.view.ConsoleView;

import java.util.Arrays;
import java.util.Scanner;

public class Controller {
    private final Scanner scanner;
    private final ConsoleView view;

    private final Register register;

    private String[] commandArguments;

    public Controller() {
        this.scanner = new Scanner(System.in);
        this.view = new ConsoleView();
        this.register = new Register();
    }

    public ConsoleView getView() {
        return view;
    }
    public String[] getCommandArguments() {
        return commandArguments;
    }

    public Register getRegister() {
        return register;
    }

    public void run() {
        String commandLine;
        while (!(commandLine = scanner.nextLine()).equalsIgnoreCase("exit")) {
            String[] commandParts = commandLine.split("\\s+");
            String commandName = parseCommandParts(commandParts);
            if (commandName == null) {
                view.printUnknownCommand();
                continue;
            }

            Command menuCommand;
            try {
                menuCommand = MenuCommand.valueOf(commandName);
            } catch (IllegalArgumentException e) {
                view.printUnknownCommand();
                continue;
            }
            menuCommand.execute(this);
        }
    }

    private String parseCommandParts(String[] commandParts) {
        String commandName = null;

        for (MenuCommand command : MenuCommand.values()) {
            if (command.name().equals(commandParts[0].toUpperCase())) {
                commandName = commandParts[0].toUpperCase();
                if (commandParts.length > 1) {
                    commandArguments = Arrays.copyOfRange(
                            commandParts, 1, commandParts.length);
                } else {
                    commandArguments = new String[0];
                }
                break;
            }
            if (commandParts.length > 1 &&
                    command.name().equals(commandParts[1].toUpperCase())) {
                commandName = commandParts[1].toUpperCase();
                commandArguments = new String[commandParts.length - 1];
                commandArguments[0] = commandParts[0];
                if (commandArguments.length > 1) {
                    System.arraycopy(commandParts, 2,
                            commandArguments, 1, commandArguments.length - 1);
                }
                break;
            }
        }

        return commandName;
    }
}
