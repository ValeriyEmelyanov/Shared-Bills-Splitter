package splitter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import splitter.model.Group;

import java.util.Optional;

@Repository
    public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByName(String name);

}
