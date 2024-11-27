package spring.bank.controller;


import org.modelmapper.ModelMapper;
import org.openapitools.api.UsersApi;


import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.UserService;

import java.io.IOException;

@RestController
public class UserController implements UsersApi {

    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public ResponseEntity<UserDto> createUser(UserDto userDto){

        try {
            userDto = userService.createUser(userDto);
            return new ResponseEntity<>(userDto,HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(userDto,HttpStatus.CONFLICT);
        }

    }



}
