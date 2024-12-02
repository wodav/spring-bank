package spring.bank.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.openapitools.dto.TransactionDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import spring.bank.entities.Transaction;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        skipAccountsInTransaction(modelMapper);
        return modelMapper;
    }

    private void skipAccountsInTransaction(ModelMapper modelMapper){
        TypeMap<Transaction, TransactionDto> propertyMapper = modelMapper.createTypeMap(Transaction.class, TransactionDto.class);
        propertyMapper.addMappings(mapper -> mapper.skip(TransactionDto::setAccounts));
    }

}