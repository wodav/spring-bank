package spring.bank.controller;

import org.openapitools.api.AccountsApi;
import org.openapitools.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.AccountService;

import java.io.IOException;
import java.util.List;

@RestController
public class AccountController implements AccountsApi {

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<List<AccountDto>> accountsGet() {
        return AccountsApi.super.accountsGet();
    }
}
