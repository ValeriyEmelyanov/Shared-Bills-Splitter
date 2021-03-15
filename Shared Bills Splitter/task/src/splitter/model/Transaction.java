package splitter.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "date")
    private LocalDate date;

    @ManyToOne
    private Person creditor;

    @ManyToOne
    private Person debtor;

    @Column(name = "sum")
    private BigDecimal sum;

    public Transaction() {
    }

    public Transaction(LocalDate date, Person creditor, Person debtor, BigDecimal sum) {
        this.date = date;
        this.creditor = creditor;
        this.debtor = debtor;
        this.sum = sum;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Person getCreditor() {
        return creditor;
    }

    public void setCreditor(Person creditor) {
        this.creditor = creditor;
    }

    public Person getDebtor() {
        return debtor;
    }

    public void setDebtor(Person debtor) {
        this.debtor = debtor;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }
}
