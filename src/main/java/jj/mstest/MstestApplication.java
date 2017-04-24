package jj.mstest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MstestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MstestApplication.class, args);
    }

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


}
