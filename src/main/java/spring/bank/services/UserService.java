package spring.bank.services;

import org.modelmapper.ModelMapper;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.bank.entities.User;
import spring.bank.repositories.UserRepository;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

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

        User user = this.modelMapper.map(userDto,User.class);
        user.setDateOfCreate(OffsetDateTime.now());
        user = userRepository.save(user);
        return this.modelMapper.map(user,UserDto.class);
    }
}
