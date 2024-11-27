package spring.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import spring.bank.entities.User;

import java.time.LocalDate;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}
