package splitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import splitter.model.Person;
import splitter.model.Transaction;
import splitter.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public List<Transaction> getByDateBeforOrEquals(LocalDate date) {
        return transactionRepository.findAllByDateIsLessThanEqualOrderByDate(date);
    }

    @Transactional
    public void create(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Transactional
    public void deleteAllByDateBefore(LocalDate date) {
        transactionRepository.deleteAllByDateBefore(date);
    }

    private void createTransactionsFromDistribution(TransactionService transactionService,
                                                    LocalDate operationDate,
                                                    Person funder,
                                                    Map<Person, BigDecimal> distribution) {
        for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
            if (funder.equals(entry.getKey())) {
                continue;
            }
            transactionService.create(new Transaction(
                    operationDate,
                    funder,
                    entry.getKey(),
                    entry.getValue()
            ));
        }
    }

    public void createTransactionsFromDistribution(LocalDate operationDate,
                                                   Person funder,
                                                   Map<Person, BigDecimal> distribution) {
        for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
            if (funder.equals(entry.getKey())) {
                continue;
            }
            create(new Transaction(operationDate,
                    funder,
                    entry.getKey(),
                    entry.getValue()));
        }
    }

    public void createTransactionsFromDistribution(LocalDate operationDate,
                                                   Map<Person, BigDecimal> distribution,
                                                   Person recipient) {
        for (Map.Entry<Person, BigDecimal> entry : distribution.entrySet()) {
            if (recipient.equals(entry.getKey())) {
                continue;
            }
            create(new Transaction(operationDate,
                    entry.getKey(),
                    recipient,
                    entry.getValue()));
        }
    }

}
