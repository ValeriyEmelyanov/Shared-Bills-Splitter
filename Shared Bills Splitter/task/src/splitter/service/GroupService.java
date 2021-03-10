package splitter.service;

import splitter.model.Group;
import splitter.model.Person;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

public class GroupService {
    private static final Map<String, Group> GROUPS = new HashMap<>();

    public GroupService() {
    }

    public static Map<String, Group> getGroups() {
        return Collections.unmodifiableMap(GROUPS);
    }

    public static void put(Group group) {
        GROUPS.put(group.getName(), group);
    }

    public static Optional<Group> getByName(String name) {
        Group group = GROUPS.get(name);
        if (group == null) {
            return Optional.empty();
        }
        return Optional.of(group);
    }

    public static Optional<Set<Person>> groupMembersFromArgumentGroup(String[] argumentGroup) {
        if (argumentGroup == null || argumentGroup.length == 0) {
            return Optional.empty();
        }

        Set<Person> members = new TreeSet<>();
        Set<Person> toRemove = new HashSet<>();

        for (String s : argumentGroup) {
            if (s.matches("-?[A-Z]+")) {
                Optional<Group> groupOptional = GroupService.getByName(s.replace("-", ""));
                if (groupOptional.isEmpty()) {
                    return Optional.empty();
                }
                if (s.startsWith("-")) {
                    toRemove.addAll(groupOptional.get().getMembers());
                } else {
                    members.addAll(groupOptional.get().getMembers());
                }
                continue;
            }
            if (s.startsWith("-")) {
                toRemove.add(new Person(s.substring(1)));
                continue;
            }
            members.add(new Person(s));
        }

        members.removeAll(toRemove);

        return Optional.of(members);
    }
}
