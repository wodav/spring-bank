package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.AccountDto;
import org.openapitools.dto.TransactionDto;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.bank.entities.Account;
import spring.bank.entities.Transaction;
import spring.bank.entities.User;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.TransactionRepository;
import spring.bank.repositories.UserRepository;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class UserService {

    @Autowired
    AccountService accountService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ModelMapper modelMapper;

    private final ChronoUnit truncateDateOfCreation = ChronoUnit.SECONDS;

    @Transactional
    public TransactionDto createTransaction(Integer userId, Integer accountId, TransactionDto transactionDto) throws IOException {

        Optional<Account> optionalSourceAccount = accountRepository.findById(accountId.longValue());

        if(optionalSourceAccount.isEmpty()) {
            throw new NullPointerException("Account with id " + accountId + " not found");
        }

        String destinationIban = transactionDto.getDestinationIban();

        Account destinationAccount = null;
        boolean sourceBankIsEqualsDestinationBank = accountService.isSourceBankEqualsDestinationBank(destinationIban);

        if(sourceBankIsEqualsDestinationBank) {

            Optional<Account> optionalDestinationAccount = accountRepository.findByIban(destinationIban);
            destinationAccount = optionalDestinationAccount.get();
            if (optionalDestinationAccount.isEmpty()) {
                throw new NullPointerException("Account with iban " + destinationIban + " not found");
            }
        }else {
            //TODO: RESTCALL To Iban Provider
        }

        Account sourceAccount = optionalSourceAccount.get();


        if(!accountService.validate(destinationAccount, transactionDto.getDestinationName())) {
            throw new IOException("Iban and user name are not matching");
        }

        User sourceUser = sourceAccount.getUser();

        String sourceName = sourceUser.getFirstName() + " " + sourceUser.getLastName();
        String sourceIban = sourceAccount.getIban();

        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);

        transaction.getAccounts().add(sourceAccount);
        transaction.setDateOfCreation(OffsetDateTime.now().truncatedTo(truncateDateOfCreation));
        transaction.setSourceName(sourceName);
        transaction.setSourceIban(sourceIban);
        transaction = transactionRepository.save(transaction);

        sourceAccount.getTransactions().add(transaction);
        accountRepository.save(sourceAccount);

        if(sourceBankIsEqualsDestinationBank) {
            destinationAccount.getTransactions().add(transaction);
            accountRepository.save(destinationAccount);
        }

        transactionDto = modelMapper.map(transaction,TransactionDto.class);

        //TODO:Logic FutureTransaction
        return transactionDto;

    }

    @Transactional
    public UserDto createUser(UserDto userDto) throws IOException {

        if (userRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getDateOfBirth())) {
            throw  new IOException("Error: Combination of first name, last name and birth is already taken!");
        }
        else {
            User user = this.modelMapper.map(userDto,User.class);
            user.setDateOfCreation(OffsetDateTime.now().truncatedTo(truncateDateOfCreation));
            user = userRepository.save(user);
            return this.modelMapper.map(user,UserDto.class);
        }
    }

    @Transactional
    public UserDto delete(Integer id) throws IOException {

        Optional<User> optionalUser= userRepository.findById(id.longValue());

        if(optionalUser.isEmpty()){
            throw new NullPointerException("User with id " + id + " not found");
        }
        else {
            User user = optionalUser.get();
            if (user.getAccounts().isEmpty()){
                userRepository.delete(user);
                return modelMapper.map(user,UserDto.class);
            }
            else{
                throw new IOException(
                        "User with id " + id + " can not be deleted" +
                        "Reason: User has accounts"
                );
            }
        }
    }

    @Transactional
    public List<UserDto> getAll() {

        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user : users){
            List<AccountDto> accountDtos = new ArrayList<>();

            for(Account account : user.getAccounts()){
                AccountDto accountDto = modelMapper.map(account, AccountDto.class);
                accountDtos.add(accountDto);
            }
            UserDto userDto = modelMapper.map(user,UserDto.class);
            userDto.accounts(accountDtos);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Transactional
    public AccountDto createAccount(Integer id, AccountDto accountDto) {

        Optional<User> optionalUser= userRepository.findById(id.longValue());

        if(optionalUser.isEmpty()){
            throw new NullPointerException("User with id " + id + " not found");
        }
        else{
            User user = optionalUser.get();
            Account account = modelMapper.map(accountDto, Account.class);

            Integer nextAccountNumber = accountService.getHighestAccountNumber() + 1;

            account.setAmount(0.00f);
            account.setIban(accountService.buildIban(nextAccountNumber));
            account.setAccountNumber(nextAccountNumber);
            account.setDateOfCreation(OffsetDateTime.now().truncatedTo(truncateDateOfCreation));

            account.setUser(user);
            account = accountRepository.save(account);
            return modelMapper.map(account, AccountDto.class);
        }
    }
}
