package splitter.service;

import splitter.model.Transaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionService {
    private static final List<Transaction> TRANSACTIONS = new ArrayList<>();

    private TransactionService() {
    }

    public static List<Transaction> getTransactions() {
        return Collections.unmodifiableList(TRANSACTIONS);
    }

    public static void add(Transaction record) {
        TRANSACTIONS.add(record);
    }
}
