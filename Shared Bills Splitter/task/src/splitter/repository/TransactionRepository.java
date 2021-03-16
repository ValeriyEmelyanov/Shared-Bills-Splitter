package splitter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import splitter.model.Transaction;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findAllByDateIsLessThanEqualOrderByDate(LocalDate date);

    void deleteAllByDateBefore(LocalDate date);
}
