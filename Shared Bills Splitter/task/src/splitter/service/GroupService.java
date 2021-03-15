package splitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import splitter.model.Group;
import splitter.model.Person;
import splitter.repository.GroupRepository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

@Component
public class GroupService {

    private final GroupRepository groupRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public void create(Group group) {
        Optional<Group> optionalFetched = groupRepository.findByName(group.getName());
        optionalFetched.ifPresent(value -> group.setId(value.getId()));
        groupRepository.save(group);
    }

    public Optional<Group> getByName(String name) {
        return groupRepository.findByName(name);
    }

    public Optional<Set<Person>> groupMembersFromArgumentGroup(
            String[] argumentGroup, PersonService personService) {
        if (argumentGroup == null || argumentGroup.length == 0) {
            return Optional.empty();
        }

        Set<Person> members = new TreeSet<>();
        Set<Person> toRemove = new HashSet<>();

        for (String s : argumentGroup) {
            if (s.matches("-?[A-Z]+")) {
                Optional<Group> groupOptional = groupRepository
                        .findByName(s.replace("-", ""));
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
                toRemove.add(personService.getByNameOrCreate(s.substring(1)));
                continue;
            }
            members.add(personService.getByNameOrCreate(s));
        }

        members.removeAll(toRemove);

        return Optional.of(members);
    }

    public void addAll(Group group, Collection<Person> newMembers) {
        changeGroup(group, newMembers, true);
    }

    public void removeAll(Group group, Collection<Person> toRemove) {
        changeGroup(group, toRemove, false);
    }

    private void changeGroup(Group group, Collection<Person> changeMembers,
                             boolean isAdding) {
        groupRepository.findByName(group.getName())
                .ifPresent(value -> group.setId(value.getId()));
        Set<Person> members = group.getMembers();

        if (isAdding) {
            members.addAll(changeMembers);
        } else {
            members.removeAll(changeMembers);
        }

        group.setMembers(members);
        groupRepository.save(group);
    }
}
