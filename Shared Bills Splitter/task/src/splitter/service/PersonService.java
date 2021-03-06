package splitter.service;

import splitter.model.Person;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class PersonService {
    private static final Set<Person> PEOPLE = new HashSet<>();

    private PersonService() {
    }

    public static Person getByName(String name) {
        Optional<Person> optionalPerson = PEOPLE.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();

        if (optionalPerson.isPresent()) {
            return optionalPerson.get();
        }

        Person person = new Person(name);
        PEOPLE.add(person);
        return person;
    }
}
