package spring.bank.mapper;

import org.openapitools.dto.RoleDto;
import org.openapitools.dto.UserDto;
import org.springframework.security.core.GrantedAuthority;

public class RoleAuthoritiesMapper {

    public static RoleDto getRole(String authorities) {

        RoleDto roleDto = null;

        switch (authorities) {
            case "ROLE_USER" -> roleDto = RoleDto.USER;
            case "ROLE_BANK_EMPLOYEE" -> roleDto = RoleDto.BANK_EMPLOYEE;
            case "ROLE_ADMIN" -> roleDto = RoleDto.ADMIN;
        }
        return roleDto;
    }

    public static String getAuthorities(UserDto userDto) {

        String authorities = null;

        switch(userDto.getRole()){
            case RoleDto.USER -> authorities = "ROLE_USER";
            case RoleDto.BANK_EMPLOYEE -> authorities = "ROLE_BANK_EMPLOYEE";
        }
        return authorities;
    }
}
