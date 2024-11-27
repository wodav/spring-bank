package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.bank.entities.User;
import spring.bank.repositories.UserRepository;

import java.io.IOException;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) throws IOException {

        if (userRepository.existsByFirstNameAndLastName(userDto.getFirstName(),userDto.getLastName())) { //TODO: add birthdate
            throw  new IOException("Error: Combination of first name, last name and birth is already taken!");
        }

        User user = this.modelMapper.map(userDto,User.class);
        user = userRepository.save(user);
        return this.modelMapper.map(user,UserDto.class);
    }
}
