package splitter.command;

import splitter.controller.Controller;
import splitter.model.Group;
import splitter.model.Person;
import splitter.service.GroupService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupCommand implements Command {
    @Override
    public void execute(Controller controller) {
        String[] arguments = controller.getOperationArguments();
        if (arguments.length < 2) {
            controller.getView().printInvalidCommandArguments();
        }

        GroupMode groupMode;
        try {
            groupMode = GroupMode.valueOf(arguments[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        String groupName = arguments[1];
        if (groupName.matches(".*[a-z].*")) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Group group = null;
        if (groupMode != GroupMode.CREATE) {
            Optional<Group> optionalGroup = GroupService.getByName(groupName);
            if (optionalGroup.isEmpty()) {
                controller.getView().printUnknownGroup();
                return;
            }
            group = optionalGroup.get();
        }

        String[] argumentGroup = controller.getArgumentGroup();

        switch (groupMode) {
            case CREATE:
                createGroup(controller, groupName, argumentGroup);
                break;
            case ADD:
                changeGroupStructure(controller, group, argumentGroup, true);
                break;
            case REMOVE:
                changeGroupStructure(controller, group, argumentGroup, false);
            case SHOW:
                showGroup(controller, group);
                break;
            default:
                controller.getView().printInvalidCommandArguments();
        }
    }

    private void createGroup(Controller controller, String groupName, String[] argumentGroup) {
        Optional<Set<Person>> optionalMembers =
                GroupService.groupMembersFromArgumentGroup(argumentGroup);
        if (optionalMembers.isEmpty()) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        GroupService.put(new Group(groupName, optionalMembers.get()));
    }

    private void changeGroupStructure(Controller controller, Group group, String[] argumentGroup,
                                      boolean isAdding) {
        Optional<Set<Person>> optionalSet = GroupService.groupMembersFromArgumentGroup(argumentGroup);
        if (optionalSet.isEmpty()) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        if (isAdding) {
            group.addAll(optionalSet.get());
        } else {
            group.removeAll(optionalSet.get());
        }
    }

    private void showGroup(Controller controller, Group group) {
        Set<Person> members = group.getMembers();
        controller.getView().printList(
                members.stream()
                        .map(Person::getName)
                        .collect(Collectors.toList()));
    }

    enum GroupMode {
        CREATE, ADD, REMOVE, SHOW;
    }
}
