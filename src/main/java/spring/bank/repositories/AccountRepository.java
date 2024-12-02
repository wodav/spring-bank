package spring.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.bank.entities.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //@Query(value = "SELECT MAX(account_number) FROM Account", nativeQuery = true)
    Optional<Account> findTopByOrderByAccountNumberDesc();
}
