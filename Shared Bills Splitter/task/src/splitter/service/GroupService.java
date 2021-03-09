package splitter.service;

import splitter.model.Group;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
}
