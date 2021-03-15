package splitter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import splitter.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    Optional<Person> findByName(String name);

}
