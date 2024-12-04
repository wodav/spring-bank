package spring.bank.controller;

import org.openapitools.api.TransactionsApi;
import org.openapitools.dto.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import spring.bank.services.TransactionService;

import java.io.IOException;
import java.net.ConnectException;
import java.util.List;
import java.util.Objects;

@Controller
public class TransactionController implements TransactionsApi {

    @Autowired
    TransactionService transactionService;

    @Override
    public ResponseEntity<List<TransactionDto>> transactionsGet() {
        return TransactionsApi.super.transactionsGet();
    }

    @Override
    public ResponseEntity<TransactionDto> transactionsPost(TransactionDto transactionDto) {
        // TODO: mayebe to account/{id}/createTransaction
        try {
            transactionDto = transactionService.createTransaction(transactionDto);
            return new ResponseEntity<>(transactionDto, HttpStatus.OK);
        }catch (NullPointerException e){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }catch (ConnectException e){
            return new ResponseEntity<>(null,HttpStatus.SERVICE_UNAVAILABLE);
        }
        catch (IOException e){
            if(Objects.equals(e.getMessage(), "Iban and user name are not matching")){
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if(Objects.equals(e.getMessage(), "Iban is not valid from validation algorithm")){
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            if(Objects.equals(e.getMessage(), "Iban and user name from destination are not matching")){
                return new ResponseEntity<>(null,HttpStatus.UNPROCESSABLE_ENTITY);
            }
            else {
                return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
            }
        }
    }
}
