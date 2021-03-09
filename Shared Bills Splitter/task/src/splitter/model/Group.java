package splitter.model;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class Group {
    private final Set<Person> members = new TreeSet<>();
    private final String name;

    public Group(String name, Set<Person> members) {
        this.name = name;
        this.members.addAll(members);
    }

    public Group(String name, Person... persons) {
        this.name = name;
        this.members.addAll(Set.of(persons));
    }

    public String getName() {
        return name;
    }

    public Set<Person> getMembers() {
        return Collections.unmodifiableSet(members);
    }

}
