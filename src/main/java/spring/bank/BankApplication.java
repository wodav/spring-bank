package spring.bank;

import net.minidev.json.JSONObject;
import org.openapitools.dto.AccountDto;
import org.openapitools.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import spring.bank.entities.Account;

@SpringBootApplication
public class BankApplication implements CommandLineRunner {

	@Autowired
	RestTemplate restTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		String uri = "http://localhost:8080";

		JSONObject userDto = new JSONObject();

		userDto.put("firstName", "John");
		userDto.put("lastName", "James");
		userDto.put("dateOfBirth", "2024-12-02");
		userDto.put("email", "example@mail.com");
		userDto.put("password", "12345");
		userDto.put("phone", "12345");
		userDto.put("userStatus", 1);
		userDto.put("role", "user");

		HttpEntity<JSONObject> httpEntity = new HttpEntity<>(userDto);
		ResponseEntity response = restTemplate.exchange(uri+"/users", HttpMethod.POST, httpEntity, UserDto.class);

		userDto = new JSONObject();

		userDto.put("firstName", "Adam");
		userDto.put("lastName", "Adams");
		userDto.put("dateOfBirth", "2024-12-02");
		userDto.put("email", "example@mail.com");
		userDto.put("password", "12345");
		userDto.put("phone", "12345");
		userDto.put("userStatus", 1);
		userDto.put("role", "user");

		httpEntity = new HttpEntity<>(userDto);
		response = restTemplate.exchange(uri+"/users", HttpMethod.POST, httpEntity, UserDto.class);

		JSONObject accountDto = new JSONObject();

		accountDto.put("currency", "EUR");
		accountDto.put(	"isMain", true);

		httpEntity = new HttpEntity<>(accountDto);
		response = restTemplate.exchange(uri+"/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);
		response = restTemplate.exchange(uri+"/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);
		response = restTemplate.exchange(uri+"/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);

		response = restTemplate.exchange(uri+"/users/2/accounts", HttpMethod.POST, httpEntity, AccountDto.class);

		JSONObject transactionDto = new JSONObject();

		transactionDto.put("destinationName", "John James");
		transactionDto.put("destinationIban", "DE9111111111000001");
		transactionDto.put("amount", 570);
		transactionDto.put("currency", "EUR");
		transactionDto.put("purposeOfUse", "Miete Januar");

		httpEntity = new HttpEntity<>(transactionDto);
		response = restTemplate.exchange(uri+"/users/2/accounts/4/transactions", HttpMethod.POST, httpEntity, AccountDto.class);

		transactionDto.put("destinationName", "John James");
		transactionDto.put("destinationIban", "DE9111111111000003");
		transactionDto.put("amount", 320);
		transactionDto.put("currency", "EUR");
		transactionDto.put("purposeOfUse", "GEZ");

		httpEntity = new HttpEntity<>(transactionDto);
		response = restTemplate.exchange(uri+"/users/2/accounts/4/transactions", HttpMethod.POST, httpEntity, AccountDto.class);
	}

}
