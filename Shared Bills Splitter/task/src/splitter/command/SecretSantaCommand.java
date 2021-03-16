package splitter.command;

import splitter.controller.Controller;
import splitter.model.Group;
import splitter.model.Person;
import splitter.util.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SecretSantaCommand implements Command {
    @Override
    public void execute(Controller controller) {
        String[] operationArguments = controller.getOperationArguments();
        if (operationArguments.length != 1) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Optional<Group> optionalGroup = controller
                .getGroupService()
                .getByName(operationArguments[0]);
        if (optionalGroup.isEmpty()) {
            controller.getView().printInvalidCommandArguments();
            return;
        }

        Set<Person> members = optionalGroup.get().getMembers();

        if (members.size() == 1) {
            String name = members.stream().findFirst().get().getName();
            controller.getView().printGifting(name, name);
            return;
        }

        List<String> names = members.stream()
                .map(Person::getName)
                .sorted()
                .collect(Collectors.toList());

        Map<String, String> pairs = new TreeMap<>();

        if (members.size() == 2) {
            pairs.put(names.get(0), names.get(1));
            pairs.put(names.get(1), names.get(0));
            controller.getView().printGiftingPairs(pairs);
            return;
        }

        Set<String> selected = new HashSet<>();
        for (String name : names) {
            List<String> pool = new ArrayList<>(names);
            pool.remove(name);
            pool.removeAll(selected);
            String second = pool.size() == 1
                    ? pool.get(0)
                    : pool.get(Util.RANDOM.nextInt(pool.size()));
            pairs.put(name, second);
            selected.add(second);
        }
        controller.getView().printGiftingPairs(pairs);
    }
}
