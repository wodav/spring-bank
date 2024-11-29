package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.bank.entities.Account;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public AccountDto delete(Integer id) {

        Optional<Account> optionalAccount = accountRepository.findById(id.longValue());

        if(optionalAccount.isEmpty()){
            throw new NullPointerException("Account with id " + id + " not found");
        }
        else {
            Account account = optionalAccount.get();
            accountRepository.delete(account);
            return modelMapper.map(account, AccountDto.class);
        }
    }
}
