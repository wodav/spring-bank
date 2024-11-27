package spring.bank.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
/*

public class UserMapper {



    public static UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBooks().stream().map(UserMapper::toDto).collect(Collectors.toList())
        );
    }

    public static User toEntity(UserDto dto) {
        return new User(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getAddress(),
                dto.getBooks().stream().map(UserMapper::toEntity).collect(Collectors.toList())
        );
    }

    public static BookDto toDto(Book entity) {
        return new BookDto(entity.getName(), entity.getAuthor());
    }

    public static Book toEntity(BookDto dto) {
        return new Book(dto.getName(), dto.getAuthor());
    }
}*/
