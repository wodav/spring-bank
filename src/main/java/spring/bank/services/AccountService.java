package spring.bank.services;

import org.openapitools.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
public class AccountService {

    @Transactional
    public AccountDto create(AccountDto accountDto) throws IOException {
        return null;
    }
}
