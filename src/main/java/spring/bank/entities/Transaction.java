package spring.bank.entities;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts;

    private String sourceName;

    private String sourceIban;

    private String destinationName;

    private String destinationIban;

    private String destinationBIC;

    private Float amount = null;

    private String currency;

    private String purposeOfUse;

    private OffsetDateTime dateOfFutureTransaction;

    private OffsetDateTime dateOfCreation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceIban() {
        return sourceIban;
    }

    public void setSourceIban(String sourceIban) {
        this.sourceIban = sourceIban;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDestinationIban() {
        return destinationIban;
    }

    public void setDestinationIban(String destinationIban) {
        this.destinationIban = destinationIban;
    }

    public String getDestinationBIC() {
        return destinationBIC;
    }

    public void setDestinationBIC(String destinationBIC) {
        this.destinationBIC = destinationBIC;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPurposeOfUse() {
        return purposeOfUse;
    }

    public void setPurposeOfUse(String purposeOfUse) {
        this.purposeOfUse = purposeOfUse;
    }

    public OffsetDateTime getDateOfFutureTransaction() {
        return dateOfFutureTransaction;
    }

    public void setDateOfFutureTransaction(OffsetDateTime dateOfFutureTransaction) {
        this.dateOfFutureTransaction = dateOfFutureTransaction;
    }

    public OffsetDateTime getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(OffsetDateTime dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
