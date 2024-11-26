package spring.bank.controller;


import org.openapitools.api.UsersApi;

import org.openapitools.dto.Role;
import org.openapitools.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UsersApi {

    @Override
    public ResponseEntity<User> createUser(User user){
        return new ResponseEntity<>(user,HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<User> deleteUser(Integer id) {
        return UsersApi.super.deleteUser(id);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer id, User user) {
        return UsersApi.super.updateUser(id, user);
    }

    @Override
    public ResponseEntity<User> usersFindByNameGet(String firstName, String lastName) {
        return UsersApi.super.usersFindByNameGet(firstName, lastName);
    }

    @Override
    public ResponseEntity<List<User>> usersGet() {
        return UsersApi.super.usersGet();
    }

    @Override
    public ResponseEntity<User> usersRoleGet(Role role) {
        return UsersApi.super.usersRoleGet(role);
    }
}
