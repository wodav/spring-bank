package spring.bank.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.openapitools.dto.AccountDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "transactions")
    private Set<Account> accounts;

    private String source;

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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
}
