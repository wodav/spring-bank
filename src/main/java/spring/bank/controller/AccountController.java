package spring.bank.controller;

import org.openapitools.api.AccountsApi;
import org.openapitools.dto.AccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.AccountService;

import java.util.List;

@RestController
public class AccountController implements AccountsApi {

    @Autowired
    AccountService accountService;

    @Override
    public ResponseEntity<List<AccountDto>> accountsGet() {
        return new ResponseEntity<>(accountService.getAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDto> deleteAccount(Integer id) {
        try{
            AccountDto accountDto = accountService.delete(id);
            return new ResponseEntity<>(accountDto,HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
    }
}
