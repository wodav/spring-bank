package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import spring.bank.calculation.Iban;
import spring.bank.entities.Account;
import spring.bank.entities.Transaction;
import spring.bank.entities.User;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.TransactionRepository;
import spring.bank.security.JwtUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    JwtUtils jwtUtils;

    @Value("${external.server.port}")
    String externalServerPort; //TODO: Later get uri from iban registry

    @Value("${external.server.uri}")
    String externalServerUri;

    private final ChronoUnit truncateDateOfCreation = ChronoUnit.SECONDS;

    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) throws IOException {

        //TODO get name from jwt and proof with iban

        Account sourceAccount = null;
        Account destinationAccount = null;

        String sourceIban = transactionDto.getSourceIban();
        String destinationIban = transactionDto.getDestinationIban();

        boolean sourceIbanIsNotValid = !Iban.validate(sourceIban);
        boolean destinationIbanIsNotValid = !Iban.validate(destinationIban);

        if(sourceIbanIsNotValid){
            throw new IOException("Iban: "
                    + sourceIban
                    + " from source is not valid from validation algorithm");
        }

        if(destinationIbanIsNotValid){
            throw new IOException("Iban: "
                    + destinationIban
                    + " from destination is not valid from validation algorithm");
        }

        boolean sourceIbanBelongsToThisBank = Iban.belongsToThisBank(sourceIban);
        boolean destinationIbanBelongsToThisBank = Iban.belongsToThisBank(destinationIban);

        //TODO: Or call here account/validate
        if(sourceIbanBelongsToThisBank){
            Optional<Account> optionalSourceAccount = accountRepository.findByIban(sourceIban);
            if(optionalSourceAccount.isEmpty()) {
                throw new NullPointerException("Source Account with iban " + sourceIban + " not found");
            }else{
                sourceAccount = optionalSourceAccount.get();
            }
        }else {
            /*
            String uri = externalServerUri+":"+externalServerPort;
            HttpEntity httpEntity = new HttpEntity<>(transactionDto);
            try{
                ResponseEntity response = restTemplate.exchange(uri+"/transactions/approved", HttpMethod.POST, httpEntity, AccountDto.class);
            }catch (HttpStatusCodeException e){
                throw new ConnectException("Server" + uri + "not reachable (called endpoint: /transactions)");
            }*/
        }

        if(destinationIbanBelongsToThisBank) {

            Optional<Account> optionalDestinationAccount = accountRepository.findByIban(destinationIban);
            if (optionalDestinationAccount.isEmpty()) {
                throw new NullPointerException("Destination Account with iban " + destinationIban + " not found");
            }else{
                destinationAccount = optionalDestinationAccount.get();
            }

        }else {
            String uri = externalServerUri+":"+externalServerPort;
            HttpEntity httpEntity = new HttpEntity<>(transactionDto);
            try{
                //TODO: call here account/validate
                ResponseEntity<TransactionDto> response = restTemplate.exchange(uri+"/transactions", HttpMethod.POST, httpEntity, TransactionDto.class);

            }catch (HttpStatusCodeException e){
                throw new ConnectException("Server" + uri + "not reachable (called endpoint: /transactions)");
            }
        }

        //if(!accountService.validate(sourceAccount, transactionDto.getSourceName())) {
            //throw new IOException("Iban and user name from source are not matching");
       // }

//        if(!accountService.validate(destinationAccount, transactionDto.getDestinationName())) {
  //          throw new IOException("Iban and user name from destination are not matching"); //TODO: Proof in restcall before, cause transaction is already at destination, call registry or endpoint account/validate
    //    }

        Transaction transaction = modelMapper.map(transactionDto, Transaction.class);

        transaction.getAccounts().add(sourceAccount);
        transaction.setDateOfCreation(OffsetDateTime.now().truncatedTo(truncateDateOfCreation));
        if(sourceIbanBelongsToThisBank){
            User sourceUser = sourceAccount.getUser();
            String sourceName = sourceUser.getFirstName() + " " + sourceUser.getLastName();
            transaction.setSourceName(sourceName);
        }
        transaction.setSourceIban(sourceIban);
        transaction = transactionRepository.save(transaction);

        if(sourceIbanBelongsToThisBank){
            sourceAccount.getTransactions().add(transaction);
            accountRepository.save(sourceAccount);
        }

        if(destinationIbanBelongsToThisBank) {
            destinationAccount.getTransactions().add(transaction);
            accountRepository.save(destinationAccount);
        }

        transactionDto = modelMapper.map(transaction,TransactionDto.class);

        //TODO:Logic FutureTransaction
        return transactionDto;

    }
}
