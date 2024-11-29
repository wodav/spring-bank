package spring.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.bank.entities.Account;
import spring.bank.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
