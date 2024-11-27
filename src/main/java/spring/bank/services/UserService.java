package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.AccountDto;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.bank.entities.Account;
import spring.bank.entities.User;
import spring.bank.repositories.AccountRepository;
import spring.bank.repositories.UserRepository;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) throws IOException {

        if (userRepository.existsByFirstNameAndLastNameAndDateOfBirth(
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getDateOfBirth())) {
            throw  new IOException("Error: Combination of first name, last name and birth is already taken!");
        }
        else {
            User user = this.modelMapper.map(userDto,User.class);
            user.setDateOfCreate(OffsetDateTime.now());
            user = userRepository.save(user);
            return this.modelMapper.map(user,UserDto.class);
        }
    }

    @Transactional
    public UserDto delete(Integer id) {

        Optional<User> optionalUser= userRepository.findById(id.longValue());

        if(optionalUser.isEmpty()){
            throw new NullPointerException("User with id " + id + " not found");
        }
        else {
            //TODO: user.getAccounts should be empty throw exception and return Conflict
            User user = optionalUser.get();
            userRepository.delete(user);
            return modelMapper.map(user,UserDto.class);
        }
    }

    @Transactional
    public List<UserDto> getAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtoList = new ArrayList<>();
        for(User user : users){
            UserDto userDto = modelMapper.map(user,UserDto.class);
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    @Transactional
    public AccountDto createAccount(Integer id, AccountDto accountDto) {

        Optional<User> optionalUser= userRepository.findById(id.longValue());

        if(optionalUser.isEmpty()){
            throw new NullPointerException("User with id " + id + " not found");
        }
        else{
            User user = optionalUser.get();
            Account account = modelMapper.map(accountDto, Account.class);
            account.setUser(user);
            account = accountRepository.save(account);
            return modelMapper.map(account, AccountDto.class);
        }
    }
}
