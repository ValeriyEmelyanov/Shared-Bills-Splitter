package splitter.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {
    private final LocalDate date;
    private final Person creditor;
    private final Person debtor;
    private final BigDecimal sum;

    public Transaction(LocalDate date, Person from, Person to, BigDecimal sum) {
        this.date = date;
        this.creditor = from;
        this.debtor = to;
        this.sum = sum;
    }

    public LocalDate getDate() {
        return date;
    }

    public Person getCreditor() {
        return creditor;
    }

    public Person getDebtor() {
        return debtor;
    }

    public BigDecimal getSum() {
        return sum;
    }
}
