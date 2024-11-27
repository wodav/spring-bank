package spring.bank.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import org.openapitools.dto.AccountDto;
import org.openapitools.dto.RoleDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="_USER") //table name "USER" is reserved
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
/*
    @Valid
    private List<@Valid AccountDto> account = new ArrayList<>();
*/
    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String phone;

    private Integer userStatus;

    private RoleDto role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
/*
    public @Valid List<@Valid AccountDto> getAccount() {
        return account;
    }

    public void setAccount(@Valid List<@Valid AccountDto> account) {
        this.account = account;
    }
*/
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public RoleDto getRole() {
        return role;
    }

    public void setRole(RoleDto role) {
        this.role = role;
    }
}
