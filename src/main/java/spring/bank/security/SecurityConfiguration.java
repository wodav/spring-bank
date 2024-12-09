package spring.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Autowired
    CustomCorsConfiguration customCorsConfiguration;

    private static final String[] WHITE_LIST_URL = {"/h2-console/**", "/swagger-ui","/swagger-ui/index.html#/**", "/users/**", "/users"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(customCorsConfiguration))
                .authorizeHttpRequests(
                        (request) -> request
                                .requestMatchers(WHITE_LIST_URL).permitAll()
                );
        return httpSecurity.build();
    }
}
