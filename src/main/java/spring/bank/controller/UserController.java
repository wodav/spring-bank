package spring.bank.controller;


import org.modelmapper.ModelMapper;
import org.openapitools.api.UsersApi;


import org.openapitools.dto.AccountDto;
import org.openapitools.dto.RoleDto;
import org.openapitools.dto.TransactionDto;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.UserService;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@RestController
public class UserController implements UsersApi {

    @Autowired
    UserService userService;

    @Override
    public ResponseEntity<List<AccountDto>> usersIdAccountsGet(Integer id) {
        return UsersApi.super.usersIdAccountsGet(id);
    }

    @Override
    public ResponseEntity<AccountDto> createAccount(Integer id, AccountDto accountDto) {

        try {
            accountDto = userService.createAccount(id, accountDto);
            return new ResponseEntity<>(accountDto, HttpStatus.CREATED);
        } catch (NullPointerException e) {
            return new ResponseEntity<>(accountDto,HttpStatus.NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity<TransactionDto> usersUserIdAccountsAccountIdTransactionsPost(Integer userId, Integer accountId, TransactionDto transactionDto) {
        // TODO: mayebe to account/{id}/createTransaction
        try {
            transactionDto = userService.createTransaction(userId, accountId, transactionDto);
            return new ResponseEntity<>(transactionDto,HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }catch (IOException e){
            if(Objects.equals(e.getMessage(), "Iban and user name are not matching")){
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if(Objects.equals(e.getMessage(), "Iban is not valid from validation algorithm")){
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else {
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Transactional
    public ResponseEntity<UserDto> createUser(UserDto userDto){

        try {
            userDto = userService.createUser(userDto);
            return new ResponseEntity<>(userDto,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(userDto,HttpStatus.CONFLICT);
        }

    }

    @Override
    public ResponseEntity<UserDto> deleteUser(Integer id) {

        try{
            UserDto userDto = userService.delete(id);
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        } catch (IOException e){
            return new ResponseEntity<>(null,HttpStatus.IM_USED);
        }
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Integer id, UserDto userDto) {
        return UsersApi.super.updateUser(id, userDto);
    }

    @Override
    public ResponseEntity<UserDto> usersFindByNameGet(String firstName, String lastName) {
        return UsersApi.super.usersFindByNameGet(firstName, lastName);
    }

    @Override
    public ResponseEntity<List<UserDto>> usersGet() {
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> usersRoleGet(RoleDto role) {
        return UsersApi.super.usersRoleGet(role);
    }
}
