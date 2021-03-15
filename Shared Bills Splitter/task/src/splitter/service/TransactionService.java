package splitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import splitter.model.Transaction;
import splitter.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

@Component
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    private TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getByDateBeforOrEquals(LocalDate date) {
        return transactionRepository.findAllByDateIsLessThanEqualOrderByDate(date);
    }

    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}
