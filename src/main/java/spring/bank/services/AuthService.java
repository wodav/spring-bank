package spring.bank.services;

import org.openapitools.dto.LoginDto;
import org.openapitools.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import spring.bank.security.JwtUtils;
import spring.bank.security.UserDetailsImpl;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;

    public LoginDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUserName(),
                        loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        RoleDto roleDto = null;

        for (GrantedAuthority grantedAuthority: userDetails.getAuthorities()){
            switch (grantedAuthority.getAuthority()) {
                case "ROLE_USER" -> roleDto = RoleDto.USER;
                case "ROLE_BANK_EMPLOYEE" -> roleDto = RoleDto.BANK_EMPLOYEE;
                case "ROLE_ADMIN" -> roleDto = RoleDto.ADMIN;
            }
        }

        loginDto.setRole(roleDto);
        loginDto.setToken(jwtUtils.generateJwtToken(authentication));
        loginDto.setUserName(userDetails.getUsername());

        return loginDto;

    }
}
