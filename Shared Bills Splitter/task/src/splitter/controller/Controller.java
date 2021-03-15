package splitter.controller;

import org.springframework.stereotype.Component;
import splitter.command.Command;
import splitter.command.MenuCommand;
import splitter.service.GroupService;
import splitter.service.PersonService;
import splitter.service.TransactionService;
import splitter.view.ConsoleView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Controller {

    private final Scanner scanner;
    private final ConsoleView view;
    private final Pattern pattern;
    private final DateTimeFormatter formatter;

    private final PersonService personService;
    private final GroupService groupService;
    private final TransactionService transactionService;

    private LocalDate operationDate;
    private String[] operationArguments;
    private String[] argumentGroup;

    public Controller(PersonService personService,
                      GroupService groupService,
                      TransactionService transactionService) {
        this.personService = personService;
        this.groupService = groupService;
        this.transactionService = transactionService;

        this.scanner = new Scanner(System.in);
        this.view = new ConsoleView();
        this.pattern = Pattern.compile("\\(.*\\)");
        this.formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
    }

    public PersonService getPersonService() {
        return personService;
    }

    public GroupService getGroupService() {
        return groupService;
    }

    public TransactionService getTransactionService() {
        return transactionService;
    }

    public ConsoleView getView() {
        return view;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public String[] getOperationArguments() {
        return operationArguments;
    }

    public String[] getArgumentGroup() {
        return argumentGroup;
    }

    public void run() {
        String commandLine;
        while (!(commandLine = scanner.nextLine()).equalsIgnoreCase("exit")) {
            String commandName;
            try {
                commandName = parseCommandLine(commandLine);
            } catch (Exception e) {
                view.printInvalidCommandArguments();
                continue;
            }
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

    private String parseCommandLine(String commandLine) {
        commandLine = extractOperatonDate(commandLine);
        commandLine = extractArgumentGroup(commandLine);

        String[] commandParts = commandLine.split("\\s+");
        String commandName = null;

        for (MenuCommand command : MenuCommand.values()) {
            if (command.name().equals(commandParts[0].toUpperCase())) {
                commandName = commandParts[0].toUpperCase();
                if (commandParts.length > 1) {
                    operationArguments = Arrays.copyOfRange(
                            commandParts, 1, commandParts.length);
                } else {
                    operationArguments = new String[0];
                }
                break;
            }
            if (commandParts.length > 1 &&
                    command.name().equals(commandParts[1].toUpperCase())) {
                commandName = commandParts[1].toUpperCase();
                operationArguments = new String[commandParts.length - 1];
                operationArguments[0] = commandParts[0];
                if (operationArguments.length > 1) {
                    System.arraycopy(commandParts, 2,
                            operationArguments, 1, operationArguments.length - 1);
                }
                break;
            }
        }

        return commandName;
    }

    private String extractOperatonDate(String commandLine) {
        if (commandLine.length() > 10) {
            String maybeDate = commandLine.substring(0, 10);
            if (maybeDate.matches("\\d{4}\\.\\d{2}\\.\\d{2}")) {
                operationDate = LocalDate.parse(maybeDate, formatter);
                return commandLine.substring(11).trim();
            }
        }
        operationDate = LocalDate.now();
        return commandLine;
    }

    private String extractArgumentGroup(String commandLine) {
        String argGroupLine = null;
        Matcher matcher = pattern.matcher(commandLine);
        if (matcher.find()) {
            argGroupLine = matcher.group(0);
        }

        if (argGroupLine != null) {
            commandLine = commandLine.replace(argGroupLine, "");
            argGroupLine = argGroupLine.replaceAll("[()]", "");
            argumentGroup = argGroupLine.split(",\\s*");
            for (int i = 0; i < argumentGroup.length; i++) {
                if (argumentGroup[i].startsWith("+")) {
                    argumentGroup[i] = argumentGroup[i].substring(1);
                }
            }
        } else {
            argumentGroup = new String[0];
        }

        return commandLine;
    }
}
