package splitter.command;

import splitter.controller.Controller;
import splitter.model.Group;
import splitter.model.Person;
import splitter.service.GroupService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupCommand implements Command {
    @Override
    public void execute(Controller controller) {
        String[] arguments = controller.getOperationArguments();
        GroupMode groupMode;
        try {
            groupMode = GroupMode.valueOf(arguments[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        switch (groupMode) {
            case CREATE:
                createGroup(controller, arguments);
                break;
            case SHOW:
                showGroup(controller, arguments);
                break;
            default:
                controller.getView().printInvalidCommandArguments();
        }
    }

    private void showGroup(Controller controller, String[] arguments) {
        if (arguments.length < 2) {
            controller.getView().printInvalidCommandArguments();
        }
        Optional<Group> optional = GroupService.getByName(arguments[1]);
        if (optional.isEmpty()) {
            controller.getView().printUnknownGroup();
            return;
        }
        Set<Person> members = optional.get().getMembers();
        controller.getView().printList(
                members.stream()
                        .map(Person::getName)
                        .collect(Collectors.toList()));
    }

    private void createGroup(Controller controller, String[] arguments) {
        String groupName = arguments[1];
        if (groupName.matches(".*[a-z].*")) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        String[] argumentGroup = controller.getArgumentGroup();
        if (argumentGroup == null || argumentGroup.length == 0) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Set<Person> members = Arrays.stream(argumentGroup)
                .map(Person::new)
                .collect(Collectors.toSet());
        GroupService.put(new Group(groupName, members));
    }

    enum GroupMode {
        CREATE, SHOW;
    }
}
