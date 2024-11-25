package spring.bank.controller;


import org.openapitools.api.UsersApi;

import org.openapitools.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<User> createUser(User user){
        return new ResponseEntity<>(user,HttpStatus.OK);
    }


}
