package jj.mstest.e2e;

import jj.mstest.controller.dto.UsernamePassword;
import jj.mstest.restClient.dto.Project;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class E2ETest {
    @Value("${cloud.password}")
    private String cloudPassword;

    @Test
    public void testLoginAndGetProjects() {
        UsernamePassword usernamePassword = new UsernamePassword();
        usernamePassword.setUsername("jan.janocko");
        usernamePassword.setPassword(cloudPassword);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Void> responseEntity = restTemplate.postForEntity("http://localhost:8080/user", usernamePassword, Void.class);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        ResponseEntity<Project[]> projectResponse = restTemplate.getForEntity("http://localhost:8080/project", Project[].class);
        assertTrue(projectResponse.getStatusCode().is2xxSuccessful());
        assertEquals(2, projectResponse.getBody().length);
        assertEquals("NEW", projectResponse.getBody()[0].getStatus());
    }

}
