package splitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import splitter.model.Person;
import splitter.repository.PersonRepository;

import java.util.Optional;

@Component
public class PersonService {

    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getByNameOrCreate(String name) {
        Optional<Person> optionalPerson = personRepository.findByName(name);
        return optionalPerson.orElseGet(() -> personRepository.save(new Person(name)));
    }
}
