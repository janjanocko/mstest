package jj.mstest.restClient;

import jj.mstest.restClient.dto.LoginResponse;
import jj.mstest.restClient.dto.Project;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


/**
 * Test that calls real interface.
 * Ignored because I don't want real API be called too often
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestClientIntegrationTest {
    @Autowired
    private RestClient subject;

    @Value("${cloud.password}")
    private String cloudPassword;

    @Test
    public void shouldLoginToRealServer() {
        ResponseEntity<LoginResponse> responseEntity = subject.login("jan.janocko", cloudPassword);
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        assertNotNull(responseEntity.getBody().getToken());
    }

    @Test
    public void shouldGetRealProjects() {
        Project[] projects = subject.getProjects().getBody();
        assertEquals(2, projects.length);
    }
}
