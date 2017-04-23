package jj.mstest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MstestConfiguration {
    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
