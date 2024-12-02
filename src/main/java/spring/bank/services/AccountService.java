package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.bank.entities.Account;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
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

    @Transactional
    public List<AccountDto> getAll() {

        List<Account> accounts = accountRepository.findAll();
        List<AccountDto> accountDtoList = new ArrayList<>();

        for(Account account : accounts){
            AccountDto accountDto = modelMapper.map(account, AccountDto.class);
            accountDtoList.add(accountDto);
        }

        return accountDtoList;
    }
}
