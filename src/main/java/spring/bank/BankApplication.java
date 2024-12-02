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
		ResponseEntity response = restTemplate.exchange("http://localhost:8080/users", HttpMethod.POST, httpEntity, UserDto.class);

		JSONObject accountDto = new JSONObject();

		accountDto.put("currency", "EUR");
		accountDto.put(	"isMain", true);

		httpEntity = new HttpEntity<>(accountDto);
		response = restTemplate.exchange("http://localhost:8080/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);
		response = restTemplate.exchange("http://localhost:8080/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);
		response = restTemplate.exchange("http://localhost:8080/users/1/accounts", HttpMethod.POST, httpEntity, AccountDto.class);



	}

}
