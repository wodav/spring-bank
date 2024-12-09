package spring.bank.controller;

import org.openapitools.api.AuthApi;
import org.openapitools.dto.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import spring.bank.services.AuthService;

import java.io.IOException;

@RestController
public class AuthController implements AuthApi {

    @Autowired
    AuthService authService;

    @Override
    public ResponseEntity<LoginDto> login(LoginDto loginDto) {

        loginDto = authService.login(loginDto);
        return new ResponseEntity<>(loginDto, HttpStatus.CREATED);

    }
}
