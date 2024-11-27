package spring.bank.controller;


import org.modelmapper.ModelMapper;
import org.openapitools.api.UsersApi;


import org.openapitools.dto.RoleDto;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.UserService;

import java.io.IOException;
import java.util.List;

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

    @Override
    public ResponseEntity<UserDto> deleteUser(Integer id) {

        try{
            UserDto userDto = userService.delete(id);
            return new ResponseEntity<>(userDto,HttpStatus.OK);
        } catch (NullPointerException e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
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
        return UsersApi.super.usersGet();
    }

    @Override
    public ResponseEntity<UserDto> usersRoleGet(RoleDto role) {
        return UsersApi.super.usersRoleGet(role);
    }
}
