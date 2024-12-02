package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring.bank.entities.Account;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Value("${iban.countrycode}")
    String ibanCountryCode;

    @Value("${iban.checkdigits}")
    String ibanCheckDigits;

    @Value("${iban.bankcode}")
    String ibanBankCode;

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

    public String buildIban(Integer accountNumber) {

        String formattedAccountNumber = String.format("%06d", accountNumber);

        return ibanCountryCode
                + ibanCheckDigits
                + ibanBankCode
                + formattedAccountNumber;
    }

    //TODO: Safe highest iban in extra value. If account gets deleted account number will given away again!
    public Integer getHighestAccountNumber() {

        Optional<Account> topAccountNumberOptional = accountRepository.findTopByOrderByAccountNumberDesc();

        if(topAccountNumberOptional.isEmpty()){
            return  0;
        }
        else {
            return topAccountNumberOptional.get().getAccountNumber();
        }
    }

    public boolean validate(Account destinationAccount, String destinationName) {

        return Objects.equals(destinationName, destinationAccount.getUser().getFirstName() + " " + destinationAccount.getUser().getLastName());

    }

    public boolean isSourceBankEqualsDestinationBank(String destinationIban) {
        return true;//TODO regex threw iban and check
    }
}
