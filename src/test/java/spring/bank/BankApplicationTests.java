package spring.bank;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.openapitools.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankApplicationTests {
	@Autowired
	protected TestRestTemplate testRestTemplate;
	protected ResponseEntity response;
	@Test
	void contextLoads() {
		HttpHeaders headers = new HttpHeaders();
		JSONObject newUser = new JSONObject();

		//headers.add("Authorization","Bearer "+ jwtToken);
		headers.add("Content-Type","application/json");
		newUser.put("username","Ben");

		HttpEntity httpEntity = new HttpEntity<>(newUser,headers);

		response=testRestTemplate.exchange("/users", HttpMethod.POST,httpEntity, User.class);
		System.out.print("Hhaha");
	}

}
